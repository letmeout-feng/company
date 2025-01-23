package com.internal.quartz.task;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.util.StringUtil;
import com.internal.common.core.domain.entity.SysUser;
import com.internal.common.email.SystemConfig;
import com.internal.common.enums.QuoteStatusEnum;
import com.internal.common.request.PageParams;
import com.internal.common.utils.MailUtil;
import com.internal.common.utils.poi.EasyExcelUtil;
import com.internal.quote.domain.QuoteEmailSetting;
import com.internal.quote.dto.QuoteOpportunitiesQuery;
import com.internal.quote.mapper.QuoteEmailSettingMapper;
import com.internal.quote.mapper.QuoteOpportunitiesMapper;
import com.internal.quote.service.IQuoteOpportunitiesService;
import com.internal.quote.vo.QuoteOpportunitiesExportVO;
import com.internal.quote.vo.QuoteOpportunitiesVO;
import com.internal.system.domain.SystemEmail;
import com.internal.system.mapper.SysUserMapper;
import com.internal.system.mapper.SystemEmailMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 定时任务调度测试
 *
 * @author every
 */
@Component("GenLoseReportOnWeekTask")
@Slf4j
@AllArgsConstructor
public class GenLoseReportOnWeekTask {

    private final SystemConfig systemConfig;
    private final SystemEmailMapper systemEmailMapper;
    private final IQuoteOpportunitiesService quoteOpportunitiesService;
    private final QuoteOpportunitiesMapper quoteOpportunitiesMapper;
    private final QuoteEmailSettingMapper quoteEmailSettingMapper;
    private final SysUserMapper sysUserMapper;

    private static final String TITLE = "本周丢单商机汇总";
    private static final String DEFAULT_MSG = "本周丢单商机汇总文件内容";
    private static final String DEFAULT_DETAIL = "从上周日 12:00:01 - 本周日 11:59:59 产生的丢单商机";
    private static final String EXCEL_FILE_NAME = "商机汇总.xlsx";
    private static final String EMAIL_TYPE = "QUOTE";
    private static final String WEEKLY_SCHEDULED_NOTIFICATION = "WEEKLY_SCHEDULED_NOTIFICATION";

    public void sync() throws MessagingException, GeneralSecurityException {
        log.info("--------------开始生成本周丢单商机汇总---------------");

        List<SysUser> receiveUser = new ArrayList<>();  // 收件人
        List<SysUser> ccUserList = new ArrayList<>();   // 抄送人

        List<QuoteOpportunitiesVO> businessOpportunityList = fetchBusinessOpportunities();
        if (businessOpportunityList == null || businessOpportunityList.isEmpty()) {
            log.error("商机列表为空，无法生成Excel文件。");
            return;
        }

        // 导出Excel文件
        ByteArrayOutputStream bos = exportToExcel(businessOpportunityList);

        // 获取收件人和抄送人
        List<SysUser> sysUserList = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery());
        List<SysUser> finalReceiveUser = getReceivers(sysUserList);
        List<SysUser> finalCcUserList = getCcUsers(sysUserList, businessOpportunityList);

        // 获取邮件信息并发送
        SystemEmail systemEmail = getSystemEmail();
        if (systemEmail != null) {
            log.info("----------开始发送生成本周丢单商机汇总邮件----------");
            sendEmail(systemEmail, finalReceiveUser, finalCcUserList, bos, DEFAULT_MSG, DEFAULT_DETAIL);
        } else {
            log.error("邮件已无发送次数。");
        }

        log.info("--------------结束执行本周丢单商机汇总任务---------------");
    }

    private List<QuoteOpportunitiesVO> fetchBusinessOpportunities() {
        try {
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            // 计算本周日的 11:59:59
            LocalDateTime thisSundayEnd = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                    .withHour(11)
                    .withMinute(59)
                    .withSecond(59);
            // 计算上周日的 12:00:01
            LocalDateTime lastSundayStart = thisSundayEnd.minusWeeks(1)
                    .withHour(12)
                    .withMinute(0)
                    .withSecond(1);
            PageParams<QuoteOpportunitiesQuery> params = new PageParams<>(1, Integer.MAX_VALUE);
            params.setModel(QuoteOpportunitiesQuery.builder()
                    .weekLose(Boolean.TRUE)
                    .loseDateStart(lastSundayStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))  // 设置开始时间
                    .loseDateEnd(thisSundayEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))      // 设置结束时间
                    .statusList(Arrays.asList("14","15","17"))
                    .build());
            return BeanUtil.copyToList(quoteOpportunitiesMapper.selectExportLostInfoList(params.getModel()), QuoteOpportunitiesVO.class);
        } catch (Exception e) {
            log.error("数据库获取数据失败，请查看邮件详情: {}", JSON.toJSONString(e));
            return null;
        }
    }

    private ByteArrayOutputStream exportToExcel(List<QuoteOpportunitiesVO> businessOpportunityList) {
        List<QuoteOpportunitiesExportVO> quoteOpportunitiesExportVOList = BeanUtil.copyToList(businessOpportunityList, QuoteOpportunitiesExportVO.class);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        EasyExcelUtil<QuoteOpportunitiesExportVO> easyExcelUtil = new EasyExcelUtil<>(QuoteOpportunitiesExportVO.class);
        // 数据处理
        quoteOpportunitiesExportVOList.forEach(item ->{
            item.setStatus(QuoteStatusEnum.getInfoByCode(item.getStatus()));
        });
        easyExcelUtil.exportEasyExcel(bos, quoteOpportunitiesExportVOList, "Sheet1");
        return bos;
    }

    private List<SysUser> getReceivers(List<SysUser> sysUserList) {
        List<SysUser> receiveUser = new ArrayList<>();
        QuoteEmailSetting quoteEmailSetting = quoteEmailSettingMapper.selectOne(Wrappers.<QuoteEmailSetting>lambdaQuery()
                .eq(QuoteEmailSetting::getType, WEEKLY_SCHEDULED_NOTIFICATION));

        if (quoteEmailSetting != null && StringUtil.isNotEmpty(quoteEmailSetting.getSender())) {
            Arrays.stream(quoteEmailSetting.getSender().split(","))
                    .map(userId -> sysUserList.stream()
                            .filter(sysUser -> String.valueOf(sysUser.getUserId()).equals(userId))
                            .findFirst().orElse(null))
                    .filter(Objects::nonNull)
                    .forEach(receiveUser::add);
        }

        return receiveUser;
    }

    private List<SysUser> getCcUsers(List<SysUser> sysUserList, List<QuoteOpportunitiesVO> businessOpportunityList) {
        List<SysUser> ccUserList = new ArrayList<>();
        businessOpportunityList.forEach(quoteOpportunities -> {
            if (StringUtil.isNotEmpty(quoteOpportunities.getSupportPerson())) {
                Arrays.stream(quoteOpportunities.getSupportPerson().split(","))
                        .map(userId -> sysUserList.stream()
                                .filter(sysUser -> sysUser.getCrmUserId().equals(userId))
                                .findFirst().orElse(null))
                        .filter(Objects::nonNull)
                        .forEach(ccUserList::add);
            }
        });
        return ccUserList;
    }

    private SystemEmail getSystemEmail() {
        return systemEmailMapper.selectOne(Wrappers.<SystemEmail>lambdaQuery()
                .eq(SystemEmail::getType, EMAIL_TYPE)
                .ge(SystemEmail::getSendNum, 0)
                .lt(SystemEmail::getSendNum, 90).last("limit 1"));
    }

    private void sendEmail(SystemEmail systemEmail, List<SysUser> receiveUser, List<SysUser> ccUserList,
                           ByteArrayOutputStream bos, String msg, String strDetail) {
        try {
            MailUtil.sendEmailToSyncProjectManager("true", systemEmail.getHost(),
                    systemEmail.getUsername(), systemEmail.getPassword(),
                    TITLE, msg + " <br/> " + strDetail,
                    true, ccUserList, true, receiveUser,
                    Map.of(EXCEL_FILE_NAME, bos.toByteArray()));

            log.info("----------开发发送同步邮件成功----------");

            systemEmail.setSendNum(systemEmail.getSendNum() + 1);
            systemEmailMapper.updateById(systemEmail);
        } catch (MessagingException | GeneralSecurityException e) {
            log.error("邮件发送失败: {}", e.getMessage());
        }
    }

}

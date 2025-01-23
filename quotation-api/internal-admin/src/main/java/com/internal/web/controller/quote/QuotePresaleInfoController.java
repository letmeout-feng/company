package com.internal.web.controller.quote;

import cn.hutool.core.util.ObjectUtil;
import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.email.SystemConfig;
import com.internal.common.enums.BusinessType;
import com.internal.common.enums.ContractType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.quote.domain.QuotePresaleInfo;
import com.internal.quote.dto.BaseDTO;
import com.internal.quote.dto.QuotePresaleInfoQuery;
import com.internal.quote.dto.QuotePresaleInfoSaveDTO;
import com.internal.quote.service.IQuotePresaleInfoService;
import com.internal.quote.vo.QuotePreSaleDetailInfoVO;
import com.internal.quote.vo.QuotePresaleInfoVO;
import com.internal.quote.vo.SignDetailInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售报价信息Controller
 * 
 * @author internal
 * @date 2024-10-30
 */
@RestController
@AllArgsConstructor
@Tag(name = "销售报价信息", description  = "销售报价信息")
@RequestMapping("/quote/info")
public class QuotePresaleInfoController extends BaseController
{

    private final IQuotePresaleInfoService quotePresaleInfoService;
    private final SystemConfig systemConfig;
    /**
     * 查询报价系统-商机售前报价信息列表
     */
    @Operation(summary = "查询报价系统-商机售前报价信息列表", description  = "查询报价系统-商机售前报价信息列表")
    @PreAuthorize("@ss.hasPermi('quote:info:list')")
    @PostMapping("/page")
    public TableDataInfo<List<QuotePresaleInfo>> list(@RequestBody PageParams<QuotePresaleInfo> params)
    {
        startPage(params);
        List<QuotePresaleInfo> list = quotePresaleInfoService.selectQuotePresaleInfoList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出报价系统-商机售前报价信息列表
     */
    @Operation(summary = "导出报价系统-商机售前报价信息列表", description  = "导出报价系统-商机售前报价信息列表")
    @PreAuthorize("@ss.hasPermi('quote:info:export')")
    @Log(title = "报价系统-商机售前报价信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuotePresaleInfo quotePresaleInfo)
    {
        List<QuotePresaleInfo> list = quotePresaleInfoService.selectQuotePresaleInfoList(quotePresaleInfo);
        ExcelUtil<QuotePresaleInfo> util = new ExcelUtil<QuotePresaleInfo>(QuotePresaleInfo.class);
        util.exportExcel(response, list, "报价系统-商机售前报价信息数据");
    }

    /**
     * 获取报价系统-商机售前报价信息详细信息
     */
    @Operation(summary = "获取报价系统-商机售前报价信息详细信息", description  = "获取报价系统-商机售前报价信息详细信息")
    @PreAuthorize("@ss.hasPermi('quote:info:query')")
    @GetMapping(value = "/{id}")
    public R<QuotePresaleInfo> getInfo(@PathVariable("id") Long id)
    {
        return  R.ok(quotePresaleInfoService.selectQuotePresaleInfoById(id));
    }

    /**
     * 新增报价系统-商机售前报价信息
     */
    @Operation(summary = "新增报价系统-商机售前报价信息", description  = "新增报价系统-商机售前报价信息")
    @PreAuthorize("@ss.hasPermi('quote:info:add')")
    @Log(title = "报价系统-商机售前报价信息", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody QuotePresaleInfo quotePresaleInfo)
    {
        return R.ok(quotePresaleInfoService.insertQuotePresaleInfo(quotePresaleInfo));
    }

    /**
     * 修改报价系统-商机售前报价信息
     */
    @Operation(summary = "修改报价系统-商机售前报价信息", description  = "修改报价系统-商机售前报价信息")
    @PreAuthorize("@ss.hasPermi('quote:info:edit')")
    @Log(title = "报价系统-商机售前报价信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody QuotePresaleInfo quotePresaleInfo)
    {
        return R.ok(quotePresaleInfoService.updateQuotePresaleInfo(quotePresaleInfo));
    }

    /**
     * 销售报价-待销售报价-粗略报价详情
     */
    @Operation(summary = "销售报价-待销售报价-粗略报价详情", description  = "销售报价-待销售报价-粗略报价详情")
    @PostMapping("/getSalesRoughInfo")
    public R<QuotePreSaleDetailInfoVO> getSalesRoughInfo(@RequestBody QuotePresaleInfoQuery quotePresaleInfoQuery)
    {
        QuotePreSaleDetailInfoVO result = quotePresaleInfoService.getSalesRoughInfo(quotePresaleInfoQuery);
        return R.ok(result);
    }

    /**
     * 销售报价-待销售报价-详细报价详情
     */
    @Operation(summary = "销售报价-待销售报价-详细报价详情", description  = "销售报价-待销售报价-详细报价详情")
    @PostMapping("/getSalesDetailInfo")
    public R<QuotePreSaleDetailInfoVO> getSalesDetailInfo(@RequestBody QuotePresaleInfoQuery quotePresaleInfoQuery)
    {
        if(ObjectUtil.isEmpty(quotePresaleInfoQuery.getQuoteType())){
            quotePresaleInfoQuery.setQuoteType(ContractType.XX.getCode());
        }
        QuotePreSaleDetailInfoVO result = quotePresaleInfoService.getSalesDetailInfo(quotePresaleInfoQuery);
        return R.ok(result);
    }

    /**
     * 销售报价-待销售报价-详细报价详情
     */
    @Operation(summary = "销售报价-报价详情", description  = "销售报价-报价详情")
    @PostMapping("/getDetailInfoById")
    public R<QuotePresaleInfoVO> getDetailInfoById(@RequestBody QuotePresaleInfoQuery query)
    {
        QuotePresaleInfoVO result = quotePresaleInfoService.getDetailInfoById(query);
        return R.ok(result);
    }

    /**
     * 销售报价-重新销售报价
     */
    @Operation(summary = "销售报价-重新销售报价", description  = "销售报价-重新销售报价(需要opportunitiesId)")
    @PostMapping("/reSales")
    public R<QuotePreSaleDetailInfoVO> reSales(@RequestBody QuotePresaleInfoQuery query)
    {
        QuotePreSaleDetailInfoVO result = quotePresaleInfoService.reSales(query);
        return R.ok(result);
    }


    /**
     * 待销售报价-确认销售报价
     */
    @Operation(summary = "待销售报价-确认销售报价", description  = "待销售报价-确认销售报价")
    @Log(title = "待销售报价-确认销售报价", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('quote:sale:create','quote:rejected:update','quote:order:update')")
    @PostMapping("/addSalesQuotesVersion")
    public R<Integer> addSalesQuotesVersion(@RequestBody QuotePresaleInfoSaveDTO saveDTO)
    {
        return R.ok(quotePresaleInfoService.addSalesQuotesVersion(saveDTO));
    }

    /**
     * 销售报价-签约申请页
     */
    @Operation(summary = "销售报价-签约申请页", description  = "销售报价-签约申请页")
    @PostMapping("/approvalAndSignInfo")
    public R<SignDetailInfoVO> approvalAndSignInfo(@RequestBody BaseDTO dto)
    {
        SignDetailInfoVO result = quotePresaleInfoService.approvalAndSignInfo(dto.getOpportunitiesId(),true);
        return R.ok(result);
    }

    /**
     * 销售报价-待销售报价审批页
     */
    @Operation(summary = "销售报价-待销售报价审批页", description  = "销售报价-待销售报价审批页")
    @PostMapping("/approvalInfo")
    public R<SignDetailInfoVO> approvalInfo(@RequestBody BaseDTO dto)
    {
        SignDetailInfoVO result = quotePresaleInfoService.approvalAndSignInfo(dto.getOpportunitiesId(),false);
        return R.ok(result);
    }
    /**
     * 存在成本信息-需要opportunitiesId
     */
    @Operation(summary = "存在成本信息-需要opportunitiesId", description  = "存在成本信息-需要opportunitiesId")
    @PostMapping("/existCostInfo")
    public R<Boolean> existCostInfo(@RequestBody BaseDTO dto)
    {
        Boolean result = quotePresaleInfoService.existCostInfo(dto.getOpportunitiesId());
        return R.ok(result);
    }



}

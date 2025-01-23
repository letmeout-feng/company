package com.internal.common.core.domain.dto;

import com.internal.common.core.domain.BaseEntity;
import com.internal.common.core.domain.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 邮箱发送信息
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@Schema(name = "SendEmailInfoDTO", description = "邮箱发送信息")
public class SendEmailInfoDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 发送人信息
     */
    @Schema(description = "发送人信息")
    private SysUser sysUser;

    /**
     * 模版转换信息
     */
    @Schema(description = "模版转换信息")
    private EmailModelInfoConvertDTO emailModelInfoConvertDTO;

    /**
     * 主题转换信息
     */
    @Schema(description = "主题转换信息")
    private EmailSubjectInfoConvertDTO emailSubjectInfoConvertDTO;

    /**
     * 抄送人列表
     */
    @Schema(description = "抄送人列表")
    private List<SysUser> copyUser = new LinkedList<>();

    /**
     * 附件信息
     */
    @Schema(description = "附件信息")
    private Map<String, byte[]> attachments;

    public SendEmailInfoDTO(SysUser sysUser, EmailModelInfoConvertDTO emailModelInfoConvertDTO, EmailSubjectInfoConvertDTO emailSubjectInfoConvertDTO, List<SysUser> copyUser, Map<String, byte[]> attachments) {
        this.sysUser = sysUser;
        this.emailModelInfoConvertDTO = emailModelInfoConvertDTO;
        this.emailSubjectInfoConvertDTO = emailSubjectInfoConvertDTO;
        this.copyUser = copyUser;
        this.attachments = attachments;
    }
}
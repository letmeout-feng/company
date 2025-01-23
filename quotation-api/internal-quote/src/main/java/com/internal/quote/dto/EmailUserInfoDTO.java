package com.internal.quote.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zdliu
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Schema(name = "EmailUserInfoDTO", description = "邮件用户相关信息")
public class EmailUserInfoDTO {

    @Schema(description = "类型")
    private String type;

    @Schema(description = "用户名")
    private String nickName;

    @Schema(description = "是否显示关闭按钮")
    private boolean isDefault;

    @Schema(description = "用户id")
    private String userId;


}
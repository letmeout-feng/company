package com.internal.framework.config;

import com.internal.common.config.InternalConfig;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Swagger2的接口配置
 *
 * @author every
 */
@Configuration
public class SwaggerConfig {

    /**
     * 系统基础配置
     */
    @Autowired
    private InternalConfig internalConfig;

    /**
     * 是否开启 Swagger
     */
    @Value("${swagger.enabled}")
    private boolean enabled;

    /**
     * 配置 OpenAPI 文档
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("内部管理系统_接口文档")
                        .description("用于管理集团旗下公司的人员信息,具体包括XXX,XXX模块...")
                        .version("版本号: " + internalConfig.getVersion())
                        .contact(new Contact()
                                .name(internalConfig.getName())
                                .url(null)
                                .email(null))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                // 设置安全模式（Bearer Token）
                .schemaRequirement("Authorization", new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization"))
                .security(Collections.singletonList(
                        new SecurityRequirement().addList("Authorization")
                ));
    }
}

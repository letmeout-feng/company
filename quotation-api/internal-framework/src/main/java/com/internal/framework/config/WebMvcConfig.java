package com.internal.framework.config;

import com.internal.common.constant.SwaggerConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 因为 父类WebMvcConfigurationSupport 重写了 extendMessageConverters 方法
 * 覆盖了原方法.导致了 配置文件中的该配置失效  #所有数字返回给前端的时候,都转成字符串 spring.jackson.generator.write-numbers-as-strings=true
 * 所以此处,我们自己重写 extendMessageConverters 方法
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 扩展MVC框架的消息转换器.请求数据返回的结果,在返回给前端之前进行一次转换.
     *
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter myConverter = new MappingJackson2HttpMessageConverter() {
            @Override
            protected boolean canWrite(MediaType mediaType) {
                // 获取当前请求路径
                RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                if (requestAttributes instanceof ServletRequestAttributes) {
                    HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                    String requestUri = request.getRequestURI();

                    // 如果请求路径在 STATIC_WITHE_PATH_LIST 中，跳过转换
                    for (String path : SwaggerConstant.STATIC_WITHE_PATH_LIST) {
                        if (path.endsWith("/**")) {
                            if (requestUri.startsWith(path.substring(0, path.length() - 3))) {
                                return false;
                            }
                        } else if (requestUri.equals(path)) {
                            return false;
                        }
                    }
                }
                return super.canWrite(mediaType);
            }
        };
        myConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0, myConverter);
    }
}
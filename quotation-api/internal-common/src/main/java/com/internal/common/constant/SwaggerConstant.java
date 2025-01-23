package com.internal.common.constant;

public class SwaggerConstant {

        /**
         * 允许匿名访问的静态资源路径列表
         */
        public static final String[] STATIC_WITHE_PATH_LIST = new String[]{
                "/",
                "/js/**",
                "/css/**",
                "/img/**",
                "/fonts/**",
                "/index.html",
                "/doc.html",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v3/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/druid/**"
        };

        /**
         * 允许匿名访问的静态资源存放位置列表
         */
        public static final String[] STATIC_WITHE_LOCATION_LIST = new String[]{
                "classpath:/static/",
                "classpath:/public/",
                "classpath:/META-INF/resources/"
        };


}
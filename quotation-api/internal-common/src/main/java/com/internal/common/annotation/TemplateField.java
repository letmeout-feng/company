package com.internal.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zdliu
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TemplateField {
    String value(); // 占位符名称
    String suffix() default ""; // 新增字段：后缀字符
    boolean formatAsCurrency() default false; // 新增字段：是否格式化为金额
}
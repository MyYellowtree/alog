package com.xiaoke.alog.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将该注解放在Controller方法上
 * 当用户访问该方法时则需要先进行Token认证（登录认证）
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenVerify {
    // 表明该方法是否需要Token认证
    boolean required() default true;
}

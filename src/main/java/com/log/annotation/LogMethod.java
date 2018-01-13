package com.log.annotation;

import java.lang.annotation.*;

/**
 * 记录日志注解
 * @author Chen
 * @version 1.0.0
 * @date 2017/7/17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogMethod {

    String value() default "";
}

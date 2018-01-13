package com.log.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启拦截器记录访问日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ControllerConfiguration.class})
public @interface EnableControllerLog {

}

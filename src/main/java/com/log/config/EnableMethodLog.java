package com.log.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启记录@LogMehtod标注方法调用日志
 * @author clq
 * @version 1.0.0
 * @date 2018/1/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({MethodConfiguration.class})
public @interface EnableMethodLog {

}

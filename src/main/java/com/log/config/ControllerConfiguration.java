package com.log.config;

import com.log.aop.ControllerAspect;
import com.log.interceptor.ControllerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;

/**
 * 日志拦截器配置
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ControllerConfiguration extends WebMvcConfigurerAdapter {
    private static final Log log = LogFactory.getLog(ControllerConfiguration.class);
    @Value("/admin/public/*")
    private String mvcLogPath;

    @Value("${mvc.log.path}")
    private String mvcUnlogPath;

    @Bean
    public ControllerAspect controllerAspect314() {
        return new ControllerAspect();
    }

    @Bean
    public MappedInterceptor mappedInterceptor124124124() {
        log.info("添加controller日志拦截器到" + mvcLogPath);
        if (StringUtils.isEmpty(mvcLogPath)) {
            return new MappedInterceptor(mvcLogPath.split(","), new ControllerInterceptor());
        } else {
            return new MappedInterceptor(mvcLogPath.split(","), mvcUnlogPath.split(","), new ControllerInterceptor());
        }

    }





}

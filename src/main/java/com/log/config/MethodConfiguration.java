package com.log.config;

import com.log.aop.MethodAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by clq on 2018/1/13.
 * aspect配置
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class MethodConfiguration {

    @Bean
    public MethodAspect logMethodAspect(){
        return new MethodAspect();
    }
}

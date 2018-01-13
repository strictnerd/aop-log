package com.log.aop;

import com.alibaba.fastjson.JSON;
import com.log.bean.ParameterBean;
import com.log.interceptor.ControllerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 记录Controller的调用日志
 *
 * @author Chen
 * @version 1.0.0
 */
@Slf4j
@Aspect
public class ControllerAspect {

    private static final Log log = LogFactory.getLog(ControllerAspect.class);

    /**
     * 拦截所有Controller注解的方法，记录日志
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* *.*(..)) && ( @within(org.springframework.web.bind.annotation.RestController)|| @within(org.springframework.stereotype.Controller) )")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
        /**
         *  是否需要记录日志标记
         * 由WebMvcLogInterceptor设置，所有被WebMvcLogInterceptor拦截的方法都会记录日志
         */
        long startTime = System.currentTimeMillis();
        ParameterBean parameterBean = ControllerInterceptor.param.get();
        if (parameterBean == null || !parameterBean.getLogTag()) {
            return joinPoint.proceed();
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String target = signature.getMethod().getName();
        if(args != null) {
            parameterBean.setParams(args);
            parameterBean.setMethod(target);
        }
        //执行被代理方法
        Object result = null;
        try {
            result = joinPoint.proceed();
        }catch (Exception e) {
            log.error("方法:"+target+",执行过程出错，错误日志:", e);
        }

        RestController restController = joinPoint.getTarget().getClass().getAnnotation(RestController.class);
        ResponseBody responseBody = null;
        if (joinPoint.getSignature() instanceof MethodSignature) {
            responseBody = signature.getMethod().getAnnotation(ResponseBody.class);
        }
        //记录内容
        if (restController != null || responseBody != null) {
            try {
                parameterBean.setRetValue(result);
                parameterBean.setTime(System.currentTimeMillis()-startTime);
            } catch (Exception e) {
                log.info("输出日志异常：", e);
            }finally {
                log.info(JSON.toJSONString(parameterBean));
                ControllerInterceptor.param.remove();
            }

        }
        return result;

    }
}

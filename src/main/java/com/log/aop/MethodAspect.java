package com.log.aop;

import com.alibaba.fastjson.JSON;
import com.log.annotation.LogMethod;
import com.log.bean.ParameterBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;

/**
 * 记录@LogMethod注解方法的调用日志
 * @author Chen
 * @version 1.0.0
 */
@Slf4j
@Aspect
public class MethodAspect {

    private static final Log log = LogFactory.getLog(MethodAspect.class);

    @Around("execution(* *.*(..)) && @annotation(com.log.annotation.LogMethod)")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        ParameterBean parameterBean = new ParameterBean();
        //方法名
        String target = joinPoint.getSignature().getName();
        parameterBean.setMethod(target);
        if(joinPoint.getSignature() instanceof MethodSignature){
            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
            LogMethod logMethod = signature.getMethod().getAnnotation(LogMethod.class);
            if (logMethod != null && logMethod.value() != null) {
                //输出方法值
                target = logMethod.value();
            }
        }
        //参数
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (int j = 0; j < parameterAnnotations[i].length; j++) {
                    if (parameterAnnotations[i][j].annotationType().equals(RequestBody.class)) {
                        parameterBean.setParams(args[i]);
                    }
                }
            }
        } catch (Exception e) {
            log.info("序列化方法参数异常，无法输出日志");
        }
        Object result = null;
        try {
            //执行
            result = joinPoint.proceed();
            if (result != null) {
                parameterBean.setRetValue(result);
            }
            parameterBean.setTime((System.currentTimeMillis()-startTime));
        } catch (Exception e) {
            log.info("序列化响应异常,无法输出日志");
        }finally {
            log.info(JSON.toJSONString(parameterBean));
        }
        return result;

    }
}

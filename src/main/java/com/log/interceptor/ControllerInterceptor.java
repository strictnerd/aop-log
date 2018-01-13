package com.log.interceptor;

import com.log.bean.ParameterBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * mvc日志拦截器，记录请求信息和响应信息
 * 目前无法读取请求body中的内容
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/7/14
 */
@Slf4j
public class ControllerInterceptor implements HandlerInterceptor {

    private static final Log log = LogFactory.getLog(ControllerInterceptor.class);

    public static final ThreadLocal<ParameterBean> param = new ThreadLocal();
    /**
     * 记录请求信息
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ParameterBean bean = new ParameterBean();
        bean.setUrl(request.getRequestURI());
        bean.setLogTag(true);
        param.set(bean);
        return true;
    }

    /**
     * 记录响应信息
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            log.info("--------------------响应信息------------------------");
            log.info("请求uri " + request.getRequestURI());
            log.info("响应 view:" + modelAndView.getViewName());
            Map<String, Object> results = modelAndView.getModel();
            for (Map.Entry<String, Object> result : results.entrySet()) {
                try {
                    log.info(result.getKey() + "=" + result.getValue());
                } catch (Exception e) {
                    log.info("输出结果"+result.getKey()+"异常,无法输出.......");
                }
            }
            log.info("--------------------------------------------------------");
        }
    }

    /**
     * 异常还是用异常拦截栈记录
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ControllerInterceptor.param.remove();
    }

}
package com.log.bean;

/**
 * Created by clq on 2018/1/13.
 */
public class ParameterBean {
    /**
     * 请求参数
     */
    private Object params;

    /**
     * 请求耗费时间
     */
    private Long time;
    /**
     * 日否记录日志标记
     */
    private Boolean logTag;
    /**
     * 返回值
     */
    private Object retValue;
    /**
     * 请求地址
     */
    private String url;

    /**
     * 方法名称
     */
    private String method;

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Object getRetValue() {
        return retValue;
    }
    public void setRetValue(Object retValue) {
        this.retValue = retValue;
    }

    public Boolean getLogTag() {
        return logTag;
    }

    public void setLogTag(Boolean logTag) {
        this.logTag = logTag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

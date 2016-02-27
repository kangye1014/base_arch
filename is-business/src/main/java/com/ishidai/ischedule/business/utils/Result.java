/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ishidai.ischedule.business.utils;

import java.io.Serializable;

/**
 * 操作结果集
 * 
 * @author luyujian
 * @param <T>
 */
public class Result<T> implements Serializable {

    /**
     * @author luyujian
     */
    private static final long serialVersionUID = -8275952484393082674L;

    private Object code = -1;

    private String message = "";

    private T bean;

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public void setCodeMessage(Object code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result [code=" + code + ", message=" + message + "]";
    }

}

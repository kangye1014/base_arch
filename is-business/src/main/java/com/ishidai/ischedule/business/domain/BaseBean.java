/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ishidai.ischedule.business.domain;

import java.io.Serializable;

/**
 * 基础Bean
 * 
 * @author luyujian
 */
public class BaseBean implements Serializable {

    /**
     * @author luyujian
     */
    private static final long serialVersionUID = 1880270714923798776L;
    /**
     * id
     */
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseBean other = (BaseBean) obj;
        if (id != other.id)
            return false;
        return true;
    }

}

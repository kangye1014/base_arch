package com.ishidai.ischedule.business.domain;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用户个人消息
 * 
 * @author xiaoting
 */
public class UserPersonalMessage extends BaseBean {

    private static final long serialVersionUID = -5696715523786414835L;

    /**
     * 关联的用户ID
     */
    private Long userId;

    /**
     * 消息
     */
    private String message;
    /**
     * 是否已读
     */
    private Integer isRead;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 消息类型
     */
    private Integer type;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}

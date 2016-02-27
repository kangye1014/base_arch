package com.ishidai.ischedule.business.domain;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 标的状态改变记录
 * 
 * @author wangmeng
 * @time 2014年12月16日下午9:52:19
 */
public class LoanStatusRecord {

    private long id;
    private long loanId; // 标的id
    private int status; // 标的改变之后的状态
    private String content; // 状态详解
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime; // 操作时间
    private long operateStaffId; // 操作人

    // private String opinion; //审批意见
    // private int type; //审批形式

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public long getOperateStaffId() {
        return operateStaffId;
    }

    public void setOperateStaffId(long operateStaffId) {
        this.operateStaffId = operateStaffId;
    }
}

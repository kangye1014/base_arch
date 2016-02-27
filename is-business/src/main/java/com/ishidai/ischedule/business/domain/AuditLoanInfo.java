package com.ishidai.ischedule.business.domain;

/**
 * Created by 37Y6D32 on 2015/5/27.
 */
public class AuditLoanInfo extends Loan {

    private String applyType;

    private String cellPhoneOsType;

    private String statusResult;// 标的状态文字形式

    private String phone;// 手机号码

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatusResult() {
        return statusResult;
    }

    public void setStatusResult(String statusResult) {
        this.statusResult = statusResult;
    }

    public String getCellPhoneOsType() {
        return cellPhoneOsType;
    }

    public void setCellPhoneOsType(String cellPhoneOsType) {
        this.cellPhoneOsType = cellPhoneOsType;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

}

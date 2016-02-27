package com.ishidai.ischedule.business.enums;

public enum MessageTypeEnum {

    LOAN_AUDIT_THROUGH(1, "贷款审核通过"),

    LOAN_AUDIT_NOT_THROUGH(2, "贷款审核不通过"),

    LOAN_NOTICE(3, "放款通知"),

    REPAMENT_NOTICE(4, "还款通知"),

    OVERDUE_RELIEF_THROUGH(5, "逾期减免审核通过"),

    OVERDUE_RELIEF_NOT_THROUGH(6, "逾期减免审核不通过"),

    STATUS_F_NOT_THROUGH(7, "进件状态改为待补充资料"),

    D11_NOTICE(8, "用户双11反息通知");

    private int type;
    private String desc;

    private MessageTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}

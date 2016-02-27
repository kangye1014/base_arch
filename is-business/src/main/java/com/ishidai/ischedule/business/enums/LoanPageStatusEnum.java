package com.ishidai.ischedule.business.enums;

import java.util.EnumSet;

public enum LoanPageStatusEnum {

    AUDITING(1, "审核中"), RAISEING(2, "筹款中"), AUDITNOTPASS(3, "审核不通过"), LOANREPAY(4, "还款中"), LOANLOSE(5, "已流标"), END(6,
            "标的结束"), CONFIGING(7, "待用户确认"), CONFIGFAIL(8, "用户放弃确认"), NONEXISTENT(9, "无记录");

    private final String value;
    private final int type;

    LoanPageStatusEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static LoanPageStatusEnum getEnumByType(int type) {
        for (LoanPageStatusEnum myEnum : EnumSet.allOf(LoanPageStatusEnum.class)) {
            if (myEnum.getType() == type) {
                return myEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }
}

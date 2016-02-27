package com.ishidai.ischedule.business.enums;

import java.util.EnumSet;

/**
 * 标的状态类型
 * 
 * @author sunquanchao
 */
public enum LoanStatusEnum {

    WAITCHECK(0, "待审核"), RISKCHECKING(1, "审核中（风控）"), RISKPASS(2, "审核通过（风控）(待人工审核)"), AUDITNO(3, "审核不通过（风控）"), OPERATENO(
            4, "审核通过(用户确认中)"), PUSHSUCCESS(5, "推送成功"), PUSHFAIL(6, "推送失败"), LOANOPEN(7, "已发标"), LOANFULL(8, "已满标"), LOANLOSE(
            9, "已流标"), LOANED(10, "已放款"), LOANREPAY(11, "还款中"), END(12, "还款已完成"), DELETE(13, "已删除"), OPERATEPASS(14,
            "审核不通过（人工）"), CONFIRMSUCCESS(15, "用户确认成功（待推送）"), CONFIRMFAIL(16, "用户确认失败"), OVERDUE(17, "已逾期"), WAITSUPPLEMMENT(
                    90, "待补充资料"), CHECKINGAPPCONFIRMED(91, "待用户APP端确认"), GIVEUPWAITSUPPLEMMENT(92, "放弃补充资料"), GIVEUPAPPCONFIRMED(
                            93, "用户放弃APP端确认"), ANTIFRAUDAUDIT(18, "反欺诈审核中"), ANTIFRAUDCOMPLETE(19, "反欺诈完成");


    private final String value;
    private final int type;

    LoanStatusEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static LoanStatusEnum getEnumByType(int type) {
        for (LoanStatusEnum myEnum : EnumSet.allOf(LoanStatusEnum.class)) {
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

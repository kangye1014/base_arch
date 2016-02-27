package com.ishidai.ischedule.business.utils;

import com.ishidai.ischedule.business.enums.LoanPageStatusEnum;
import com.ishidai.ischedule.business.enums.LoanStatusEnum;

public class StatusUtils {

    /**
     * H5端标的状态展示
     * 
     * @param status
     * @return
     * @author wangmeng
     * @time 2014年12月31日下午1:56:21
     */
    public static int getStatusShow(int status) {
        // 生成页面展示状态
        int result = 0;
        if (status == LoanStatusEnum.WAITCHECK.getType() || status == LoanStatusEnum.RISKCHECKING.getType()
                || status == LoanStatusEnum.RISKPASS.getType() || status == LoanStatusEnum.WAITSUPPLEMMENT.getType()) {
            result = LoanPageStatusEnum.AUDITING.getType();// 审核中 1
        } else if (status == LoanStatusEnum.AUDITNO.getType() || status == LoanStatusEnum.OPERATEPASS.getType()) {
            result = LoanPageStatusEnum.AUDITNOTPASS.getType();// 审核不通过 3
        } else if (status == LoanStatusEnum.CONFIRMSUCCESS.getType() || status == LoanStatusEnum.PUSHFAIL.getType()
                || status == LoanStatusEnum.PUSHSUCCESS.getType() || status == LoanStatusEnum.LOANOPEN.getType()
                || status == LoanStatusEnum.LOANFULL.getType() || status == LoanStatusEnum.LOANED.getType()) {
            result = LoanPageStatusEnum.RAISEING.getType();// 筹款中 2
        } else if (status == LoanStatusEnum.LOANREPAY.getType()) {
            result = LoanPageStatusEnum.LOANREPAY.getType();// 还款中 4
        } else if (status == LoanStatusEnum.END.getType()) {
            result = LoanPageStatusEnum.END.getType();// 还款已完成 6
        } else if (status == LoanStatusEnum.LOANLOSE.getType()) {
            result = LoanPageStatusEnum.LOANLOSE.getType();// 已流标 5
        } else if (status == LoanStatusEnum.OPERATENO.getType()) {
            result = LoanPageStatusEnum.CONFIGING.getType();// 待用户确认 7
        } else if (status == LoanStatusEnum.CONFIRMFAIL.getType()) {
            result = LoanPageStatusEnum.CONFIGFAIL.getType();// 用户确认失败 8
        }
        return result;
    }
}

package com.ishidai.ischedule.business.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.ishidai.ischedule.business.enums.LoanStatusEnum;

public class LoanUtils {

    /**
     * @note 验证 标的是否处于 待审核、审核中、已满标、已发标、推送成功、推送失败、用户确认成功、已还款、还款中
     * @param status
     * @return
     * @author wangmeng
     * @date 2015年8月7日上午11:08:15
     */
    public static boolean vilidUserLoanPermission(int status) {
        if (status == LoanStatusEnum.WAITCHECK.getType() || status == LoanStatusEnum.RISKCHECKING.getType()) {
            return true;
        } else if (status == LoanStatusEnum.RISKPASS.getType() || status == LoanStatusEnum.OPERATENO.getType()) {
            return true;
        } else if (status == LoanStatusEnum.PUSHSUCCESS.getType() || status == LoanStatusEnum.PUSHFAIL.getType()) {
            return true;
        } else if (status == LoanStatusEnum.LOANOPEN.getType() || status == LoanStatusEnum.LOANFULL.getType()) {
            return true;
        } else if (status == LoanStatusEnum.LOANED.getType() || status == LoanStatusEnum.LOANREPAY.getType()) {
            return true;
        } else if (status == LoanStatusEnum.CONFIRMSUCCESS.getType()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 标的审核时间已经超过30天
     * 
     * @author wangmeng
     * @time 2015年1月14日下午2:49:04
     */
    public static boolean vilidateAuditTime(Date auditTime) {
        long days = DateUtils.daysBetween(auditTime, new Date());
        if (days < 30) {
            return false;
        }
        return true;
    }

    public static int getRepayStatus(int type, int status) {
        int repayStatus = 0;
        if (type == 0) {// 0:当期 status:未还 0：当期-未还 1：当期已还
            repayStatus = (status == 1 ? 1 : 0);
        } else {// 其他代表逾期 2:逾期-未还
            repayStatus = 2;
        }
        return repayStatus;
    }

    /**
     * @对身份证编号进行模糊处理
     * @param idNo
     * @return
     * @author wangmeng
     * @time 2015年4月4日下午11:49:31
     */
    public static String getIdNoStr(String idNo) {
        if (StringUtils.isEmpty(idNo)) {
            return "";
        }
        if (idNo.length() != 18) {
            return idNo;
        }
        return idNo.substring(0, 2) + "************" + idNo.substring(14, 18);
    }

}

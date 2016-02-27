package com.ishidai.ischedule.business.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ishidai.ischedule.business.dao.LoanDao;
import com.ishidai.ischedule.business.domain.AuditLoanInfo;
import com.ishidai.ischedule.business.domain.Loan;
import com.ishidai.ischedule.business.domain.LoanStatusRecord;
import com.ishidai.ischedule.business.enums.CommSourceEnum;

@Component
public class LoanService {

    @Autowired
    private LoanDao loanDao;

    public List<Map<String, Object>> queryLoanByRequestIds(List<Long> requestIds, CommSourceEnum puhui) {
        Map<String, Object> params = new HashMap<>();
        params.put("ids", requestIds);
        if (null != puhui)
            params.put("source", puhui.getType());

        List<Map<String, Object>> result = loanDao.queryLoanByRequestIds(params);
        return result;
    }

    public List<AuditLoanInfo> getLoanListByParams(Map<String, Object> hashMap) {
        return loanDao.getLoanListByParams(hashMap);
    }

    public void addStatusRecord(LoanStatusRecord loanStatus) {
        loanDao.addStatusRecord(loanStatus);
    }

    public long updateLoanConfig(Loan loan) {
        return loanDao.updateLoanConfig(loan);
    }

    public Loan getLoanById(long id) {
        return loanDao.getLoanById(id);
    }
}

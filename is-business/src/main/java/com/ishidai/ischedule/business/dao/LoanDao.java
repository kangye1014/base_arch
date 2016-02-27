package com.ishidai.ischedule.business.dao;

import java.util.List;
import java.util.Map;

import com.ishidai.ischedule.business.domain.AuditLoanInfo;
import com.ishidai.ischedule.business.domain.Loan;
import com.ishidai.ischedule.business.domain.LoanStatusRecord;
import com.ishidai.ischedule.task.dao.BaseDao;

public interface LoanDao extends BaseDao {

    List<Map<String, Object>> queryLoanByRequestIds(Map<String, Object> params);

    List<AuditLoanInfo> getLoanListByParams(Map<String, Object> hashMap);

    void addStatusRecord(LoanStatusRecord loanStatus);

    public long updateLoanConfig(Loan loan);

    Loan getLoanById(long id);
}

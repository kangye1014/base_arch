package com.ishidai.ischedule.test.base;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.ishidai.ischedule.business.domain.AuditLoanInfo;
import com.ishidai.ischedule.business.domain.Loan;
import com.ishidai.ischedule.jobs.AbandonLoanJob;

public class AbandonLoanJobTest extends BaseTest {

    @Autowired
    private AbandonLoanJob abandonLoanJob;

    // @Test
    public void addStatusRecordTest() {

        long lonaId = 1447651;

        AuditLoanInfo loan = new AuditLoanInfo();
        loan.setId(lonaId);
        abandonLoanJob.addStatusRecord(loan);

        // validateSQL:select * from p_status_record order by id desc limit 12;
    }

    // @Test
    public void updateLoanConfigTest() {

        long lonaId = 1447651;

        AuditLoanInfo loan = new AuditLoanInfo();
        loan.setId(lonaId);
        loan.setVersion(7);// 版本号，先查
        abandonLoanJob.updateLoanConfig(loan);

        // validateSQL:select * from p_loan where id = 1447651;
    }

    // @Test
    public void getAllOperatenoStatusLoan() {
        List<AuditLoanInfo> loanList = abandonLoanJob.getAllOperatenoStatusLoan();
        for (int i = 0; i < loanList.size(); i++) {
            Loan loan = loanList.get(i);
            System.out.println(JSON.toJSONString(loan));
        }
        System.out.println(loanList.size());
    }

    @Test
    public void abandonLoan() {
        // SQL: update p_loan set status = 4 limit 100;
        abandonLoanJob.abandonLoan();
    }
}

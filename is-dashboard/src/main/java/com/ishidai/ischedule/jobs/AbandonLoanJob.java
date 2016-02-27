package com.ishidai.ischedule.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ishidai.ischedule.business.domain.AuditLoanInfo;
import com.ishidai.ischedule.business.domain.Loan;
import com.ishidai.ischedule.business.domain.LoanStatusRecord;
import com.ishidai.ischedule.business.domain.User;
import com.ishidai.ischedule.business.enums.CommSourceEnum;
import com.ishidai.ischedule.business.enums.LoanStatusEnum;
import com.ishidai.ischedule.business.services.LoanService;
import com.ishidai.ischedule.business.services.UserService;
import com.ishidai.ischedule.business.utils.ConstantSet;
import com.ishidai.ischedule.business.utils.UuidUtil;
import com.ishidai.ischedule.params.JobContent;
import com.puhui.credit.enumeration.ChannelType;
import com.puhui.credit.enumeration.LoanType;
import com.puhui.credit.service.CreditSystemQuotaUpdateService;
import com.puhui.credit.vo.param.AdditionAvailableQuotaVo;
import com.puhui.credit.vo.returnvalue.BaseResponseVo;

public class AbandonLoanJob extends JobContent {

    private final static Logger logger = LoggerFactory.getLogger(AbandonLoanJob.class);
    @Autowired
    CreditSystemQuotaUpdateService creditSystemQuotaUpdateService;
    @Autowired
    private UserService userManager;

    @Autowired
    LoanService loanService;

    public void abandonLoan() {

        logger.info("用户放弃或者确认标的(自动跑批使用) 五天后  用户自动放弃借款**每两个小时跑批**开始");
        // 第一步：查询所有的人工审核已通过 待确认的标的
        List<AuditLoanInfo> loanList = getAllOperatenoStatusLoan();
        if (CollectionUtils.isEmpty(loanList)) {
            logger.info("未找到人工审核已通过 待确认的标的");
            return;
        }

        for (int i = 0; i < loanList.size(); i++) {

            Loan loan = loanList.get(i);
            logger.debug("当前进件loan:{}", JSON.toJSONString(loan));
            if ((loan.getOperateAuditTime() == null || loan.getSource() == null)
                    || checkoutAuditTimeUntil(loan.getOperateAuditTime(), loan.getSource())) {
                logger.info("不满足放弃条件,跳过");
                continue;
            }

            try {
                // 修改标的已放弃或者标的已确认
                updateLoanConfig(loan);
                // 添加修改标的状态记录
                addStatusRecord(loan);
                // 通知信审
                noticeCredit(loan, loan.getId());
                logger.info("LoanServiceImpl:abandonOLoan***五天后 用户userId=" + loan.getUserId() + "" + "**realName="
                        + loan.getRealName() + "的标的LoanId=" + loan.getId() + "自动放弃借款。");
            } catch (Exception e) {
                logger.error("修改标的{}已放弃或者标的已确认错误:{}", JSON.toJSONString(loan), e.getMessage());
            }

        }
        // 打印执行结果
        printJobResultDetails();
    }

    private Boolean checkoutAuditTimeUntil(Date auditTime, Integer source) {

        logger.debug("审核auditTime：{}, source:{}", auditTime, source);
        Date date = new Date();
        // 间隔时间
        long dutiTime = date.getTime() - auditTime.getTime();
        // 最大的间隔时间
        Long limitHours = null;

        try {
            limitHours = getExtJosnObject().getLong(String.valueOf(source));
        } catch (Exception e) {
            logger.warn("ext_params:{} 未配置或配置错误不能正确识别渠道{}", getExtJosnObject(), source);
        }

        if (null == limitHours)
            limitHours = ConstantSet.MODEL_CONSTANTS_ABANDON_DAYS;

        long untiTime = (long) limitHours * 1000 * 60 * 60;
        logger.debug("审核limitHours：{}, dutiTime:{},untiTime{},rerult:{}", limitHours, dutiTime, untiTime,
                dutiTime < untiTime);

        return dutiTime < untiTime;
    }

    private void printJobResultDetails() {
        // to be continue
        logger.info("用户放弃或者确认标的(自动跑批使用) 五天后  用户自动放弃借款**每两个小时跑批**结束");
    }

    public void addStatusRecord(Loan loan) {
        LoanStatusRecord loanStatus = new LoanStatusRecord();
        loanStatus.setLoanId(loan.getId());
        loanStatus.setStatus(LoanStatusEnum.CONFIRMFAIL.getType());
        loanStatus.setContent("标" + loanStatus.getLoanId() + ":用户放弃该标的");
        loanStatus.setOperateTime(new Date());
        loanStatus.setOperateStaffId(0l);// 无操作人则为0
        loanService.addStatusRecord(loanStatus);
    }

    public long updateLoanConfig(Loan loan) {
        Loan newLoan = new Loan();
        newLoan.setId(loan.getId());
        newLoan.setStatus(LoanStatusEnum.CONFIRMFAIL.getType());
        newLoan.setConfirmTime(new Date());
        newLoan.setVersion(loan.getVersion());
        long resultCounts = loanService.updateLoanConfig(newLoan);
        return resultCounts;
    }

    public List<AuditLoanInfo> getAllOperatenoStatusLoan() {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("status", LoanStatusEnum.OPERATENO.getType());
        List<AuditLoanInfo> loanList = loanService.getLoanListByParams(hashMap);
        return loanList;
    }

    public void noticeCredit(Loan openLoan) {

        if (openLoan == null) {
            logger.error("通知信审为空");
            return;
        }

        logger.info("当前loan source :{}", openLoan.getSource());
        if (CommSourceEnum.JIEAPP.getType() == openLoan.getSource()) {// 提高可用额度
            AdditionAvailableQuotaVo<Object> vo = new AdditionAvailableQuotaVo<>();
            vo.setUuid(UuidUtil.createUUID());
            User user = userManager.findUserById(openLoan.getUserId());
            vo.setIdNumber(user != null ? user.getIdNo() : "");
            vo.setAdditionQuota(openLoan.getRealAmount());
            vo.setLoanType(LoanType.CASH);
            vo.setChannelType(ChannelType.JIE_A_OL);
            logger.info("身份证号：{},creditSystemQuotaUpdateService.additionAvailableQuota入参：{}", vo.getIdNumber(),
                    ReflectionToStringBuilder.toString(vo));
            BaseResponseVo<Object> obj;
            try {
                obj = creditSystemQuotaUpdateService.additionAvailableQuota(vo);
                logger.info("身份证号：{},creditSystemQuotaUpdateService.additionAvailableQuota输出结果：{}", vo.getIdNumber(),
                        ReflectionToStringBuilder.toString(obj));
                if ("00000".equals(obj.getCode())) {
                    logger.info("身份证号：{},creditSystemQuotaUpdateService.additionAvailableQuota提高可用额度成功",
                            vo.getIdNumber());
                } else {
                    logger.info("身份证号：{},creditSystemQuotaUpdateService.additionAvailableQuota提高可用额度失败{}",
                            vo.getIdNumber(), obj.getMsg());
                }
            } catch (Exception e) {
                logger.error("身份证号：{},creditSystemQuotaUpdateService.additionAvailableQuota提高可用额度出现异常：{},{}",
                        vo.getIdNumber(), e.getMessage(), e);
            }
        }
    }

    public void noticeCredit(Loan loan, long id) {
        if (null == loan || null == loan.getSource())
            loan = loanService.getLoanById(id);
        noticeCredit(loan);
    }

    public static void main(String[] args) {
        String ji = "{\"2\":3,\"3\":4,\"e\": 3}";
        JSONObject os = JSON.parseObject(ji);
        System.out.println(os.getString("e"));
        System.out.println(os.getInteger(String.valueOf(3)));
        System.out.println(os.getLong(String.valueOf(5)));

        List<AuditLoanInfo> loanList = new ArrayList<>();
        System.out.println(CollectionUtils.isEmpty(loanList));
        loanList = null;
        System.out.println(CollectionUtils.isEmpty(loanList));
    }

}

package com.ishidai.ischedule.jobs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.ishidai.dubbo.api.ApiEnum;
import com.ishidai.dubbo.api.ApiEnum.OpStatus;
import com.ishidai.dubbo.api.Result;
import com.ishidai.dubbo.api.SendMQService;
import com.ishidai.dubbo.api.SmsService;
import com.ishidai.dubbo.dto.user.UserMessage;
import com.ishidai.ischedule.business.enums.CommSourceEnum;
import com.ishidai.ischedule.business.enums.MessageTypeEnum;
import com.ishidai.ischedule.business.services.AppPushService;
import com.ishidai.ischedule.business.services.LoanService;
import com.ishidai.ischedule.business.utils.ConstantSet;
import com.ishidai.ischedule.business.utils.ContextHandleUtils;
import com.ishidai.ischedule.business.utils.DateUtils;
import com.ishidai.ischedule.business.utils.ResultCodeConstants;
import com.ishidai.ischedule.params.JobContent;
import com.puhui.core.api.RepayQueryService;
import com.puhui.core.vo.UnRepayLendVo;

/**
 * 逾期还款消息推送任务
 * 
 * @author kangye
 * @Date 2015年11月2日
 */
public class RemindRepaymentPushJob extends JobContent {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AppPushService appPushService;
    @Autowired
    private RepayQueryService repayQueryService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SendMQService sendMQService;

    private boolean enabled = false;
    private Date dateLimit;

    private List<MessagePushResult> sucessResultSet;;
    private List<MessagePushResult> errorResultSet;
    private ExecutorService pool = null;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void executePush() {

        logger.info("当前任务绑定参数是:{}", getExtJosnObject());

        Calendar cal1 = Calendar.getInstance();
        Integer date;
        try {
            date = getExtJosnObject().getInteger("date");
        } catch (Exception e1) {
            date = null;
        }
        cal1.add(Calendar.DATE, +(null == date ? 3 : date));

        // 重新设置BEAN下的状态变量，非线程安全
        dateLimit = cal1.getTime();
        sucessResultSet = Collections.synchronizedList(new ArrayList());
        errorResultSet = Collections.synchronizedList(new ArrayList());
        pool = Executors.newFixedThreadPool(10);

        if (enabled == false) {
            logger.info("取消：提前3天，推送还款通知");
            return;
        }

        logger.info("开始推送提醒还款消息：{}", DateUtils.dateToStr(new Date(), DateUtils.FORMAT_DATE));
        try {
            pushRemindMessage();
        } catch (Exception e) {
            logger.error("消息推送异常:{}", e.getMessage());
        }
        logger.info("提醒还款消息推送完毕：{}", DateUtils.dateToStr(new Date(), DateUtils.FORMAT_DATE));
    }

    public void pushRemindMessage() {

        // 调贷后接口查询三天后还款记录
        List<UnRepayLendVo> unRepayLendVos = repayQueryService.queryUnRepayLendVoByDate(dateLimit,
                ConstantSet.SOURCE_CODE);
        if (CollectionUtils.isEmpty(unRepayLendVos)) {
            logger.info("未查询到需要推送提醒还款消息的还款记录");
            return;
        }
        logger.info("共{}条还款提醒记录", unRepayLendVos.size());
        if (!isInPushTime()) {
            logger.info("不在推送/短信时间段内");
            return;
        }
        List<Long> requestIds = new ArrayList<Long>();
        for (UnRepayLendVo unRepayLendVo : unRepayLendVos) {
            requestIds.add(unRepayLendVo.getCoreLendRequestId());
        }
        logger.info("提醒core_request_id集合是{}", requestIds);

        // 从匹配库中所有对应的进件
        List<Map<String, Object>> bePushedLoans = loanService.queryLoanByRequestIds(requestIds, null);
        if (CollectionUtils.isEmpty(bePushedLoans)) {
            logger.info("未找到任何匹配需推送记录的进件");
            return;
        }
        logger.info("共找到{}条匹配进件", bePushedLoans.size());
        // printAllMatchCoreRequestIds(bePushedLoans);

        // 推送
        for (final Map<String, Object> bepushLoan : bePushedLoans) {
            for (final UnRepayLendVo unRepayLendVo : unRepayLendVos) {

                Long coreRequestId = (Long) bepushLoan.get("core_request_id");
                if (unRepayLendVo.getCoreLendRequestId() == coreRequestId) {

                    final PushRemindMessage pushRemindMessage = getPushRemindMessage((Integer) bepushLoan.get("source"));
                    pool.execute(new Runnable() {
                        public void run() {
                            MessagePushResult result = pushRemindMessage.pushRemindMessageThread(bepushLoan,
                                    unRepayLendVo);

                            if (result.getCode().equals(ResultCodeConstants.SUCCESS)) {
                                sucessResultSet.add(result);
                            } else {
                                errorResultSet.add(result);
                            }

                        }
                    });

                    break;
                }
            }
        }

        // 等待运行结束
        pool.shutdown();
        try {
            // 最大可执行时间30分钟
            pool.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            logger.error("线程运行异常中断:{}", e.getMessage());
        }

        // 推送情况汇总
        printDetialexecLogs();
    }

    // 打印所有匹配的core_request_id ,调试用
    @SuppressWarnings("unused")
    private void printAllMatchCoreRequestIds(List<Map<String, Object>> bePushedLoans) {
        List<Long> requestIds = new ArrayList<Long>();
        for (Map<String, Object> bepushLoan : bePushedLoans) {
            requestIds.add((Long) bepushLoan.get("core_request_id"));
        }
        logger.info("所有匹配的core_request_id集合是{}", requestIds);
    }

    public void printDetialexecLogs() {
        logger.info("#################################################################################################################");
        logger.info("## 还款提醒消息推送结束,{}条推送成功, 明细:{}", sucessResultSet.size(), sucessResultSet);
        logger.info("## 还款提醒消息推送结束,{}条推送失败, 明细:{}", errorResultSet.size(), errorResultSet);
        logger.info("#################################################################################################################");
    }

    /**
     * 推送处理器的策略
     */
    public PushRemindMessage getPushRemindMessage(int source) {
        if (source == CommSourceEnum.PUHUI.getType()) {
            return new PushAppNotice();
        } else if (source == CommSourceEnum.JIEAPP.getType()) {
            return new PushMq(source);
        } else if (source == CommSourceEnum.WEIXIN.getType()) {
            return new PushMq(source);
        } else {
            return new PushShortMessage();
        }
    }

    static enum MessageType {
        MQ, SHORT_MESSGE, APP_NOTICE
    }

    @SuppressWarnings("serial")
    private class MessagePushResult extends com.ishidai.ischedule.business.utils.Result<UnRepayLendVo> {

        private MessageType messageType;

        public MessageType getMessageType() {
            return messageType;
        }

        public void setMessageType(MessageType messageType) {
            this.messageType = messageType;
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            if (getCode().equals(ResultCodeConstants.SUCCESS)) {
                sb.append(getBean().getCoreLendRequestId());
                sb.append("(");
                sb.append(getMessageType().name());
                sb.append(")");
            } else {
                sb.append(getBean().getCoreLendRequestId());
                sb.append("(");
                sb.append(getMessageType().name());
                sb.append(")");
                sb.append(":");
                sb.append(getMessage());
            }
            return sb.toString();
        }
    }

    /**
     * 消息发送模板类
     * 
     * @author kangye
     */
    private abstract class PushRemindMessage {

        public abstract void pushRemindMessage(Map<String, Object> bepushLoan, UnRepayLendVo unRepayLendVo);

        public abstract MessageType getMessageType();

        protected MessagePushResult messagePushResult;

        protected String getDefaultBankCard(Map<String, Object> bepushLoan) {

            if (null == bepushLoan)
                return null;

            Object bankCard = bepushLoan.get("bank_card");
            if (null == bankCard || StringUtils.isEmpty((String) bankCard)) {
                return null;
            }
            return (String) bankCard;
        }

        public MessagePushResult pushRemindMessageThread(final Map<String, Object> bepushLoan,
                final UnRepayLendVo unRepayLendVo) {

            messagePushResult = new MessagePushResult();
            messagePushResult.setBean(unRepayLendVo);
            messagePushResult.setMessageType(getMessageType());

            try {
                pushRemindMessage(bepushLoan, unRepayLendVo);
            } catch (Exception e) {
                messagePushResult.setCodeMessage(ResultCodeConstants.FAILED, e.getMessage());
            }

            logger.debug("当前状态是{}", messagePushResult);
            return messagePushResult;
        }
    }

    private class PushMq extends PushRemindMessage {

        public final static String OPNELINE_LOAN_REMIND_QUEEN = "openline_loan_remind_queen";
        public final static String WEIXIN_LOAN_REMIND_QUEEN = "weixin_loan_remind_queen";
        private Integer source = null;

        public PushMq(int source) {
            this.source = source;
        }

        @Override
        public MessageType getMessageType() {
            return MessageType.MQ;
        }

        @Override
        public void pushRemindMessage(Map<String, Object> bepushLoan, UnRepayLendVo unRepayLendVo) {

            // 重新封装一下消息,减少传输字节
            String dueDay = DateUtils.formateDate(dateLimit, DateUtils.FORMAT_2);
            Map<String, Object> messageConText = new HashMap<String, Object>();

            messageConText.put("dueDay", dueDay);
            messageConText.put("amount", String.valueOf(unRepayLendVo.getAmount()));
            messageConText.put("source", bepushLoan.get("source"));
            messageConText.put("apply_no", bepushLoan.get("id"));
            messageConText.put("user_id", bepushLoan.get("user_id"));
            messageConText.put("core_lend_request_id", String.valueOf(unRepayLendVo.getCoreLendRequestId()));

            logger.info("apply_no:{},消息发送到：{},消息是:{}", String.valueOf(bepushLoan.get("id")),
                    OPNELINE_LOAN_REMIND_QUEEN, JSON.toJSONString(messageConText));
            com.ishidai.dubbo.api.Result<String> result = sendMQService.sendQueue(getContent(),
                    JSON.toJSONString(messageConText));

            if (result.getCode() == OpStatus.FAILED) {
                logger.info("apply_no:{},MQ发送失败:{}", String.valueOf(bepushLoan.get("id")), result.getMessage());
                messagePushResult.setCodeMessage(ResultCodeConstants.FAILED, result.getMessage());
            } else {
                logger.info("apply_no:{},MQ发送成功", String.valueOf(bepushLoan.get("id")));
                messagePushResult.setCodeMessage(ResultCodeConstants.SUCCESS, "发送成功");
            }
        }

        private String getContent() {
            CommSourceEnum commSourceEnum = CommSourceEnum.getEnumByType(source);
            if (CommSourceEnum.WEIXIN.equals(commSourceEnum))
                return WEIXIN_LOAN_REMIND_QUEEN;
            else
                return OPNELINE_LOAN_REMIND_QUEEN;
        }

    }

    private class PushShortMessage extends PushRemindMessage {

        public final static String relay_short_message_template = "尊敬的用户您好，您的param0（期号）期借款项目还款日为param1，当期还款本息共计param2元，您可以登录账户，进入爱钱进·借啊页面，"
                + "点击立即还款按钮即可操作，如有任何问题可咨询爱钱进·借啊客服热线4008128018。";
        public final static String check_card_short_msg_template = "尊敬的用户您好，您的第param0期借款项目还款日为param1，当期应还本息共计param2元，"
                + "请在还款日18:00之前确保关联划扣卡param3中留有足够的余额，同时您也可以登录爱钱进·借啊进行还款操作，如有任何问题可咨询借啊客服热线4008128018；";

        @Override
        public MessageType getMessageType() {
            return MessageType.SHORT_MESSGE;
        }

        @Override
        public void pushRemindMessage(Map<String, Object> bepushLoan, UnRepayLendVo unRepayLendVo) {

            String dueDay = DateUtils.formateDate(dateLimit, DateUtils.FORMAT_2);
            String defaultBankCard = getDefaultBankCard(bepushLoan);
            String content = "";

            if (null == defaultBankCard) {
                content = ContextHandleUtils.replaceContentByParams(
                        relay_short_message_template,
                        new String[] { String.valueOf(unRepayLendVo.getPhase()), dueDay,
                                String.valueOf(unRepayLendVo.getAmount()) });
            } else {
                content = ContextHandleUtils.replaceContentByParams(check_card_short_msg_template, new String[] {
                        String.valueOf(unRepayLendVo.getPhase()), dueDay, String.valueOf(unRepayLendVo.getAmount()),
                        defaultBankCard });
            }

            UserMessage userMessage = new UserMessage();
            userMessage.setApplyNo(String.valueOf(bepushLoan.get("id")));
            userMessage.setMessage(content);
            userMessage.setMobile((String) bepushLoan.get("mobile"));
            userMessage.setStatus(ApiEnum.SmsTypeEnum.LOAN_REPAY.getType());

            logger.info("向{}推送还款提醒,{}", (String) bepushLoan.get("mobile"), content);
            Result<String> result = smsService.send(userMessage);
            if (result.getCode() == OpStatus.FAILED) {
                logger.info("还款提醒短信发送失败:{}", result.getMessage());
                messagePushResult.setCodeMessage(ResultCodeConstants.FAILED, result.getMessage());
            } else {
                logger.info("还款提醒短信发送成功");
                messagePushResult.setCodeMessage(ResultCodeConstants.SUCCESS, "发送成功");
            }
        }
    }

    private class PushAppNotice extends PushRemindMessage {

        public final static String check_card_mobile_msg_template = "尊敬的用户您好，您的第param0期借款项目还款日为param1，当期应还本息共计param2元，请在还款日18:00之前确保关联划扣卡param3中留有足够的余额，"
                + "同时您也可以登录爱钱进·借啊进行还款操作，如有任何问题可咨询借啊客服热线4008128018";

        @Override
        public MessageType getMessageType() {
            return MessageType.APP_NOTICE;
        }

        @Override
        public void pushRemindMessage(Map<String, Object> bepushLoan, UnRepayLendVo unRepayLendVo) {

            String dueDay = DateUtils.formateDate(dateLimit, DateUtils.FORMAT_2);
            String defaultBankCard = getDefaultBankCard(bepushLoan);
            String content = "";

            logger.info("向{}推送还款提醒消息", (Long) bepushLoan.get("user_id"));

            if (null == defaultBankCard) {
                appPushService.pushRepamentPersonalMessage((Long) bepushLoan.get("user_id"), unRepayLendVo.getAmount(),
                        dateLimit);
            } else {
                content = ContextHandleUtils.replaceContentByParams(check_card_mobile_msg_template, new String[] {
                        String.valueOf(unRepayLendVo.getPhase()), dueDay, String.valueOf(unRepayLendVo.getAmount()),
                        defaultBankCard });

                logger.debug("有默认划扣银行卡,推送消息内容是:{}", content);
                appPushService.pushPersonalMessage((Long) bepushLoan.get("user_id"), content,
                        MessageTypeEnum.REPAMENT_NOTICE.getType());
            }

            messagePushResult.setCodeMessage(ResultCodeConstants.SUCCESS, "发送成功");

        }
    }

    public boolean isInPushTime() {
        return true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

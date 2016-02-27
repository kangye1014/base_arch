package com.ishidai.ischedule.business.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ishidai.ischedule.business.appmsg.PushUtil;
import com.ishidai.ischedule.business.dao.MessageDao;
import com.ishidai.ischedule.business.domain.AppToken;
import com.ishidai.ischedule.business.domain.UserPersonalMessage;
import com.ishidai.ischedule.business.enums.CommAppSourceEnum;
import com.ishidai.ischedule.business.enums.CommonConstants;
import com.ishidai.ischedule.business.enums.MessageTypeEnum;

@Component
public class AppPushService {

    @Autowired
    private MessageDao messageDao;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static final Logger logger = LoggerFactory.getLogger(AppPushService.class);
    // 创建一个可根据需要创建新线程的线程池
    private final static ExecutorService pool = Executors.newCachedThreadPool();

    /**
     * 发送还款消息
     */
    public void pushRepamentPersonalMessage(long userid, BigDecimal amount, Date dateLimit) {
        pushRepamentPersonalMessage(userid, String.valueOf(amount), dateLimit);
    }

    private void pushRepamentPersonalMessage(Long userId, String repamentMoney, Date repamentDate) {
        String content = "亲爱的用户，您在爱钱进·借啊的贷款本期需还款[repamentMoney]元，还款日为：[repamentDate]，请您及时还款，防止逾期，避免影响您的信用记录。如您已经还款，请忽略本条消息。感谢您对爱钱进·借啊的支持。";
        DateFormat dateFormat = new SimpleDateFormat("MM月dd日");
        content = content.replace("[repamentMoney]", repamentMoney);
        content = content.replace("[repamentDate]", dateFormat.format(repamentDate));
        logger.debug("推送消息是:{}", content);
        pushPersonalMessage(userId, content, MessageTypeEnum.REPAMENT_NOTICE.getType());
    }

    /**
     * 推送个人消息
     * 
     * @param userId
     * @param content
     */
    public void pushPersonalMessage(Long userId, String content, int type) {
        // 保存消息到个人消息列表
        try {
            UserPersonalMessage personalMessage = savePersonalMessage(content, userId, type);
            List<AppToken> appTokenList = getDeviceTokenByUserId(userId);
            pool.execute(new MessageRunner(personalMessage, appTokenList));
        } catch (Exception e) {
            logger.error("推送消息异常{}", e.getMessage());
        }

    }

    class MessageRunner implements Runnable {

        private final UserPersonalMessage personalMessage;

        List<AppToken> listAppUserToken;

        public MessageRunner(UserPersonalMessage personalMessage, List<AppToken> listAppUserToken) {
            this.personalMessage = personalMessage;
            this.listAppUserToken = listAppUserToken;
        }

        @Override
        public void run() {
            pushMessageToUser(listAppUserToken, personalMessage);
        }

    }

    private void pushMessageToUser(List<AppToken> listAppUserToken, UserPersonalMessage personalMessage) {

        Map<String, String> pushModels = new HashMap<String, String>();

        List<String> aliasIOSList = new ArrayList<String>();
        List<String> aliasAndroidList = new ArrayList<String>();
        if (null != listAppUserToken && listAppUserToken.size() > 0) {
            for (AppToken token : listAppUserToken) {
                // TODO 以后用常量
                if (CommAppSourceEnum.IOS.getType() == token.getType()) {
                    aliasIOSList.add(token.getDeviceToken());
                } else if (CommAppSourceEnum.ANDROID.getType() == token.getType()) {
                    aliasAndroidList.add(token.getDeviceToken());
                }
            }
        }

        // 组装发送的参数
        pushModels.put("id", String.valueOf(personalMessage.getId()));
        pushModels.put("content", personalMessage.getMessage());
        pushModels.put("createTime", dateFormat.format(personalMessage.getCreateTime()));
        pushModels.put("isDelete", String.valueOf(personalMessage.getIsDelete()));
        pushModels.put("isRead", String.valueOf(personalMessage.getIsRead()));
        pushModels.put("type", String.valueOf(personalMessage.getType()));
        String alertTitle = PushUtil.formatTitle(personalMessage.getMessage());
        // TODO 测试 删掉
        // aliasIOSList.add("dd8c3b84f8ae2f17b0344c7a290be17a92a5bdb5281c35c5e40aac9b2e87c5c0");
        if (aliasIOSList.size() > 0) {
            PushUtil.sendMsg2IOS(aliasIOSList, alertTitle, 1, pushModels, true);
            logger.info("已经推送！");
        }
        if (aliasAndroidList.size() > 0) {
            PushUtil.sendMsg2Android(aliasAndroidList, alertTitle, pushModels, false);
            logger.info("已经推送！");
        }
    }

    public UserPersonalMessage savePersonalMessage(String message, Long userId, int type) {
        UserPersonalMessage personalMessage = new UserPersonalMessage();
        personalMessage.setMessage(message);
        personalMessage.setIsDelete(CommonConstants.NO);
        personalMessage.setIsRead(CommonConstants.NO);
        personalMessage.setUserId(userId);
        personalMessage.setCreateTime(Calendar.getInstance().getTime());
        personalMessage.setType(type);
        savePersonalMessage(personalMessage);
        return personalMessage;
    }

    public int savePersonalMessage(UserPersonalMessage personalMessage) {
        return messageDao.savePersonalMessage(personalMessage);
    }

    public List<AppToken> getDeviceTokenByUserId(Long userId) {
        return messageDao.getDeviceTokenByUserId(userId);
    }
}

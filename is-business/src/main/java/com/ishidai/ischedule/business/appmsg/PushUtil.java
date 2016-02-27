package com.ishidai.ischedule.business.appmsg;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javapns.Push;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.PushedNotifications;
import javapns.notification.ResponsePacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;

import com.alibaba.fastjson.JSONObject;
import com.ishidai.ischedule.business.domain.AppToken;
import com.ishidai.ischedule.business.utils.BoException;

/**
 * App端消息推送
 * 
 * @author liuhongyu
 */
public class PushUtil extends Thread {

    private static Logger logger = LoggerFactory.getLogger(PushUtil.class);
    private static Properties prop = null;
    // main
    private static String TITLE = null;
    // android
    private static String ANDROID_APP_KEY_USER = null;
    private static String ANDROID_MASTER_SECRET_USER = null;
    // ios
    private static boolean IOS_CERTIFICATE_ENVIRONMENT = false;
    private static String IOS_CERTIFICATE_PATH_USER = null;
    private static String IOS_CERTIFICATE_PASSWORD = null;
    private static String IOS_CERTIFICATE_PATH = null;
    private int clientType;
    private String clias;

    PushUtil() {
    }

    PushUtil(String clias, int clientType) {
        this.clias = clias;
        this.clientType = clientType;
    }

    static {
        try {
            prop = new Properties();
            InputStream in = PushUtil.class.getResourceAsStream("/push.properties");
            prop.load(in);

            // main
            TITLE = prop.getProperty("push.title");
            // android
            ANDROID_APP_KEY_USER = prop.getProperty("push.androiduser.appkey");
            ANDROID_MASTER_SECRET_USER = prop.getProperty("push.androiduseruser.mastersecret");
            logger.info("Android推送信息：key：" + ANDROID_APP_KEY_USER + "   secret：" + ANDROID_MASTER_SECRET_USER);
            // IOS
            IOS_CERTIFICATE_ENVIRONMENT = Boolean.parseBoolean(prop.getProperty("push.ios.certificate.environment"));
            IOS_CERTIFICATE_PATH_USER = prop.getProperty("push.iosuser.certificate.path");
            IOS_CERTIFICATE_PASSWORD = prop.getProperty("push.ios.certificate.password");
            IOS_CERTIFICATE_PATH = prop.getProperty("push.iosuser.certificate.path");
            logger.info("IOS推送信息：开发模式：" + IOS_CERTIFICATE_ENVIRONMENT + "   证书路径：" + IOS_CERTIFICATE_PATH);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {

        }
    }

    /**
     * 向指定别名的IOS设备推送消息
     * 
     * @param tokens
     *            推送设备tokens列表
     * @param alert
     *            显示在ios通知的提醒信息
     * @param badge
     *            显示消息个数
     * @param dictionarys
     *            推送给设备的额外业务信息
     * @param isSingleThread
     *            推送类型 true：单线程推送，false：多线程推送
     */
    public static void sendMsg2IOS(List<String> tokens, String alert, int badge, Map<String, String> dictionarys,
            boolean isSingleThread) {

        PushNotificationManager pushManager = null;
        try {

            List<Device> devices = new ArrayList<Device>();
            // 封装divice
            for (String token : tokens) {
                devices.add(new BasicDevice(token));
            }

            // 封装 payLoad 对象
            PushNotificationPayload payLoad = new PushNotificationPayload(alert, badge, "default");

            dictionarys.put("content", "");// 这里赋值为空，方式内容过多，发送失败，ios客户端需再次请求获取内容
            for (Map.Entry<String, String> m : dictionarys.entrySet()) {
                payLoad.addCustomDictionary(m.getKey(), m.getValue());
            }
            // 判断证书路径是否为空
            if (IOS_CERTIFICATE_PATH == null || "".equals(IOS_CERTIFICATE_PATH)) {
                throw new BoException("ios推送证书的路径为空");
            } else {
                File certificateFile = new File(IOS_CERTIFICATE_PATH);
                if (!certificateFile.exists()) {
                    throw new BoException("ios推送证书的路径错误");
                }
            }
            logger.info("ios推送证书的路径:{}", IOS_CERTIFICATE_PATH);
            if (isSingleThread) {
                // 2.获取连接
                pushManager = new PushNotificationManager();
                pushManager.initializeConnection(new AppleNotificationServerBasicImpl(IOS_CERTIFICATE_PATH,
                        IOS_CERTIFICATE_PASSWORD, IOS_CERTIFICATE_ENVIRONMENT));
                // 3.推送消息
                PushedNotifications pushed = pushManager.sendNotifications(payLoad, devices);

                List<PushedNotification> success = PushedNotification.findSuccessfulNotifications(pushed);
                List<PushedNotification> failed = PushedNotification.findFailedNotifications(pushed);

                // 打印推送状态
                logger.info("IOS_CERTIFICATE_PATH=" + IOS_CERTIFICATE_PATH + ",IOS_CERTIFICATE_PASSWORD="
                        + IOS_CERTIFICATE_PASSWORD + ",IOS_CERTIFICATE_ENVIRONMENT=" + IOS_CERTIFICATE_ENVIRONMENT);
                logger.info("推送[ios]消息.要推送总数=" + tokens.size() + ",成功=" + success.size() + ",失败=" + failed.size());

                for (PushedNotification notification : failed) {
                    String invalidToken = notification.getDevice().getToken();
                    Exception theProblem = notification.getException();
                    theProblem.printStackTrace();
                    logger.error("ios推送错误消息：" + theProblem.getMessage());
                    ResponsePacket theErrorResponse = notification.getResponse();
                    if (theErrorResponse != null) {
                        logger.error("ios推送错误回应：" + theErrorResponse.getMessage());
                    }
                }
            } else {
                int threads = 10;
                /*
                 * Start threads, wait for them, and get a list of all pushed
                 * notifications
                 */
                List<PushedNotification> notifications = Push.payload(payLoad, IOS_CERTIFICATE_PATH,
                        IOS_CERTIFICATE_PASSWORD, IOS_CERTIFICATE_ENVIRONMENT, threads, devices);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("推送[ios]消息出错.{}", e);
        } finally {
            try {
                if (pushManager != null) {
                    pushManager.stopConnection();
                }
            } catch (Exception e) {
                logger.error("推送[ios]消息,关闭PushNotificationManager对象出错.{}", e);
            }
        }
    }

    /**
     * 向指定别名的android设备推送消息
     * 
     * @param aliases
     *            推送设备别名列表
     * @param alert
     *            标题
     * @param extras
     *            推送给设备的额外业务信息
     * @param isSendAll
     *            true:发送全部，false：按指定的别名发送
     */
    public static void sendMsg2Android(List<String> aliases, String alert, Map<String, String> extras, boolean isSendAll) {
        Builder b = PushPayload.newBuilder();// buildPushObject_ios_audienceMore_messageWithExtras().newBuilder();//PushPayload.newBuilder();
        // 设置发送平台为android
        b.setPlatform(Platform.android());
        // 判断是否群发
        if (isSendAll) {
            logger.info("推送[android]消息到[所有]用户");
            b.setAudience(Audience.all());
        } else {
            logger.info("推送[android]消息到[{}]用户", aliases.size());
            b.setAudience(Audience.alias(aliases));
        }
        b.setMessage(Message.content(String.valueOf(JSONObject.toJSON(extras))));
        PushPayload payLoad = b.build();
        androidPush(payLoad);
    }

    /**
     * 向所有android设备推送通知
     * 
     * @param alert
     *            显示在android通知的提醒信息
     * @param extras
     *            推送给设备的额外业务信息
     */
    public static void sendMsg2Android(String alert, Map<String, String> extras) {
        sendMsg2Android(null, alert, extras, false);
    }

    /**
     * 向android设备发送消息
     * 
     * @param payload
     */
    private static void androidPush(PushPayload payload) {
        JPushClient jpushClient = null;
        jpushClient = new JPushClient(ANDROID_MASTER_SECRET_USER, ANDROID_APP_KEY_USER, 3);
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("发送结果：" + result);
            System.out.println("发送结果：" + result);
        } catch (APIConnectionException e) {
            logger.error("发送失败. 稍后再试. ", e);
        } catch (APIRequestException e) {
            logger.error("JPush服务端响应异常.", e);
            logger.info("HTTP状态: " + e.getStatus());
            logger.info("错误代码: " + e.getErrorCode());
            logger.info("错误消息: " + e.getErrorMessage());
        }
    }

    public static void push(AppToken appCT) {
        PushUtil p = new PushUtil(appCT.getDeviceToken(), appCT.getType());
        p.start();

    }

    public void run() {
        List<String> tokens = new ArrayList<String>();
        tokens.add(clias);
        int IOS = 2;// AppLatitudeCache.getIntValue("AppUserToken.clientType.Iphone");
        int Android = 3;// AppLatitudeCache.getIntValue("AppUserToken.clientType.Android");

        Map<String, String> extras = new HashMap<String, String>();
        extras.put("id", "902");
        extras.put("crateTime", "2015-12-12");
        extras.put("content", "hello world");
        extras.put("type", "1");
        // PushUtil.sendMsg2Android(tokens,"这里是消息内容", extras);
        PushUtil.sendMsg2IOS(tokens, "11111", 1, extras, true);
    }

    public static String formatTitle(String content) {
        if (content.length() > 20) {
            content = content.substring(0, 20) + "......";
        }
        return content;
    }

    public static void main(String[] args) throws Exception {
        AppToken appCustomerToken = new AppToken();
        // IOS
        appCustomerToken.setDeviceToken("dd8c3b84f8ae2f17b0344c7a290be17a92a5bdb5281c35c5e40aac9b2e87c5c0");
        appCustomerToken.setType(2);
        push(appCustomerToken);

    }
}

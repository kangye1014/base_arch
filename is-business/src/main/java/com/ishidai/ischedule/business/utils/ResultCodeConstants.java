package com.ishidai.ischedule.business.utils;

/**
 * result code，message错误码
 */
public class ResultCodeConstants {
    /**
     * 定义规范 code定义规范：以CODE开头，字段之间以_隔开。code 目前只可定义正整数，不可定义负数 且code值不可重复。
     * code目前分为参数，银行卡，用户，标，标的申请 等几个大类，定义时需要在大类下定义，未找到相应的大类，需要新添加大类。
     * code常量修饰词：public static final int message定义规范：以MSG开头，字段之间以_隔开。
     * message常量修饰词：public static final String
     */

    /*
     * ---------------------返回code定义----------------------------------
     */

    /**
     * code:1表示成功
     */
    public static final int SUCCESS = 1;
    /**
     * code:-1表示失败
     */
    public static final int FAILED = -1;
    /**
     * code:-999表示提示客户再次登陆，临时应用
     */
    public static final int FAILED_TIP = -999;
    /**
     * code:0表示信息不存在
     */
    public static final int NOTEXITS = 0;

    /**
     * code:负值或者(2-99)表示已经定义的问题(不可再添加)
     */
    public static final int LOGIN_NICKNAME_PASSWORD = -2; // 登陆用户名密码异常
    public static final int LOGIN_SPECIAL_USER = -3; // 特殊用户，不许登录
    public static final int LOGIN_PERMISSION = -4; // 没有登录权限
    public static final long VALIDATE_EXIST = -5; // 码表中已经存在相关的code
    public static final int LOGIN_MOBILE_VERIFIED = -6; // 此用户手机未绑定激活
    public static final int LOGIN_EMAIL_VERIFIED = -7; // 此用户邮箱未绑定激活
    public static final int LOGIN_PASSWORD = -8; // 登陆密码异常
    public static final int LOGIN_NICKNAME = -9; // 登陆用户名异常
    public static final int LOGIN_MOBILE = -10; // 登陆手机号码异常
    public static final int LOGIN_EMAIL = -11; // 登陆邮箱异常
    public static final int LOGIN_OTHER = -12;// 其他渠道用户不能登录
    public static final int CODE_TAOBAO_EXCEPTION = -14; // 淘宝异常
    public static final int MONEY_INSUFFICIENT = -15; // 用户余额不足
    public static final int MOBILE_PASSWORD_SIMPLE = -40; // 密码简单
    public static final int NO_SESSION = -41; // session失效
    public static final int GET_CODE_FAIL = -42; // 获取验证码失败
    public static final int VALIDATE_AUTHCODE_FAIL = -43; // 图片验证码错误
    public static final int COOKIE_OUTDATE = -44; // cookie过时
    public static final int COOKIE_ERROR_STEP = -45; // cookie错误操作
    public static final int GET_PHONECODE_FAIL = -46; // 获取手机验证码失败
    public static final int VALIDATE_PHONECODE_FAIL = -47; // 短信验证码错误
    public static final int VERIFY_LOGIN_FAIL = -48; // 用户名或者密码错误
    public static final int PHONE_NOT_SUPPORT = -49; // 不支持手机号码
    public static final int LOGIN_FAIL_CODE = -50; // 登录失败
    public static final int NOT_EXIST_INFO = -51; // 信息不存在
    public static final int NOT_SUPPORT_EAIL = -52; // 不支持邮箱登录
    public static final int TAOBAOLOGIN_EAIL = -53; // 淘宝登录失败
    public static final int NOT_BINDING = -54; // 未绑定手机号
    public static final int NEED_SMS_CODE = -55; // 需要短信验证码
    public static final int IS_PROTECTED = -56; // 淘宝账户已被保护
    public static final int GET_BIIL_FAIL = -57; // 获取手机账单失败
    public static final int TAOBAO_ALLPAY_NOT_BIND = -58; // 淘宝账号与支付宝账号未绑定
    public static final int REPEAT_VERIFY = -88; // 重复验证
    public static final int ERROR_PHONE_CODE = -99; // 手机验证码错误
    public static final int NO_LOGIN = -100; // 未登录
    public static final int UN_NEW_INVESTOR = -111; // 非新手
    public static final int LAPSED = -121; // 手机验证码失效
    public static final int NEED_AUTHCODE = 2; // 需要短图片验证码
    public static final int NEED_PHONECODE = 3; // 需要短信验证码
    public static final int NEED_PHONECODE_AUTHCODE = 5; // 需要短信验证码和图片验证码
    public static final int NEED_PHONECODE_REFRESH_AUTHCODE = 6; // 需要短信验证码和图片验证码,并要刷新图片验证码
    public static final int OVER_MAX_VERIFY_TIME_CODE = -5; // 超过验证次数
    public static final int NEED_WEB_PASSWORD = 130; // 需要网站密码
    public static final int NEED_WEB_SERVICE_PASSWORD = 131; // 需要网站密码和服务密码
    public static final int SIMPLE_PASSWORD = 132; // 密码简单或为初始密码，请重置后登录
    public static final int THIRTYDAYS_LIMIT = -30;
    public static final int KNUSERID_EXIST = -31;// knUserid已存在
    /**
     * code:100-199表示参数的问题
     */
    public static final int CODE_PARAM_ILLEGAL = 100; // 参数不合法
    public static final int CODE_JSON_PARSE = 101; // json解析异常
    public static final int CODE_DATA_INFO_ERROR = 102;// 数据中心获取数据不正确
    public static final int CODE_MOBILE_CODE_ERROR = 103;// 手机验证码不正确
    public static final int CODE_MOBILE_CODE_OVER = 104; // 手机验证码超时
    public static final int CODE_MOBILE_CODE_NO = 105; // 手机未发送手机验证码
    public static final int CODE_INFO_NOT_EXISTS = 106; // 信息不存在

    /**
     * code:200-299表示银行卡的问题
     */
    public static final int CODE_BANKCARD_IS_EXISTS = 200; // 该银行卡已存在
    public static final int CODE_BANKCARD_NOT_SUPPORT = 201; // 不支持该银行卡

    /**
     * code:300-399表示用户的问题
     */
    public static final int CODE_USER_NOT_EXISTS = 300; // 用户信息不存在
    public static final int CODE_USER_INFO_EXISTS = 313;// 用户信息已存在
    public static final int LOGIN_OR_LOAN_PERMISSION = 301; // 用户没有借款权限
    public static final int CODE_USER_IDCARD_VERIFIED_NO = 302; // 用户没有实名认证
    public static final int CODE_USER_EMAIL_VERIFIED_NO = 303; // 用户没有邮箱认证
    public static final int CODE_USER_MOBILE_VERIFIED_NO = 304; // 用户没有手机认证
    public static final int CODE_USER_TAOBAO_VERIFIED_NO = 305; // 用户没有淘宝认证
    public static final int CODE_USER_MOBILE_NOT_INFO = 306; // 用户手机信息不存在
    public static final int CODE_USER_TAOBAO_NOT_INFO = 307; // 用户淘宝信息不存在
    public static final int CODE_USER_CREDIT_CARD_NOT_INFO = 308; // 用户信用卡信息不存在
    public static final int CODE_USER_OTHER_LOGIN = 399; // 用户在其他地方登录
    public static final int CODE_USER_ZHAOSHANG_DETAIL_NO = 309; // 用户有招商银行信用卡且没有信用卡详单
    public static final int CODE_USER_MOBILE_REGISTER_YES = 310; // 用户该手机已注册
    public static final int CODE_USER_NAME_REGISTER_YES = 311; // 用户该昵称已注册
    public static final int CODE_USER_IDNO_HAS_VERIFYED = 312; // 用户该身份信息已使用
    public static final int CODE_USER_EMAIL_REGISTER_YES = 313;// 该邮箱已被注册;
    public static final int CODE_USER_USERNAME_REGISTER_YES = 314;// 该用户名已被注册;
    public static final int CODE_BANKCARD_CHECK_YES = 315;// 该用户银行卡已鉴权;
    public static final int CODE_BANKCARD_CHECK_NO = 316;// 该用户银行卡未鉴权;
    public static final int CODE_AGREE_DEDUCT_YES = 317;// 该用户已签署划扣协议;
    public static final int CODE_AGREE_DEDUCT_NO = 318;// 该用户未签署划扣协议;

    /**
     * code:400-499表示标的问题
     */
    public static final int CODE_LOAN_NOT_EXISTS = 400; // 标不存在
    public static final int CODE_LOAN_REPAY = 401; // 标的处于借款中或者还款中（不可以借款）
    public static final int CODE_LOAN_HAS_AUDITED = 402; // 标的已审核
    public static final int CODE_LOAN_APPLY_RISK_NOT_INFO = 403; // 未从数据中心获取到风控信息
    public static final int CODE_LOAN_HAS_AUDITED_NO = 404; // 标的未处于人工审核通过状态
    public static final int CODE_LOAN_EXISTS = 405; // 标已存在
    public static final int CANCEL_LOAN_FAIL = 406; // 撤标失败
    public static final int CODE_LOAN_IS_OVERDUE = 407; // 标逾期

    /**
     * code:500-599表示申请标的问题
     */
    public static final int CODE_APPLY_NOT_EXISTS = 500; // 标的申请信息不存在
    public static final int CODE_LOAN_RISK_NOT_EXISTS = 501; // 标的决策引擎信息不存在
    public static final int CODE_APPLY_RISK_INFO_FALSE = 502; // 无决策引擎数据
    public static final int CODE_APPLY_JUMP_TO_ALLVERIFY = 503; // 跳转到汇总页
    public static final int CODE_APPLY_JUMP_TO_USERCENTER = 504;// 跳转到用户中心
    public static final int CODE_APPLY_CITY_NOT_SUPPORT = 505; // 不支持范围内的城市
    public static final int CODE_APPLY_POSITION_NO = 506; // 定位不成功
    public static final int CODE_APPLY_IDNO_IN_BLACK = 507; // 身份证存在黑名单中
    public static final int CODE_APPLY_IDNO_VERFIED = 508; // 身份证已验证
    public static final int CODE_APPLY_PROGESS_PAGE1_NO = 511; // 用户申请不处于用户开启爱时贷时定位
    public static final int CODE_APPLY_PROGESS_PAGE2_NO = 512; // 用户申请不处于借款用途页:定位
    public static final int CODE_APPLY_PROGESS_PAGE3_NO = 513; // 用户申请不处于邮箱账单页:定位
    public static final int CODE_APPLY_PROGESS_PAGE4_NO = 514; // 用户申请不处于邮箱账单等待页
    public static final int CODE_APPLY_PROGESS_PAGE5_NO = 515; // 用户申请不处于借款金额评估页:定位
    public static final int CODE_APPLY_PROGESS_PAGE6_NO = 516; // 用户申请不处于ID5验证完成后定位
    public static final int CODE_APPLY_PROGESS_PAGE7_NO = 517; // 用户申请不处于实名认证等待页
    public static final int CODE_APPLY_PROGESS_PAGE8_NO = 518; // 用户申请不处于淘宝抓取完成后定位
    public static final int CODE_APPLY_PROGESS_PAGE9_NO = 519; // 用户申请不处于电商信息等待页
    public static final int CODE_APPLY_PROGESS_PAGE10_NO = 520; // 用户申请不处于手机抓取完成后定位
    public static final int CODE_APPLY_PROGESS_PAGE11_NO = 521; // 用户申请不处于手机号码等待页
    public static final int CODE_APPLY_PROGESS_PAGE12_NO = 522; // 用户申请不处于银行卡填写完成后定位
    public static final int CODE_APPLY_PROGESS_PAGE13_NO = 523; // 用户申请不处于银行卡等待页
    public static final int CODE_APPLY_PROGESS_PAGE14_NO = 524; // 用户申请不处于注册爱时贷用户完成时候定位

    /**
     * code:600-699表示登录的问题
     */
    public static final int CODE_USER_USERNAME_PASSWD_ERROR = 601;// 用户登陆用户名密码异常
    public static final int CODE_USER_NOT_LOGIN = 602; // 用户没有登录权限
    public static final int CODE_USER_LOCKED = 603; // 用户已锁定

    /**
     * code:700-799表示淘宝、手机、信用卡的问题 700-729：淘宝 730-759：手机 760-790：信用卡 791-199:公用
     */
    // 700-729：淘宝
    public static final int CODE_TAOBAO_NEED_CODE_EXCEPTION = 701; // 淘宝是否需要验证码异常
                                                                   // 重新获取
    public static final int CODE_TAOBAO_OVER_MAX_VERIFY_TIME = 702;// 超过最大验证次数
    public static final int CODE_TAOBAO_SEND_SMS_EXCEPTION = 704; // 淘宝发送短信异常
    public static final int CODE_TAOBAO_SEND_SMS_OFTEN = 705; // 频繁发送淘宝验证码

    // 730-759：手机
    public static final int CODE_MOBILE_IS_ILLEGAL = 731; // 手机号码验证不通过
    public static final int CODE_MOBILE_AUTH_CODE_ERROR = 732; // 手机图片验证码失败
    public static final int CODE_MOBILE_SEND_SMS_ERROR = 733; // 发送短信失败
    public static final int CODE_MOBILE_PASSWD_ERROR = 734; // 服务密码错误
    public static final int CODE_MOBILE_CHECKPHONE_ERROR = 735; // 检验手机是否需要验证码错误，重新请求
    public static final int CODE_MOBILE_AUTHCODE_ERROR = 736; // 获取图片验证码错误，重新请求
    public static final int CODE_MOBILE_SCAUTHCODE_ERROR = 737; // 获取二次图片验证码错误，重新请求
    public static final int CODE_MOBILE_RANDOMSMS_ERROR = 738; // 获取短信验证码错误，重新请求
    public static final int CODE_MOBILE_VARANDOMSMS_ERROR = 739;// 验证短信验证码错误，请重新验证一次

    // 760-790：信用卡
    public static final int CODE_CREDITCARD_NAME_PASSWD_FAULT = 761; // 信用卡邮箱账号或者密码错误
    public static final int CODE_CREDITCARD_EMAIL_NO_SUPPORT = 762; // 信用卡邮箱不支持

    // 791-199:公用
    public static final int CODE_NO_SESSION = 791; // 没有 session
    public static final int CODE_DATA_EXCEPTION = 792; // 数据中心接口异常
    //

    /**
     * code:800-899 标示卡牛提示信用卡相关提示
     */
    public static final int CODE_KN_NET_FAIL = 801;// 很抱歉，由于当前网络不稳定，请稍后再试。谢谢～
    public static final int CODE_KN_PEOPLE_OUT = 802;// 很抱歉，申请人数过多，网络不稳定，请注销卡牛账号重新登录后再试。谢谢～
    public static final int CODE_KN_BILL_LITTLE = 803;// 很抱歉，您信用卡账单过少，无法评估额度，请通过网银或邮箱导入更多的账单后再尝试。谢谢～
    public static final int CODE_KN_COUNT_FAIL = 804;// 很抱歉，数据计算失败，请重新操作。谢谢～
    /*
     * ---------------------------返回message定义----------------------------------
     */

    public static final String MSG_SUCCESS = "操作成功";

    public static final String MSG_FAILED = "操作失败";

    public static final String MSG_TIME_OUT = "时间过期";

    public static final String MSG_SESSION_OUT = "session过期";

    public static final String MSG_SIGN_ERROR = "签名失败";

    public static final String MSG_PARAM_ILLEGAL = "参数不合法";

    public static final String MSG_LOAN_AUDITED = "标的未处于待审核状态";

    public static final String MSG_USER_NOT_EXISTS = "用户不存在";

    public static final String MSG_USER_IS_EXISTS = "用户已存在";

    public static final String MSG_USER_ACCOUNT_NOT = "用户账户不存在";

    public static final String MSG_REQUEST_FAILURE = "请求失败";

    public static final String MSG_REQUEST_VERIFYIDNO_FAILURE = "认证身份证失败";

    public static final String MSG_INFO_FAILURE = "信息错误";

    public static final String MSG_LOGIN_OR_LOAN_PERMISSION = "没有登录或者借款权限";

    public static final String MSG_LOAN_REPAY = "标的不存在、标的处于借款中或者还款中，不可以借款";

    public static final String MSG_COOKIE_OUTDATE = "注册信息cookie过时";

    public static final String MSG_COOKIE_ERROR_STEP = "注册步骤出错";

    public static final String MSG_NOT_EXISTS = "信息不存在";

    public static final String MSG_BANKCARD_IS_EXISTS = "银行卡号已存在";

    public static final String MSG_LOAN_NOT_EXISTS = "标的信息不存在";
    public static final String MSG_LOAN_EXISTS = "标的信息已存在";

    public static final String MSG_BANKCARD_NOT_SUPPORT = "不支持该银行卡";

    public static final String MSG_APPLY_NOT_EXISTS = "标的申请信息不存在";

    public static final String MSG_USER_IDCARD_VERIFIED_NO = "用户没有实名认证";

    public static final String MSG_USER_EMAIL_VERIFIED_NO = "用户没有邮箱认证";

    public static final String MSG_USER_MOBILE_VERIFIED_NO = "用户没有手机认证";

    public static final String MSG_USER_TAOBAO_VERIFIED_NO = "用户没有淘宝认证";

    public static final String MSG_LOAN_RISK_NOT_EXISTS = "标的决策引擎信息不存在";

    public static final String MSG_LOAN_HAS_AUDITED = "标的已审核";

    public static final String MSG_APPLY_RISK_INFO_FALSE = "无决策引擎数据";

    public static final String MSG_USER_MOBILE_NOT_INFO = "用户手机信息不存在";

    public static final String MSG_USER_TAOBAO_NOT_INFO = "用户淘宝信息不存在";

    public static final String MSG_LOAN_APPLY_RISK_NOT_INFO = "未从数据中心获取到风控信息";

    public static final String MSG_USER_CREDIT_CARD_NOT_INFO = "用户信用卡信息不存在";

    public static final String MSG_USER_ZHAOSHANG_DETAIL_NO = "用户有招商银行信用卡且没有信用卡详单";

    public static final String MSG_JSON_PARSE = "json解析异常";

    public static final String MSG_DATA_INFO_ERROR = "数据中心获取数据不正确";

    public static final String MSG_APPLY_JUMP_TO_ALLVERIFY = "跳转到汇总页";

    public static final String MSG_APPLY_JUMP_TO_USERCENTER = "跳转到用户中心页";

    public static final String MSG_LOAN_HAS_AUDITED_NO = "标的未处于人工审核通过状态";

    public static final String MSG_LOAN_HAS_CONFIRMAPPLY_NO = "标的未处于确认申请状态";

    public static final String MSG_USER_MOBILE_REGISTER_YES = "用户该手机已注册";

    public static final String MSG_USER_MOBILE_NO_REGISTER = "此手机号码未注册";

    public static final String MSG_MOBILE_CODE_ERROR = "手机验证码不正确";

    public static final String MSG_MOBILE_CODE_OVER = "手机验证码超时";

    public static final String MSG_USER_USERNAME_PASSWD_ERROR = "用户名或者密码错误";

    public static final String MSG_USER_NOT_LOGIN = "用户没有登录权限";

    public static final String MSG_USER_LOCKED = "账号密码错误次数过多，账号被锁，请重置密码";

    public static final String MSG_APPLY_CITY_NOT_SUPPORT = "对不起，您所在城市尚未开通爱钱进·借啊服务。更多城市即将开放，敬请期待";

    public static final String MSG_APPLY_POSITION_NO = "定位不成功";

    public static final String MSG_MOBILE_CODE_NO = "手机未发送手机验证码";

    public static final String MSG_USER_NAME_REGISTER_YES = "用户该昵称已注册";

    public static final String MSG_USER_EMAIL_REGISTER_YES = "该邮箱已被注册";

    public static final String MSG_APPLY_IDNO_IN_BLACK = "身份证存在黑名单中";

    public static final String MSG_APPLY_BANK_CARD_NO_SUPPORT = "不支持该银行卡";

    public static final String MSG_APPLY_PROGESS_PAGE1_NO = "用户未完成开启爱时贷时定位";
    public static final String MSG_APPLY_PROGESS_PAGE2_NO = "用户未完成借款用途页";
    public static final String MSG_APPLY_PROGESS_PAGE3_NO = "用户未完成邮箱账单页";
    public static final String MSG_APPLY_PROGESS_PAGE4_NO = "用户未完成邮箱账单等待页";
    public static final String MSG_APPLY_PROGESS_PAGE5_NO = "用户未完成借款金额评估页";
    public static final String MSG_APPLY_PROGESS_PAGE6_NO = "用户未完成ID5验证";
    public static final String MSG_APPLY_PROGESS_PAGE7_NO = "用户未完成实名认证等待页";
    public static final String MSG_APPLY_PROGESS_PAGE8_NO = "用户未完成淘宝抓取完成后定位";
    public static final String MSG_APPLY_PROGESS_PAGE9_NO = "用户未完成电商信息等待页";
    public static final String MSG_APPLY_PROGESS_PAGE10_NO = "用户未完成手机抓取完成后定位";
    public static final String MSG_APPLY_PROGESS_PAGE11_NO = "用户未完成手机号码等待页";
    public static final String MSG_APPLY_PROGESS_PAGE12_NO = "用户未完成银行卡填写完成后定位";
    public static final String MSG_APPLY_PROGESS_PAGE13_NO = "用户未完成银行卡等待页";
    public static final String MSG_APPLY_PROGESS_PAGE14_NO = "用户未完成注册爱时贷";

    public static final String MSG_TAOBAO_NEED_CODE_EXCEPTION = "验证码异常，请重新获取"; // 淘宝是否需要验证码异常

    public static final String MSG_TAOBAO_SEND_SMS_EXCEPTION = "发送短信异常，请重新发送"; // 淘宝发送短信异常

    public static final String MSG_TAOBAO_SEND_SMS_OFTEN = "15分钟之后再次尝试"; // 淘宝发送短信异常

    public static final String MSG_TAOBAO_OVER_MAX_VERIFY_TIME = "您的淘宝账号已连续输入错误5次，请3天后再试";

    public static final String MSG_IDNO_NO_EXIST = "用户身份信息不存在";

    public static final String MSG_TAOBAO_USER_TRY_TWO = "亲，请你再试一遍";

    public static final String MSG_CREDITCARD_NAME_PASSWD_FAULT = "信用卡邮箱账号或者密码错误";

    public static final String MSG_FAILED_INFO_ONE = "数据中心未抓取、解析完毕";

    public static final String MSG_NEED_WEBPASSWORD = "需要网站密码";

    public static final String MSG_NEED_SERVICE_WEB_PASS = "需要网站服务密码";

    public static final String MSG_SIMPLE_PASSWORD = "密码简单或为初始密码，请重置后登录";

    public static final String MSG_CREDITCARD_EMAIL_NO_SUPPORT = "信用卡邮箱不支持";

    public static final String MSG_MOBILE_NO_SUPPORT = "手机号码不支持";

    public static final String MSG_DATA_COMM_INFO = "亲，请您再试一遍呗";

    public static final String MSG_MOBILE_AUTH_CODE_ERROR = "手机图片验证码失败";

    public static final String MSG_MOBILE_CHECKPHONE_ERROR = "检验手机是否需要验证码错误，请重新获取";

    public static final String MSG_MOBILE_AUTHCODE_ERROR = "获取图片验证码错误，请重新请求";

    public static final String MSG_MOBILE_SCAUTHCODE_ERROR = "获取二次图片验证码错误，请重新请求";

    public static final String MSG_MOBILE_RANDOMSMS_ERROR = "获取短信验证码错误，请重新请求";

    public static final String MSG_MOBILE_VARANDOMSMS_ERROR = "验证短信验证码错误，请重新验证一次";

    public static final String MSG_MOBILE_ERROR_COUNTS_OVER = "手机错误验证次数超过三次";

    public static final String MSG_BANK_CARD_FORMAT_ERROR = "银行卡格式错误";

    public static final String MSG_USER_IDNO_HAS_VERIFYED = "用户该身份信息已使用";

    public static final String MSG_WITHDRAW_PROCESSING = "有未结束的提现";

    public static final String MSG_WITHDRAW_OVERDUE = "该用户有逾期未还清的标，暂不能提现";

    public static final String MSG_COMM_MISS_VERSION = "未查询到该版本信息";

    public static final String MSG_COMM_LASTEST_VERSION = "最新版本";

    public static final String MSG_LOAN_IS_OVERDUE = "标已逾期";
    
    public static final String MSG_PERMISSION_IS_NOT_DEFINED = "当前状态下，无法进行此操作。";

    public static final String MSG_BANKCARD_BIND_YES = "您的银行卡已鉴权";

    public static final String MSG_BANKCARD_BIND_NO = " 您的银行卡未鉴权";

    public static final String MSG_AGREE_DEDUCT_YES = "您已签署划扣协议";

    public static final String MSG_AGREE_DEDUCT_NO = "您未签署划扣协议";
    public static final String MSG_NO_LOGIN = "未登录";

}

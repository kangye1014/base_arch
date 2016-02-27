package com.ishidai.ischedule.business.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 借款用户类
 * 
 * @author yinjunlu
 */
public class User extends BaseBean {

	private static final long serialVersionUID = 5327695232412679511L;

	/**
	 * 性别 1男2女
	 */
	private Integer gender;

	/**
	 * 核心用户id
	 */
	private Long coreCustomerId;

	/**
	 * 第三方用户标识
	 */
	private String thirdId;

	/**
	 * 用户编号
	 */
	private String userNo;
	/**
	 * 卡牛id
	 */
	private String knUserid;
	/**
	 * 旧版卡牛唯一标示id(姓名+邮箱)
	 */
	private String oldKnUserid;
	/**
	 * 是否
	 */
	private int isNewKnid;
	/**
	 * 昵称 登陆名称
	 */
	private String nickname;
	/**
	 * 手机(注册用)
	 */
	private String mobile;
	/**
	 * 邮箱(注册用)
	 */
	private String regEmail;
	/**
	 * 手机（抓取手机账单用）
	 */
	private String phone;
	/**
	 * 身份证号
	 */
	private String idNo;
	/**
	 * 真实名称，对应身份证号
	 */
	private String realName;
	/**
	 * 用户信用综合评分
	 */
	private Double creditScore;
	/**
	 * 是否身份证验证通过，-1未验证1已验证
	 */
	private Integer idVerified;
	/**
	 * 用户头像
	 */
	private String headImage;
	/**
	 * 信用卡号
	 */
	private String creditCardNo;
	/**
	 * 用户来源 渠道类型
	 */
	private Integer utmSource;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最近登录时间
	 */
	private Date lastLoginTime;
	/**
	 * 取现密码
	 */
	private String cashPassword;
	/**
	 * 是否允许登录，-1不允许1允许，默认1
	 */
	private Integer loginPermission;
	/**
	 * 是否允许借款，-1不允许1允许，默认1
	 */
	private Integer loanPermission;
	/**
	 * 操作更新时间
	 */
	private Date updateTime;
	/**
	 * 淘宝账号
	 */
	private String taobao;
	/**
	 * 是否淘宝验证
	 */
	private int taobaoVerified;
	/**
	 * QQ号
	 */
	private String qq;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 是否手机验证，-1未验证1已验证
	 */
	private Integer mobileVerified;
	/**
	 * 是否邮箱验证，-1未验证1已验证
	 */
	private Integer emailVerified;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 注册客户端类型。
	 */
	private Integer clientType;

	/**
	 * 是否为老用户：0：新用户 1：老用户
	 */
	private Integer oldUser;
	/**
	 * 版本
	 */
	private Integer version;

	// -------------------用户银行卡信息---------------
	private String bankCardNo; // 银行卡号
	private String bankCardName;// 开户行
	private String bankCardId; // 开户行编号
	private String bankCardSub; // 开户支行

	private String region; // 所在地区
	private long idNoValidateCounts;// 身份证验证次数
	private int defaultDeduct;// 是否默认银行卡

	public User() {
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getRealName() {
		return realName == null ? realName : realName.trim();
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getIdVerified() {
		return idVerified;
	}

	public void setIdVerified(Integer idVerified) {
		this.idVerified = idVerified;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public Integer getUtmSource() {
		return utmSource;
	}

	public void setUtmSource(Integer utmSource) {
		this.utmSource = utmSource;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getCashPassword() {
		return cashPassword;
	}

	public void setCashPassword(String cashPassword) {
		this.cashPassword = cashPassword;
	}

	public Integer getLoginPermission() {
		return loginPermission;
	}

	public void setLoginPermission(Integer loginPermission) {
		this.loginPermission = loginPermission;
	}

	public Integer getLoanPermission() {
		return loanPermission;
	}

	public void setLoanPermission(Integer loanPermission) {
		this.loanPermission = loanPermission;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(Integer mobileVerified) {
		this.mobileVerified = mobileVerified;
	}

	public Integer getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Integer emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	// public String toString() {
	// return "User{" + "nickname='" + nickname + '\'' + ", mobile='" + mobile
	// + '\'' + ", idNo='" + idNo + '\'' + ", realName='" + realName
	// + '\'' + ", idVerified=" + idVerified +", creditScore=" + creditScore
	// + ", headImage='" + headImage + '\''
	// + ", utmSource=" + utmSource + ", invitedId=" + invitedId
	// + ", createTime=" + createTime + ", lastLoginTime="
	// + lastLoginTime + ", cashPassword='" + cashPassword + '\''
	// + ", loginPermission=" + loginPermission
	// + ", loanPermission=" + loanPermission
	// + ", commentPermission=" + commentPermission + ", updateTime="
	// + updateTime + ", totalCredit=" + totalCredit
	// + ", availableCredit=" + availableCredit + ", email='" + email
	// + '\'' + ", mobileVerified=" + mobileVerified
	// + ", emailVerified=" + emailVerified + ", password='"
	// + password + '\'' + ", clientType="
	// + clientType + ", version=" + version + '}';
	// }

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public Double getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(Double creditScore) {
		this.creditScore = creditScore;
	}

	public String getKnUserid() {
		return knUserid;
	}

	public void setKnUserid(String knUserid) {
		this.knUserid = knUserid;
	}

	public String getTaobao() {
		return taobao;
	}

	public void setTaobao(String taobao) {
		this.taobao = taobao;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankCardName() {
		return bankCardName;
	}

	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}

	public String getBankCardSub() {
		return bankCardSub;
	}

	public void setBankCardSub(String bankCardSub) {
		this.bankCardSub = bankCardSub;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public int getTaobaoVerified() {
		return taobaoVerified;
	}

	public void setTaobaoVerified(int taobaoVerified) {
		this.taobaoVerified = taobaoVerified;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	public long getIdNoValidateCounts() {
		return idNoValidateCounts;
	}

	public void setIdNoValidateCounts(long idNoValidateCounts) {
		this.idNoValidateCounts = idNoValidateCounts;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Integer getOldUser() {
		return oldUser;
	}

	public void setOldUser(Integer oldUser) {
		this.oldUser = oldUser;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegEmail() {
		return regEmail;
	}

	public void setRegEmail(String regEmail) {
		this.regEmail = regEmail;
	}

	public int getDefaultDeduct() {
		return defaultDeduct;
	}

	public void setDefaultDeduct(int defaultDeduct) {
		this.defaultDeduct = defaultDeduct;
	}

	public String getOldKnUserid() {
		return oldKnUserid;
	}

	public void setOldKnUserid(String oldKnUserid) {
		this.oldKnUserid = oldKnUserid;
	}

	public int getIsNewKnid() {
		return isNewKnid;
	}

	public void setIsNewKnid(int isNewKnid) {
		this.isNewKnid = isNewKnid;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Long getCoreCustomerId() {
		return coreCustomerId;
	}

	public void setCoreCustomerId(Long coreCustomerId) {
		this.coreCustomerId = coreCustomerId;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}

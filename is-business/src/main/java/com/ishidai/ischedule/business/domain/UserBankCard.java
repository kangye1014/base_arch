package com.ishidai.ischedule.business.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class UserBankCard implements java.io.Serializable {

	private static final long serialVersionUID = 6557931504146601309L;

	private long id;
	private long userId; // 客户id
	private String realName; // 真实姓名
	private String bankCard; // 账号
	private String bankName; // 开户行
	private String bankId; // 开户行编号
	private String bankSubName; // 支行名称
	private String bankProvince; // 开户省
	private String bankCity; // 开户城市
	private int status; // 1：作为还款，放款账号 0：不作为还款.放款账号'
	private Date createTime; //
	private Date updateTime; //
	private int version;
	private double money;
	private String idNo;// 证件号码
	private String phone;// 银行预留手机号
	private int state;// 是否是有效的
	private int deductMark;// 划扣标识 0：非划扣银行卡 1.划扣银行卡 2设置为默认的划扣银行卡
	private Date bindDeductTime;// 绑定时间
	private int isCheck;// 是否鉴权

	/** 充值网关（PayGateEnum） */
	private int payGate;

	/**
	 * 绑定银行卡请求ID
	 */
	private String requestId;

	/**
	 * 该银行卡是否绑定成功
	 */
	private int isBindBank;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankSubName() {
		return bankSubName;
	}

	public void setBankSubName(String bankSubName) {
		this.bankSubName = bankSubName;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public int getPayGate() {
		return payGate;
	}

	public void setPayGate(int payGate) {
		this.payGate = payGate;
	}

	public int getDeductMark() {
		return deductMark;
	}

	public void setDeductMark(int deductMark) {
		this.deductMark = deductMark;
	}

	public Date getBindDeductTime() {
		return bindDeductTime;
	}

	public void setBindDeductTime(Date bindDeductTime) {
		this.bindDeductTime = bindDeductTime;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public int getIsBindBank() {
		return isBindBank;
	}

	public void setIsBindBank(int isBindBank) {
		this.isBindBank = isBindBank;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}

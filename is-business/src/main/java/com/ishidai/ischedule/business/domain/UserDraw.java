package com.ishidai.ischedule.business.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.alibaba.fastjson.annotation.JSONField;
import com.puhui.settlement.api.dto.PaymentTaskChannelAmountTotalDTO;

public class UserDraw {
	/**
	 * 主键，自增长
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 核心用户id
	 */
	private Long coreCustomerId;

	/**
	 * 标的id
	 */
	private Long loanId;

	/**
	 * 核心标的id
	 */
	private Long coreRequestId;

	/**
	 * 订单号
	 */
	private String orderNum;

	/**
	 * 应付金额
	 */
	private Double money;

	/**
	 * 实付金额
	 */
	private Double actualMoney;

	/**
	 * 身份证号
	 */
	private String idNo;

	/**
	 * 用户真实名称
	 */
	private String realName;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 银行卡号
	 */
	private String bankNo;

	/**
	 * 银行名称
	 */
	private String bankName;

	/**
	 * 银行编号
	 */
	private String bankCode;

	/**
	 * 开户省份
	 */
	private String bankProvince;

	/**
	 * 开户城市
	 */
	private String bankCity;

	/**
	 * 支行名称
	 */
	private String bankSubName;

	/**
	 * 预约时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date subscribeTime;

	/**
	 * 状态：0-结算划扣中 1-结算划扣成功 2-结算划扣失败 3-核心充值成功 4-核心充值失败
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 还款列表id
	 */
	private Long lendRepayRecordId;

	private Integer[] drawStatus;

	public Integer[] getDrawStatus() {
		return drawStatus;
	}

	public void setDrawStatus(Integer[] drawStatus) {
		this.drawStatus = drawStatus;
	}

	public Long getLendRepayRecordId() {
		return lendRepayRecordId;
	}

	public void setLendRepayRecordId(Long lendRepayRecordId) {
		this.lendRepayRecordId = lendRepayRecordId;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public Double getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}

	private List<PaymentTaskChannelAmountTotalDTO> paymentTaskChannelAmountTotal;// 结算划扣渠道信息

	public List<PaymentTaskChannelAmountTotalDTO> getPaymentTaskChannelAmountTotal() {
		return paymentTaskChannelAmountTotal;
	}

	public void setPaymentTaskChannelAmountTotal(List<PaymentTaskChannelAmountTotalDTO> paymentTaskChannelAmountTotal) {
		this.paymentTaskChannelAmountTotal = paymentTaskChannelAmountTotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCoreCustomerId() {
		return coreCustomerId;
	}

	public void setCoreCustomerId(Long coreCustomerId) {
		this.coreCustomerId = coreCustomerId;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getCoreRequestId() {
		return coreRequestId;
	}

	public void setCoreRequestId(Long coreRequestId) {
		this.coreRequestId = coreRequestId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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

	public String getBankSubName() {
		return bankSubName;
	}

	public void setBankSubName(String bankSubName) {
		this.bankSubName = bankSubName;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
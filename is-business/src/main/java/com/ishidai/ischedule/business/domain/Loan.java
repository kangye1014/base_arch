package com.ishidai.ischedule.business.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.alibaba.fastjson.annotation.JSONField;
import com.ishidai.ischedule.business.utils.LoanUtils;
import com.ishidai.ischedule.business.utils.StatusUtils;
import com.puhui.core.vo.ThirdPayChannelVo;

/**
 * 标的实体
 * 
 * @author wangmeng
 * @time 2014年12月15日下午5:47:14
 */
public class Loan implements java.io.Serializable {

	private static final long serialVersionUID = 7402502979049535471L;

	// --------------------------------标的信息-----------------------------
	private long id;
	private String applyNo; // 申请编号、
	private Long coreCustomerId; // 核心客户id
	private Long coreRequestId; // 核心进件id
	private String loanNo; // 进件号、
	private long contractId; // 合同id 外键
	private String issue; // 期号
	private String title; // 项目标题
	private int type; // 标的类型
	private long userId; // 用户id 外键
	private int shares; // 标的总分数
	private double amount; // 合同金额
	private double realAmount; // 实际贷款金额
	private Double openAmount; // 放款金额
	private double raiseAmount; // 募集金额
	private int raiseLimit; // 募集期限以天为单位
	private int period; // 借款期限以月为单位
	private double rate; // 借款利率（年利率）
	private int repayType; // 还款方式：0：按本付息
	private String loanDesc; // 标的描述
	private String loanPic; // 标的图片
	private double serviceFeeRate; // 服务费率
	private double overdulInsterestRate; // 逾期还款罚息费率
	private double badDebtInsterestRate; // 坏账还款罚息费率
	private double prepaymentInsterest; // 提前还款罚息费率
	private int status; // 0：待审核 1：审核中（风控）2：审核通过（风控） 3：审核不通过（风控）
						// 4：审核通过（人工）(用户确认中) 5：推送成功 6：推送失败 7：已发标
						// 8：已满标 9：流标 10：已放款 11：还款中 12：还款已完成 13：已删除
						// 14：审核不通过（人工） 15：用户确认成功（待推送） 16：用户确认失败
	private int pageStatus; // 页面状态 1：审核中 2：筹款中 3：审核未通过 4：还款中 5:已流标 6：还款完成
							// 7:用户确认中（待确认） 8：用户确认失败
	private int isOverDue; // 是否逾期：0 否 1：是
	private int isBadDebt; // 是否坏账：0：否 1：是
	private int isRiskAudit; // 是否风控审核 0：否 1：是
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date riskAuditTime; // 风控审核时间
	private String riskScore; // 风险控制审核分数
	private double riskMoney; // 风险控制审批金额
	private int isOperateAudit; // 是否人工审核 0：否 1：是
	private long operateAuditStaff; // 人工审核人
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date operateAuditTime; // 人工审核时间
	private String operateAuditOpinion; // 人工审核意见
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date confirmTime; // 用户确认标的时间
	private Date overDueTime; // 逾期时间
	private Date badDebTime; // 坏账时间
	private Date passTime; // 放标时间
	private Date fullTime; // 标满时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date openTime; // 放款时间
	private Date firstRepayTime; // 第一个还款日
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date pushTime; // 推送时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date failTime; // 流标时间
	private Date endTime; // 标结束时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime; // 创建时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime; // 修改时间
	private long operateStaffId; // 操作人
	private int version; //
	private int tempStatus; // 批核结论中用户暂存的临时状态
	private long productId;
	// -------------------------------------用户信息-------------------------------
	private String realName;
	private String mobile;
	// modify 2015-10-12 zengsongbin 增加使用手机号属性，重新重命名防止之类集成重写
	private String usePhone;
	private String nickname;
	private String idNo;
	private String idNOStr;
	private String liveCity;
	private String email;
	private String region;
	// -------------------------------------接单人信息-------------------------------
	private String receiveOrderName;// 接单人名称
	private long receiveOrderId;// 接单人
	// ----------------------------------导出Excel头信息-------------------------------
	private String auditResult; // 审核结果
	private String autoAuditResult; // 自动审核状态
	private String operAuditResult; // 手动审核状态

	private int isNewCreditData;// 是否是卡牛改版后数据
	// -----------------数据来源----------------------------------
	private String applyType;
	private String cellPhoneOsType;
	private Integer source;
	private Integer clientType;
	private int hasUserClick;
	// add zengsongbin 增加放款渠道信息
	private List<ThirdPayChannelVo> channel;

	private Integer busiSource;

	private Long orderId;// 工单id

	public List<ThirdPayChannelVo> getChannel() {
		return channel;
	}

	public void setChannel(List<ThirdPayChannelVo> channel) {
		this.channel = channel;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getBusiSource() {
		return busiSource;
	}

	public void setBusiSource(Integer busiSource) {
		this.busiSource = busiSource;
	}

	public String getUsePhone() {
		return usePhone;
	}

	public void setUsePhone(String usePhone) {
		this.usePhone = usePhone;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
		this.idNOStr = LoanUtils.getIdNoStr(idNo);
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Double getOpenAmount() {
		return openAmount;
	}

	public void setOpenAmount(Double openAmount) {
		this.openAmount = openAmount;
	}

	public double getRaiseAmount() {
		return raiseAmount;
	}

	public void setRaiseAmount(double raiseAmount) {
		this.raiseAmount = raiseAmount;
	}

	public int getRaiseLimit() {
		return raiseLimit;
	}

	public void setRaiseLimit(int raiseLimit) {
		this.raiseLimit = raiseLimit;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getRepayType() {
		return repayType;
	}

	public void setRepayType(int repayType) {
		this.repayType = repayType;
	}

	public String getLoanDesc() {
		return loanDesc;
	}

	public void setLoanDesc(String loanDesc) {
		this.loanDesc = loanDesc;
	}

	public String getLoanPic() {
		return loanPic;
	}

	public void setLoanPic(String loanPic) {
		this.loanPic = loanPic;
	}

	public double getServiceFeeRate() {
		return serviceFeeRate;
	}

	public void setServiceFeeRate(double serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}

	public double getOverdulInsterestRate() {
		return overdulInsterestRate;
	}

	public void setOverdulInsterestRate(double overdulInsterestRate) {
		this.overdulInsterestRate = overdulInsterestRate;
	}

	public double getBadDebtInsterestRate() {
		return badDebtInsterestRate;
	}

	public void setBadDebtInsterestRate(double badDebtInsterestRate) {
		this.badDebtInsterestRate = badDebtInsterestRate;
	}

	public double getPrepaymentInsterest() {
		return prepaymentInsterest;
	}

	public void setPrepaymentInsterest(double prepaymentInsterest) {
		this.prepaymentInsterest = prepaymentInsterest;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		// 生成页面展示状态
		this.pageStatus = StatusUtils.getStatusShow(status);
		// 手动审核状态
		String operAuditResult = "";
		if (status == 0 || status == 1 || status == 2 || status == 91) {
			operAuditResult = "待审核";
		} else if (status == 4 || status == 5 || status == 6 || status == 7 || status == 8 || status == 9 || status == 10 || status == 11 || status == 12 || status == 13 || status == 15
				|| status == 16 || status == 91 || status == 93) {
			operAuditResult = "审核通过";
		} else if (status == 14 || status == 92) {
			operAuditResult = "审核不通过";
		} else if (status == 18) {
			operAuditResult = "反欺诈审核中";
		} else if (status == 19) {
			operAuditResult = "反欺诈审核完成";
		} else if (status == 90) {
			operAuditResult = "待补充资料";
		}
		this.operAuditResult = operAuditResult;
		// 自动动审核状态
		String autoAuditResult = "";
		if (status == 0 || status == 1 || status == 91) {
			autoAuditResult = "待审核";
		} else if (status == 2 || status == 4 || status == 5 || status == 6 || status == 7 || status == 8 || status == 9 || status == 10 || status == 11 || status == 12 || status == 13
				|| status == 14 || status == 15 || status == 16 || status == 17 || status == 18 || status == 19) {
			autoAuditResult = "审核通过";
		} else if (status == 3 || status == 92 || status == 93) {
			autoAuditResult = "审核不通过";
		} else if (status == 90) {
			autoAuditResult = "待补充资料";
		}
		this.autoAuditResult = autoAuditResult;

		// 审核结果
		String autitResult = "";
		if (status == 0 || status == 1 || status == 2 || status == 91) {
			autitResult = "待审核";
		} else if (status == 4 || status == 5 || status == 6 || status == 7 || status == 8 || status == 9 || status == 10 || status == 11 || status == 12 || status == 13 || status == 15
				|| status == 16 || status == 91 || status == 93) {
			autitResult = "审核通过";
		} else if (status == 14 || status == 92) {
			autitResult = "审核不通过";
		} else if (status == 18) {
			autitResult = "反欺诈审核中";
		} else if (status == 19) {
			autitResult = "反欺诈审核完成";
		} else if (status == 90) {
			autitResult = "待补充资料";
		}
		this.auditResult = autitResult;

	}

	public int getIsOverDue() {
		return isOverDue;
	}

	public void setIsOverDue(int isOverDue) {
		this.isOverDue = isOverDue;
	}

	public int getIsBadDebt() {
		return isBadDebt;
	}

	public void setIsBadDebt(int isBadDebt) {
		this.isBadDebt = isBadDebt;
	}

	public Date getOverDueTime() {
		return overDueTime;
	}

	public void setOverDueTime(Date overDueTime) {
		this.overDueTime = overDueTime;
	}

	public Date getBadDebTime() {
		return badDebTime;
	}

	public void setBadDebTime(Date badDebTime) {
		this.badDebTime = badDebTime;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public Date getFullTime() {
		return fullTime;
	}

	public void setFullTime(Date fullTime) {
		this.fullTime = fullTime;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getFirstRepayTime() {
		return firstRepayTime;
	}

	public void setFirstRepayTime(Date firstRepayTime) {
		this.firstRepayTime = firstRepayTime;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public Date getFailTime() {
		return failTime;
	}

	public void setFailTime(Date failTime) {
		this.failTime = failTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public long getOperateStaffId() {
		return operateStaffId;
	}

	public void setOperateStaffId(long operateStaffId) {
		this.operateStaffId = operateStaffId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRiskScore() {
		return riskScore;
	}

	public void setRiskScore(String riskScore) {
		this.riskScore = riskScore;
	}

	public String getLiveCity() {
		return liveCity;
	}

	public void setLiveCity(String liveCity) {
		this.liveCity = liveCity;
	}

	public double getRiskMoney() {
		return riskMoney;
	}

	public void setRiskMoney(double riskMoney) {
		this.riskMoney = riskMoney;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsRiskAudit() {
		return isRiskAudit;
	}

	public void setIsRiskAudit(int isRiskAudit) {
		this.isRiskAudit = isRiskAudit;
	}

	public Date getRiskAuditTime() {
		return riskAuditTime;
	}

	public void setRiskAuditTime(Date riskAuditTime) {
		this.riskAuditTime = riskAuditTime;
	}

	public int getIsOperateAudit() {
		return isOperateAudit;
	}

	public void setIsOperateAudit(int isOperateAudit) {
		this.isOperateAudit = isOperateAudit;
	}

	public long getOperateAuditStaff() {
		return operateAuditStaff;
	}

	public void setOperateAuditStaff(long operateAuditStaff) {
		this.operateAuditStaff = operateAuditStaff;
	}

	public Date getOperateAuditTime() {
		return operateAuditTime;
	}

	public void setOperateAuditTime(Date operateAuditTime) {
		this.operateAuditTime = operateAuditTime;
	}

	public String getOperateAuditOpinion() {
		return operateAuditOpinion;
	}

	public void setOperateAuditOpinion(String operateAuditOpinion) {
		this.operateAuditOpinion = operateAuditOpinion;
	}

	public int getPageStatus() {
		return pageStatus;
	}

	public void setPageStatus(int pageStatus) {
		this.pageStatus = pageStatus;
	}

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getReceiveOrderName() {
		return receiveOrderName;
	}

	public void setReceiveOrderName(String receiveOrderName) {
		this.receiveOrderName = receiveOrderName;
	}

	public long getReceiveOrderId() {
		return receiveOrderId;
	}

	public void setReceiveOrderId(long receiveOrderId) {
		this.receiveOrderId = receiveOrderId;
	}

	public double getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(double realAmount) {
		this.realAmount = realAmount;
	}

	public String getAuditResult() {
		return this.auditResult;
	}

	public String getAutoAuditResult() {
		return autoAuditResult;
	}

	public String getOperAuditResult() {
		return operAuditResult;
	}

	// 审核途径
	public String getAuditTypeStr() {
		if (isOperateAudit == 1 && isRiskAudit == 1) {
			return "人工审核";
		}
		if (isRiskAudit == 1) {
			return "自动审核";
		}
		return "";
	}

	public String getIdNOStr() {
		return idNOStr;
	}

	public void setIdNOStr(String idNOStr) {
		this.idNOStr = idNOStr;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getCellPhoneOsType() {
		return cellPhoneOsType;
	}

	public void setCellPhoneOsType(String cellPhoneOsType) {
		this.cellPhoneOsType = cellPhoneOsType;
	}

	public int getTempStatus() {
		return tempStatus;
	}

	public void setTempStatus(int tempStatus) {
		this.tempStatus = tempStatus;
	}

	public int getIsNewCreditData() {
		return isNewCreditData;
	}

	public void setIsNewCreditData(int isNewCreditData) {
		this.isNewCreditData = isNewCreditData;
	}

	public Long getCoreCustomerId() {
		return coreCustomerId;
	}

	public void setCoreCustomerId(Long coreCustomerId) {
		this.coreCustomerId = coreCustomerId;
	}

	public Long getCoreRequestId() {
		return coreRequestId;
	}

	public void setCoreRequestId(Long coreRequestId) {
		this.coreRequestId = coreRequestId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getHasUserClick() {
		return hasUserClick;
	}

	public void setHasUserClick(int hasUserClick) {
		this.hasUserClick = hasUserClick;
	}

}

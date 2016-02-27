package com.ishidai.ischedule.business.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @note 标的附属dto(包括p_loan与user关联、p_loan_in关联)
 * @author wangmeng
 * @date 2015年8月18日 下午2:30:05
 */
public class LoanAuxiliary {

	/**
	 * 标的id
	 */
	private Long loanId;

	/**
	 * 标的
	 */
	private Loan loan;

	/**
	 * 标的附属id
	 */
	private Long loanInfoId;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 用户
	 */
	private User user;

	/**
	 * 用户银行卡的id
	 */
	private Long cardId;

	/**
	 * 用户银行卡
	 */
	private UserBankCard userBankCard;

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getLoanInfoId() {
		return loanInfoId;
	}

	public void setLoanInfoId(Long loanInfoId) {
		this.loanInfoId = loanInfoId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserBankCard getUserBankCard() {
		return userBankCard;
	}

	public void setUserBankCard(UserBankCard userBankCard) {
		this.userBankCard = userBankCard;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}

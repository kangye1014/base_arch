package com.ishidai.ischedule.business.domain;


public class UserDrawAuxiliary {

	/**
	 * 划扣id
	 */
	private Long drawId;

	/**
	 * 划扣model
	 */
	private UserDraw userDraw;

	/**
	 * 标的id
	 */
	private Long loanId;

	/**
	 * 标的
	 */
	private Loan loan;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 用户
	 */
	private User user;

	public Long getDrawId() {
		return drawId;
	}

	public void setDrawId(Long drawId) {
		this.drawId = drawId;
	}

	public UserDraw getUserDraw() {
		return userDraw;
	}

	public void setUserDraw(UserDraw userDraw) {
		this.userDraw = userDraw;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

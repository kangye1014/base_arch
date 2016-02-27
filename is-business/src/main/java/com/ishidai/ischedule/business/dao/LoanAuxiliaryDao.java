package com.ishidai.ischedule.business.dao;

import java.util.List;

import com.ishidai.ischedule.business.domain.LoanAuxiliary;
import com.ishidai.ischedule.task.dao.BaseDao;
import com.puhui.core.vo.UnRepayLendVo;

/**
 * @note 标的附属Dao层(包括p_loan与user关联、p_loan_in关联)
 * @author zengsongbin
 */
public interface LoanAuxiliaryDao extends BaseDao {

	/**
	 * 
	 * @Title: getLoanUserBankInfo
	 * @Description:获取标的用户、银行卡列表
	 * @param list
	 * @return List<LoanAuxiliary>
	 * @author zengsongbin
	 * @date 2016年1月4日下午2:09:17
	 * @throws
	 */
	public List<LoanAuxiliary> getLoanUserBankInfo(List<UnRepayLendVo> list);

}

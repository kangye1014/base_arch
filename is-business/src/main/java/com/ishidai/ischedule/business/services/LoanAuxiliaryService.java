package com.ishidai.ischedule.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ishidai.ischedule.business.dao.LoanAuxiliaryDao;
import com.ishidai.ischedule.business.domain.LoanAuxiliary;
import com.puhui.core.vo.UnRepayLendVo;
@Component
public class LoanAuxiliaryService {
	@Autowired
	private LoanAuxiliaryDao loanAuxiliaryDao;
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
	public List<LoanAuxiliary> getLoanUserBankInfo(List<UnRepayLendVo> list) {
		return loanAuxiliaryDao.getLoanUserBankInfo(list);
	}

}

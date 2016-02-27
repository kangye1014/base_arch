package com.ishidai.ischedule.business.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ishidai.ischedule.business.domain.Batch;
import com.ishidai.ischedule.business.domain.LoanAuxiliary;
import com.ishidai.ischedule.business.domain.SystemRegion;
import com.ishidai.ischedule.business.domain.UserDraw;
import com.ishidai.ischedule.business.enums.BankCodeEnum;
import com.ishidai.ischedule.business.enums.OperationEnum;
import com.ishidai.ischedule.business.enums.UserDrawStatusEnum;
import com.ishidai.ischedule.business.utils.CoreConfig;
import com.ishidai.ischedule.business.utils.DateUtils;
import com.puhui.core.api.AccountQueryService;
import com.puhui.core.api.RepayQueryService;
import com.puhui.core.common.CoreBranchAccountType;
import com.puhui.core.common.LendRepayPlanStatus;
import com.puhui.core.vo.LendRepayRecordVo;
import com.puhui.core.vo.UnRepayLendVo;
import com.puhui.settlement.api.dto.ModuleType;
import com.puhui.settlement.api.dto.PayOrCollectionReqParams;
import com.puhui.settlement.api.dto.PayOrCollectionReqResult;
import com.puhui.settlement.api.dto.PaymentDraw;
import com.puhui.settlement.api.dto.PaymentDrawScheduleDTO;
import com.puhui.settlement.api.service.PayOrCollectionReqPaymentOrderService;

/**
 * 定时划扣业务处理
 * 
 * @author zengsongbin
 * 
 */
@Component
public class TimingDrawService {
	@Autowired
	private UserDrawService userDrawService;
	@Autowired
	private LoanAuxiliaryService loanAuxiliaryService;
	@Autowired
	private SystemRegionService systemRegionService;
	@Autowired
	private PayOrCollectionReqPaymentOrderService orderService;
	@Autowired
	private RepayQueryService repayQueryService;
	@Autowired
	private AccountQueryService accountQueryService;
	private static Logger logger = LoggerFactory.getLogger(TimingDrawService.class);
	/**
	 * 订单编号前缀
	 */
	public static final String DRAW_ORDERNUM_PRE = "D15";

	public void executeDrawCharge() {
		Date date = new Date();
		List<UserDraw> userDrawList = null;
		// 1:获取批次号
		List<Batch> batchList = getBatchTimeList();
		if (CollectionUtils.isEmpty(batchList)) {
			logger.info("executeDrawCharge--当日{}执行划扣充值操作--无批次号信息", DateUtil.formatDate(date));
			return;
		}
		String batchNo = getBatchNo(batchList);
		if (StringUtils.isEmpty(batchNo)) {
			logger.info("executeDrawCharge--当日{}执行划扣充值操作--无批次号信息", DateUtil.formatDate(date));
			return;
		}
		logger.info("executeDrawCharge--当日{}执行划扣充值操作--批次号信息={}", DateUtil.formatDate(date), batchNo);
		// 2:面向核心获取当日为还款日的还款列表记录
		List<UnRepayLendVo> repayList = repayQueryService.queryUnRepayLendVoByDate(date, CoreConfig.SOURCE_CODE);
		if (CollectionUtils.isEmpty(repayList)) {
			logger.info("获取核心{}还款记录为空,开始查询当日划扣表中划扣失败与推送结算失败的数据", DateUtils.formateDate(date));
			UserDraw userDraw = new UserDraw();
			userDraw.setSubscribeTime(date);
			userDraw.setDrawStatus(new Integer[]{UserDrawStatusEnum.DRAW_FALSE.getType(), UserDrawStatusEnum.PUSH_SETTLEMENT_FAIL.getType()});
			List<UserDraw> userDrawFailList = userDrawService.selectListByParams(userDraw);
			if (CollectionUtils.isEmpty(userDrawFailList)) {
				logger.info("当日{}划扣记录表中无划扣失败记录与推送结算失败记录");
				return;
			}
			Map<Long, List<UserDraw>> map = getUserDrawListGroupByCoreCustomerId(userDrawList);
			userDrawList = generateUserDrawList(map);
		} else {
			logger.info("面向核心获取{}为还款日的还款列表集合大小为：{}", DateUtil.formatDate(date), repayList.size());
			String str = getUnRepayLendVoCoreLendRequestId(repayList);
			// 3:根据核心获得到的还款记录记录列表获取标的用户、银行卡列表
			List<LoanAuxiliary> loanList = getLoanAuxiliaryByRepayList(repayList);
			if (CollectionUtils.isEmpty(loanList)) {
				logger.info("executeDrawCharge--当日{}执行划扣充值操作--无用户标的银行卡信息", DateUtils.formateDate(date));
				return;
			}
			String dbStr = getLoanCoreRequestId(loanList);
			getNotDrawCoreRequestId(str, dbStr);
			userDrawList = userLoanBankToDraw(loanList, repayList);
			if (CollectionUtils.isNotEmpty(userDrawList)) {
				List<UserDraw> insertList = new ArrayList<>();
				insertList.addAll(userDrawList);
				logger.info("{}待划扣记录总数：{}", DateUtils.formateDate(date), insertList.size());
				batchinsertUserdrawList(insertList);
			} else {
				logger.info("executeDrawCharge--当日{}未执行过划扣充值操作--无还款信息或用户标的银行卡信息", DateUtils.formateDate(date));
				return;
			}
			logger.info("executeDrawCharge--当日{}执行划扣充值操作--用户标的银行卡信息总数：{}", DateUtils.formateDate(date), loanList.size());
		}
		// 10面向核心发起预约划扣
		logger.info("开始{}预约划扣", DateUtils.formateDate(date));
		subscribeDraw(userDrawList, batchList.get(0).getBatchNo());
	}

	/**
	 * 
	 * @Title: getLoanAuxiliaryByRepayList
	 * @Description: 根据核心还款记录分批获取标信息及银行卡信息
	 * @param repayList
	 * @return List<LoanAuxiliary>
	 * @author zengsongbin
	 * @date 2016年1月13日下午3:19:28
	 * @throws
	 */
	private List<LoanAuxiliary> getLoanAuxiliaryByRepayList(List<UnRepayLendVo> repayList) {
		long start = System.currentTimeMillis();
		List<LoanAuxiliary> loanListTotal = new ArrayList<>();
		int size = 500;// 每次查询500条
		int arrSize = repayList.size() % size == 0 ? repayList.size() / size : repayList.size() / size + 1;
		logger.info("分批查询标信息,银行卡信息开始，批次大小:{},查询总数：{},批次数：{}", size, repayList.size(), arrSize);
		for (int i = 0; i < arrSize; i++) {
			try {
				List<UnRepayLendVo> splitlist;
				if (size * (i + 1) > repayList.size()) {
					splitlist = repayList.subList(size * i, repayList.size());
				} else {
					splitlist = repayList.subList(size * i, size * (i + 1));
				}
				List<LoanAuxiliary> loanList = loanAuxiliaryService.getLoanUserBankInfo(splitlist);
				if (CollectionUtils.isNotEmpty(loanList)) {
					loanListTotal.addAll(loanList);
				}
			} catch (Exception e) {
				logger.error("批次大小：{}根据核心还款列表分批查询标信息及银行卡信息异常：{},{}", size, e.getMessage(), e);
				continue;
			}
		}
		logger.info("批次大小:{},分批查询标信息,银行卡信息用时:{}毫秒,SystemMemery:freeMemory：{},maxMemory：{},totalMemory：{}", size, System.currentTimeMillis() - start, Runtime.getRuntime().freeMemory(), Runtime
				.getRuntime().maxMemory(), Runtime.getRuntime().totalMemory());
		return loanListTotal;
	}

	/**
	 * 
	 * @Title: batchinsertUserdrawList
	 * @Description: 分批插入划扣记录到数据库表
	 * @param userDrawList
	 *            void
	 * @author zengsongbin
	 * @date 2016年1月14日上午10:37:44
	 * @throws
	 */
	private void batchinsertUserdrawList(List<UserDraw> userDrawList) {
		long start = System.currentTimeMillis();
		deleteAlreadyExistSubscribeDrawinfo(userDrawList);
		if (CollectionUtils.isEmpty(userDrawList)) {
			logger.info("待插入新纪录为空");
			return;
		}
		int size = 500;// 每次查询500条
		int arrSize = userDrawList.size() % size == 0 ? userDrawList.size() / size : userDrawList.size() / size + 1;
		logger.info("分批插入划扣记录开始，批次大小:{},插入总数：{},批次数：{}", size, userDrawList.size(), arrSize);
		for (int i = 0; i < arrSize; i++) {
			try {
				List<UserDraw> splitlist;
				if (size * (i + 1) > userDrawList.size()) {
					splitlist = userDrawList.subList(size * i, userDrawList.size());
				} else {
					splitlist = userDrawList.subList(size * i, size * (i + 1));
				}
				if (CollectionUtils.isNotEmpty(splitlist)) {
					userDrawService.insertList(splitlist);
				}
			} catch (Exception e) {
				logger.error("批次大小：{}分批插入划扣记录失败：{},{}", size, e.getMessage(), e);
				continue;
			}
		}
		logger.info("批次大小:{},分批插入划扣记录用时:{}毫秒,SystemMemery:freeMemory：{},maxMemory：{},totalMemory：{}", size, System.currentTimeMillis() - start, Runtime.getRuntime().freeMemory(), Runtime.getRuntime()
				.maxMemory(), Runtime.getRuntime().totalMemory());

	}

	/**
	 * 
	 * @Title: deleteAlreadyExistSubscribeDrawinfo
	 * @Description: 插入划扣记录时排除已存在划扣记录表的数据
	 * @param userDrawList
	 *            void
	 * @author zengsongbin
	 * @date 2016年1月15日下午3:27:51
	 * @throws
	 */
	private static void deleteAlreadyExistSubscribeDrawinfo(List<UserDraw> userDrawList) {
		Iterator<UserDraw> userDrawIter = userDrawList.iterator();
		while (userDrawIter.hasNext()) {
			UserDraw userDraw = userDrawIter.next();
			if (userDraw.getId() != null) {
				logger.info("划扣记录id:{},核心请求id：{},核心客户id：{}数据已存在划扣记录表中.", userDraw.getId(), userDraw.getCoreRequestId(), userDraw.getCoreCustomerId());
				userDrawIter.remove();
			}
		}
	}

	/**
	 * @Title: getUnRepayLendVoCoreLendRequestId
	 * @Description: 获取核心当日未还款记录的核心请求id
	 * @param repayList
	 * @return String
	 * @author zengsongbin
	 * @date 2016年1月13日下午1:36:57
	 * @throws
	 */

	private static String getUnRepayLendVoCoreLendRequestId(List<UnRepayLendVo> repayList) {
		StringBuilder str = new StringBuilder();
		for (UnRepayLendVo unRepayLendVo : repayList) {
			logger.info("{}还款列表集合内容如下：{}", DateUtils.formateDate(), ReflectionToStringBuilder.toString(unRepayLendVo));
			str.append(unRepayLendVo.getCoreLendRequestId()).append(",");
		}
		logger.info("面向核心当日还款列表核心请求id集合：{}", str.toString());
		return str.toString();
	}

	/**
	 * @Title: getLoanCoreRequestId
	 * @Description: 获取标的用户、银行卡列表核心请求id
	 * @return String
	 * @author zengsongbin
	 * @date 2016年1月13日上午11:54:38
	 * @throws
	 */

	private static String getLoanCoreRequestId(List<LoanAuxiliary> loanList) {
		StringBuilder dbStr = new StringBuilder();
		for (LoanAuxiliary loanAuxiliary : loanList) {
			if (loanAuxiliary.getLoan() != null) {
				dbStr.append(loanAuxiliary.getLoan().getCoreRequestId()).append(",");
			}
		}
		logger.info("验证db获得到的用户标及银行卡信息核心请求id：{}", dbStr.toString());
		return dbStr.toString();
	}
	/**
	 * 
	 * @Title: getNotDrawCoreRequestId
	 * @Description:
	 * @param oldCoreRequestId
	 * @param newCoreRequestId
	 *            void
	 * @author zengsongbin
	 * @date 2015年12月21日下午1:48:50
	 * @throws
	 */

	private static void getNotDrawCoreRequestId(String oldCoreRequestId, String newCoreRequestId) {
		try {
			if (StringUtils.isNotBlank(oldCoreRequestId)) {
				List<String> oldId = Arrays.asList(oldCoreRequestId.split(","));
				List<String> list = new ArrayList<>(Arrays.asList(new String[oldId.size()]));
				List<String> newId = Arrays.asList(newCoreRequestId.split(","));
				Collections.copy(list, oldId);
				list.retainAll(newId);
				Object[] result = list.toArray();
				if (result != null && result.length > 0) {
					logger.info("未执行划扣操作的核心请求id：{}", result);
				}
			}
		} catch (Exception e) {
			logger.error("获取未执行划扣操作的核心请求id异常，{}", e);
		}
	}

	/**
	 * @note 将用户、标的、银行卡信息映射到划扣信息
	 * @param loanList
	 * @param repayList
	 * @return
	 * @author wangmeng
	 * @date 2015年10月10日 下午8:50:39
	 */
	private List<UserDraw> userLoanBankToDraw(List<LoanAuxiliary> loanList, List<UnRepayLendVo> repayList) {
		List<UserDraw> result = null;
		List<UserDraw> userDrawList = new ArrayList<>();
		for (LoanAuxiliary loan : loanList) {
			try {
				if (loan.getUserBankCard() == null) {
					logger.info("标：{}银行卡信息为空，跳过继续下一条", loan.getLoanId());
					continue;
				}
				logger.info("银行卡信息：{}", ReflectionToStringBuilder.toString(loan.getUserBankCard()));
				// 封装用户、标的、银行卡属性
				UserDraw coreUserDraw = new UserDraw();
				coreUserDraw.setBankCode(BankCodeEnum.getNumberByCode(loan.getUserBankCard().getBankId()) != null ? BankCodeEnum.getNumberByCode(loan.getUserBankCard().getBankId()) : "");
				coreUserDraw.setBankName(loan.getUserBankCard().getBankName() == null ? "" : loan.getUserBankCard().getBankName());
				coreUserDraw.setBankNo(loan.getUserBankCard().getBankCard() == null ? "" : loan.getUserBankCard().getBankCard());
				// 获取省code值
				if (loan.getUserBankCard() == null || loan.getUserBankCard().getBankProvince() == null) {
					logger.info("executeDrawCharge--当日未执行过划扣充值操作--标的id={}开户省为null", loan.getLoanId());
					continue;
				}
				SystemRegion systemRegion = systemRegionService.getByParCodeOveName("0", loan.getUserBankCard().getBankProvince());
				coreUserDraw.setBankProvince(systemRegion == null ? "" : systemRegion.getCode());
				// 获取市code值
				if (systemRegion == null || loan.getUserBankCard().getBankCity() == null) {
					logger.info("executeDrawCharge--当日未执行过划扣充值操作--标的id={}开户市为null", loan.getLoanId());
					continue;
				}
				systemRegion = systemRegionService.getCityCode(systemRegion.getCode(), loan.getUserBankCard().getBankCity());
				coreUserDraw.setBankCity(systemRegion == null ? "" : systemRegion.getCode());
				coreUserDraw.setBankSubName(loan.getUserBankCard().getBankSubName() == null ? "" : loan.getUserBankCard().getBankSubName());
				if (loan.getUser() != null) {
					coreUserDraw.setIdNo(loan.getUser().getIdNo() == null ? "" : loan.getUser().getIdNo());
					coreUserDraw.setMobile(loan.getUser().getMobile() == null ? "" : loan.getUser().getMobile());
					coreUserDraw.setCoreCustomerId(loan.getUser().getCoreCustomerId() == null ? 0L : loan.getUser().getCoreCustomerId());
					coreUserDraw.setRealName(loan.getUser().getRealName() == null ? "" : loan.getUser().getRealName());
				} else {
					coreUserDraw.setIdNo("");
					coreUserDraw.setMobile("");
					coreUserDraw.setCoreCustomerId(0L);
					coreUserDraw.setRealName("");
				}
				coreUserDraw.setLoanId(loan.getLoanId() == null ? 0 : loan.getLoanId());
				// 获取订单号
				String orderNum = generateNum(DRAW_ORDERNUM_PRE, loan.getLoanId() == null ? 0 : loan.getLoanId());
				coreUserDraw.setOrderNum(orderNum);

				coreUserDraw.setRemark(UserDrawStatusEnum.DRAW_ING.getValue());
				coreUserDraw.setStatus(UserDrawStatusEnum.DRAW_ING.getType());
				coreUserDraw.setSubscribeTime(new Date());
				coreUserDraw.setUserId(loan.getUserId() == null ? 0 : loan.getUserId());
				// 封装还款属性
				generateDrawInfo(repayList, loan, coreUserDraw);
				userDrawList.add(coreUserDraw);
			} catch (Exception e) {
				logger.error("executeDrawCharge--当日定时划扣失败，标id:{},bank_id:{},核心用户id:{},异常信息：{}", loan.getLoanId(), loan.getCardId(), loan.getLoan() != null ? loan.getLoan().getCoreCustomerId() : "", e);
				continue;
			}
		}
		// 去除客户已自主还款的划扣记录并重新设置客户为自主还款的划扣记录金额
		if (CollectionUtils.isNotEmpty(userDrawList)) {
			// 去除已还款的划扣记录
			logger.info("初始划扣记录总数：{}", userDrawList.size());
			Map<Long, List<UserDraw>> map = getUserDrawListGroupByCoreCustomerId(userDrawList);
			result = generateUserDrawList(map);
			logger.info("去除客户已自主还款的划扣记录和计算公司得到的划扣金额大于零的划扣记录总数：{}", result.size());
		}
		return result;
	}

	/**
	 * 
	 * @Title: getUserDrawListGroupByCoreCustomerId
	 * @Description: 根据核心用户id分组存储划扣记录
	 * @param list
	 * @return Map<String,List<UserDraw>>
	 * @author zengsongbin
	 * @date 2016年1月13日下午4:06:47
	 * @throws
	 */
	private static Map<Long, List<UserDraw>> getUserDrawListGroupByCoreCustomerId(List<UserDraw> list) {
		Map<Long, List<UserDraw>> map = new HashMap<>();
		for (UserDraw userDraw : list) {
			Long coreCustomerId = userDraw.getCoreCustomerId();
			if (map.containsKey(coreCustomerId)) {
				map.get(coreCustomerId).add(userDraw);
			} else {
				List<UserDraw> listTemp = new ArrayList<>();
				listTemp.add(userDraw);
				map.put(coreCustomerId, listTemp);
			}
		}
		logger.info("分组后的划扣记录如下：{}", map);
		return map;
	}

	/**
	 * 
	 * @Title: generateUserDrawList
	 * @Description: 构建未还款且符合条件的划扣记录
	 * @param map
	 * @return List<UserDraw>
	 * @author zengsongbin
	 * @date 2016年1月13日下午6:57:49
	 * @throws
	 */
	private List<UserDraw> generateUserDrawList(Map<Long, List<UserDraw>> map) {
		List<UserDraw> listResult = new ArrayList<>();
		for (Entry<Long, List<UserDraw>> entry : map.entrySet()) {
			try {
				logger.info("获取核心客户id：{}获取账户余额", entry.getKey());
				BigDecimal amount = BigDecimal.ZERO;// 账户余额
				try {
					amount = accountQueryService.queryAccount(entry.getKey(), CoreBranchAccountType.JR_SECOND_ACCOUNT);
					logger.info("获取核心客户id：{}账户余额为：{}", entry.getKey(), amount);
				} catch (Exception e) {
					logger.error("获取核心客户id：{}账户余额出现异常，异常原因：{},{}", entry.getKey(), e.getMessage(), e);
				}
				List<UserDraw> listValue = entry.getValue();
				for (UserDraw userDraw : listValue) {
					logger.info("初始划扣记录信息：{}", ReflectionToStringBuilder.toString(userDraw));
					logger.info("根据核心客户id：{}的还款列表id{}获取还款记录信息", entry.getKey(), userDraw.getLendRepayRecordId());
					try {
						logger.info("根据核心客户id：{}的还款列表id{}检查划扣记录表中【是否】存在记录", entry.getKey(), userDraw.getLendRepayRecordId());
						UserDraw dbUserDraw = userDrawService.selectListBylendRepayRecordId(userDraw);
						if (dbUserDraw != null) {
							logger.info("根据核心客户id：{}的还款列表id{}检查划扣记录表中【存在】记录中，信息如下：{}", entry.getKey(), userDraw.getLendRepayRecordId(), ReflectionToStringBuilder.toString(dbUserDraw));
							boolean retFlag = isStatusInStatusList(dbUserDraw.getStatus(), UserDrawStatusEnum.DRAW_FALSE.getType(), UserDrawStatusEnum.PUSH_SETTLEMENT_FAIL.getType());
							if (retFlag) {
								dbUserDraw.setStatus(UserDrawStatusEnum.DRAW_ING.getType());
								dbUserDraw.setRemark(UserDrawStatusEnum.DRAW_ING.getValue());
								BeanUtils.copyProperties(dbUserDraw, userDraw);
							} else {
								continue;
							}
						}
						logger.info("根据核心客户id：{}的还款列表id{}检查划扣记录表中【不存在】记录", entry.getKey(), userDraw.getLendRepayRecordId());
						LendRepayRecordVo lendRepayRecordVo = repayQueryService.queryLendRepayRecordVo(userDraw.getLendRepayRecordId());
						if (lendRepayRecordVo != null) {
							logger.info("根据核心客户id{}的还款列表id：{},获取到的还款信息如下：{}", entry.getKey(), userDraw.getLendRepayRecordId(), ReflectionToStringBuilder.toString(lendRepayRecordVo));
							if (LendRepayPlanStatus.REPAID.equals(lendRepayRecordVo.getStatus())) {// 已偿还
								if (userDraw.getId() != null) {
									userDraw.setStatus(UserDrawStatusEnum.NOT_DRAW_CONDITION.getType());
									userDraw.setRemark("自动划扣时，根据还款列表id查询核心接口queryLendRepayRecordVo检测到客户已偿还");
									userDrawService.updateByPrimaryKey(userDraw);
								}
								logger.info("根据核心客户id{}的还款列表id：{}获得到的还款列表信息状态为已偿还{}", entry.getKey(), userDraw.getLendRepayRecordId(), lendRepayRecordVo.getStatus());
								continue;
							} else {
								logger.info("根据核心客户id{}的还款列表id：{}获得到的还款金额为：{}", entry.getKey(), userDraw.getLendRepayRecordId(), lendRepayRecordVo.getAmount());
								userDraw.setMoney(lendRepayRecordVo.getAmount().doubleValue());// 每次预划扣金额从根据还款列表id获得到的还款金额算
								logger.info("当前客户：{}预划扣金额：{}", entry.getKey(), userDraw.getMoney());
								if (userDraw.getMoney().compareTo(amount.doubleValue()) > 0) {// 划扣金额大于用户核心账户余额执行划扣
									logger.info("当前客户：{}预划扣金额：{}大于账户余额：{}重置划扣金额", userDraw.getCoreCustomerId(), userDraw.getMoney(), amount);
									BigDecimal repayPrincipal = lendRepayRecordVo.getRepayPrincipal();// 已还本金
									BigDecimal repayInterest = lendRepayRecordVo.getRepayInterest();// 已还利息
									BigDecimal repayPhaseServiceFee = lendRepayRecordVo.getRepayPhaseServiceFee();// 已还期缴服务费
									logger.info("客户：{},当日应划扣金额：{},已还本金：{},已还利息：{},已还期缴服务费：{}", entry.getKey(), userDraw.getMoney(), repayPrincipal, repayInterest, repayPhaseServiceFee);
									Double realDrawMoney = sub(userDraw.getMoney(), repayPrincipal.doubleValue(), repayInterest.doubleValue(), repayPhaseServiceFee.doubleValue(), amount.doubleValue());
									logger.info("划扣金额计算公式：(实际划金额=预划扣金额-已还本金-已还利息-已还期缴服务费-账户余额),客户：{}得到的实际划扣金额为：{}", entry.getKey(), realDrawMoney);
									if (realDrawMoney.compareTo(0D) > 0) {
										userDraw.setMoney(realDrawMoney);
										amount = BigDecimal.ZERO;// 账户余额
										logger.info("更新后客户：{}账户余额为：{}", entry.getKey(), amount);
										logger.info("客户：{}实际执行划扣操作的划扣金额为：{}", entry.getKey(), userDraw.getMoney());
									} else {
										if (userDraw.getId() != null) {
											userDraw.setStatus(UserDrawStatusEnum.NOT_DRAW_CONDITION.getType());
											userDraw.setRemark("划扣金额计算公式：(实际划金额=预划扣金额-已还本金-已还利息-已还期缴服务费-账户余额)得到后的划扣金额小于零，更新划扣状态为失败");
											userDrawService.updateByPrimaryKey(userDraw);
										}
										logger.info("客户：{}划扣金额计算公式得到的值小于零，不执行划扣操作记录", entry.getKey());
										continue;
									}
								} else {
									logger.info("当前客户：{}预划扣金额：{}小于或等于账户余额：{}不执行划扣操作,更新账户余额", entry.getKey(), userDraw.getMoney(), amount);
									amount = BigDecimal.valueOf(sub(amount.doubleValue(), userDraw.getMoney()));
									logger.info("更新后客户：{}账户余额为：{}", entry.getKey(), amount);
									if (userDraw.getId() != null) {
										userDraw.setStatus(UserDrawStatusEnum.NOT_DRAW_CONDITION.getType());
										userDraw.setRemark("当前客户" + userDraw.getCoreCustomerId() + "预划扣金额：" + userDraw.getMoney() + "小于或等于账户余额：" + amount + "不执行划扣操作");
										userDrawService.updateByPrimaryKey(userDraw);
									}
									continue;
								}
							}
						} else {
							logger.info("根据核心客户id{}的还款列表id:{}获取还款列表信息为空", entry.getKey(), userDraw.getLendRepayRecordId());
						}
					} catch (Exception e) {
						logger.error("根据核心客户id{}的还款列表id:{}获取还款列表信息异常，异常原因{},{}", entry.getKey(), userDraw.getLendRepayRecordId(), e.getMessage(), e);
					}
					if (userDraw.getId() != null) {
						// 更新已存在划扣记录表的划扣记录信息，更新金额，状态重新划扣
						UserDraw tempUserDraw = new UserDraw();
						BeanUtils.copyProperties(userDraw, tempUserDraw);
						tempUserDraw.setId(userDraw.getId());
						// 设置新订单编号，防止推送结算时出现订单编号重复无法推送问题
						String orderNum = generateNum(DRAW_ORDERNUM_PRE, userDraw.getLoanId() == null ? 0 : userDraw.getLoanId());
						tempUserDraw.setOrderNum(orderNum);
						tempUserDraw.setStatus(UserDrawStatusEnum.DRAW_ING.getType());
						tempUserDraw.setRemark(UserDrawStatusEnum.DRAW_ING.getValue());
						userDrawService.updateByPrimaryKey(tempUserDraw);
						userDraw.setOrderNum(orderNum);
					}
					listResult.add(userDraw);
				}
			} catch (Exception e) {
				logger.error("根据核心用户id:{}分组循环生成划扣记录失败：{}，{}", entry.getKey(), e.getMessage(), e);
				continue;
			}
		}
		return listResult;
	}
	/**
	 * 
	 * @Title: generateDrawInfo
	 * @Description:生成划扣记录划扣金额，核心请求id，划扣列表id
	 * @param repayList
	 * @param loan
	 * @param coreUserDraw
	 *            void
	 * @author zengsongbin
	 * @date 2016年1月13日下午2:03:13
	 * @throws
	 */

	private static void generateDrawInfo(List<UnRepayLendVo> repayList, LoanAuxiliary loan, UserDraw coreUserDraw) {
		for (UnRepayLendVo repay : repayList) {
			if (loan.getLoan() != null && loan.getLoan().getCoreRequestId().longValue() == repay.getCoreLendRequestId()) {
				coreUserDraw.setMoney(repay.getAmount().doubleValue());
				coreUserDraw.setCoreRequestId(repay.getCoreLendRequestId());
				coreUserDraw.setLendRepayRecordId(repay.getLendRepayRecordId());
				break;
			}
		}
	}

	/**
	 * 
	 * @Title: isStatusInStatusList
	 * @Description: 指定的状态是否在状态列表中
	 * @param status
	 *            指定状态
	 * @param statusMore
	 *            状态列表
	 * @return boolean
	 * @author zengsongbin
	 * @date 2016年1月13日下午5:34:41
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private static <T> boolean isStatusInStatusList(T status, T... statusMore) {
		boolean retFlag = false;
		List<T> statusList = getStatusList(statusMore);
		if (statusList.contains(status)) {
			retFlag = true;
		}
		return retFlag;
	}

	/**
	 * 获得状态列表
	 * 
	 * @Title: getStatusList
	 * @Description:
	 * @param statusMore
	 * @return List<T>
	 * @author zengsongbin
	 * @date 2016年1月13日下午5:34:02
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private static <T> List<T> getStatusList(T... statusMore) {
		return new ArrayList<>(Arrays.asList(statusMore));
	}

	/**
	 * 
	 * @Title: generateNum
	 * @Description: 生成编号
	 * @param preStr
	 * @param suf
	 * @return String
	 * @author zengsongbin
	 * @date 2016年1月4日下午2:31:15
	 * @throws
	 */

	private static String generateNum(String preStr, long suf) {
		StringBuilder num = new StringBuilder(preStr);
		String dateStr = DateUtils.dateToStr(new Date(), 5);
		num.append(dateStr);
		if (String.valueOf(suf).length() > 7) {
			String tmp = String.valueOf(suf);
			num.append(tmp.substring(tmp.length() - 7, tmp.length()));
		} else {
			num.append(String.format("%07d", suf));
		}
		return num.toString();
	}

	/**
	 * @note 获取结算批次号
	 * @param batchList
	 * @return
	 * @author zengsongbin
	 * @date 2015年10月22日 下午5:43:21
	 */

	private static String getBatchNo(List<Batch> batchList) {
		// 使用comparable进行排序
		Collections.sort(batchList);
		String code = DateUtils.formateDate("HH:mm:ss").replace(":", "");
		for (Batch batch : batchList) {
			String bankTime = batch.getBatchTime().replace(":", "");
			if (Integer.valueOf(bankTime) > Integer.valueOf(code)) {
				return batch.getBatchNo();
			}
		}
		return "";
	}

	/**
	 * @note 获取批次时间列表
	 * @return
	 * @author zengsongbin
	 * @date 2015年10月10日 下午2:17:58
	 */
	private List<Batch> getBatchTimeList() {
		List<Batch> batchList = new ArrayList<>();
		// 1:获取结算批次时间列表
		List<PaymentDrawScheduleDTO> batchTimeList = orderService.queryPaymentDrawScheduleList();
		if (CollectionUtils.isEmpty(batchTimeList)) {
			return batchList;
		}
		// 2:实体转换
		for (PaymentDrawScheduleDTO dto : batchTimeList) {
			Batch batch = new Batch();
			batch.setBatchNo(dto.getDrawBatchNo());
			batch.setBatchTime(dto.getDrawTime());
			batchList.add(batch);
		}
		return batchList;
	}

	/**
	 * 
	 * @Title: sub
	 * @Description:减法运算(浮点型)
	 * @param v1
	 * @param v2
	 * @param v3
	 * @param v4
	 * @param v5
	 * @return double
	 * @author zengsongbin
	 * @date 2015年12月10日下午7:08:53
	 * @throws
	 */
	private static double sub(double v1, double v2, double v3, double v4, double v5) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = new BigDecimal(Double.toString(v3));
		BigDecimal b4 = new BigDecimal(Double.toString(v4));
		BigDecimal b5 = new BigDecimal(Double.toString(v5));
		return b1.subtract(b2).subtract(b3).subtract(b4).subtract(b5).doubleValue();

	}

	/**
	 * 减法运算(浮点型)
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个参数的差
	 * @author zengsongbin
	 */
	private static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 
	 * @Title: subscribeDraw
	 * @Description:向结算发起预约划扣预约划扣
	 * @param userDrawList
	 * @param batchNo
	 * @return boolean
	 * @author zengsongbin
	 * @date 2016年1月13日下午1:28:59
	 * @throws
	 */
	private void subscribeDraw(List<UserDraw> userDrawList, String batchNo) {
		long start = System.currentTimeMillis();
		// 1验证划扣列表数据
		if (CollectionUtils.isEmpty(userDrawList)) {
			logger.info("subscribeDrawCharge--无预约划扣记录");
			return;
		}
		// 2:封装批次列表
		List<String> batchNoList = new ArrayList<>();
		batchNoList.add(batchNo);
		// 3:借啊划扣module与结算划扣module映射
		List<PayOrCollectionReqParams> payList = new ArrayList<>();
		for (UserDraw userDraw : userDrawList) {
			PayOrCollectionReqParams payOr = new PayOrCollectionReqParams();
			payOr.setModule(ModuleType.JIEA.name()); // 订单所属业务模块
			payOr.setOrderSerialNumber(userDraw.getOrderNum()); // 订单号
			payOr.setOperation(OperationEnum.DRAW_CHARGE.getCode()); // 订单业务类型
			payOr.setAmount(userDraw.getMoney()); // 订单金额
			payOr.setPaymentType(true); // 是否是收款请求 true:收款请求;false:付款请求
			payOr.setIdCardType("0"); // 证件类型 例如:"0：身份证"
			payOr.setIdCardNo(userDraw.getIdNo()); // 证件号码
			payOr.setAccountName(userDraw.getRealName()); // 开户名
			payOr.setAccountNo(userDraw.getBankNo()); // 银行卡号
			payOr.setBank(userDraw.getBankName()); // 银行名称
			payOr.setBankCode(userDraw.getBankCode()); // 银行编号
			payOr.setBankName(userDraw.getBankSubName()); // 支行名称
			payOr.setAccountProvince(userDraw.getBankProvince()); // 开户行所在省
			payOr.setAccountCity(userDraw.getBankCity()); // 开户行所在市
			payOr.setReserveDate(userDraw.getSubscribeTime()); // 预约划扣日期
			payOr.setUserName("爱钱进.借啊"); // 操作人
			payOr.setPaymentAccountType("PUHUI_THIRD"); // 账户类型
			payOr.setBusinessAccountNoType("1"); // 账户对公/对私类型 1对公 0对私
			payOr.setMobile(userDraw.getMobile()); // 手机号
			payOr.setPaymentDraw(PaymentDraw.RESERVE_DRAW.name()); // 划扣标示:预约划扣
			payOr.setDrawBatchNoList(batchNoList); // 批次时间列表中批次号
			payList.add(payOr);
		}
		// 4:向结算执行划扣
		try {
			logger.info("{}：预约划扣请求参数信息如下：{}", DateUtils.formateDate(), JSON.toJSON(payList));
		} catch (Exception e1) {
			logger.error("打印预约划扣请求参数异常：{},{}", e1.getMessage(), e1);
		}
		List<PayOrCollectionReqResult> resultList;
		try {
			resultList = orderService.batchAddPayOrCollectionReqOrder(payList);
			if (CollectionUtils.isEmpty(resultList)) {
				logger.error("{}向核心发起预约划扣请求后结算返回信息为空", DateUtils.formateDate());
				return;
			}
			// 5:对结算接收数据的结果进行遍历写入日志
			for (PayOrCollectionReqResult result : resultList) {
				logger.info("{}预约划扣请求后,结算返回的响应信息如下：{}", DateUtils.formateDate(), ReflectionToStringBuilder.toString(result));
				try {
					if ("0000".equals(result.getResultCode())) {
						logger.info("订单号:orderNum={},预约划扣结算反馈结果:SUCCESS", result.getPayOrCollectionReqParams().getOrderSerialNumber());
					} else {
						logger.info("订单号:orderNum={},预约划扣结算反馈结果:FALSE,反馈信息={}", result.getPayOrCollectionReqParams().getOrderSerialNumber(), result.getResultMessage());
						// 6推送划扣信息到结算系统失败，更新划扣记录状态为推送失败，并记录原因
						UserDraw ud = new UserDraw();
						ud.setOrderNum(result.getPayOrCollectionReqParams().getOrderSerialNumber());
						ud.setStatus(UserDrawStatusEnum.PUSH_SETTLEMENT_FAIL.getType());
						ud.setRemark(result.getResultMessage());
						userDrawService.updateByOrderNum(ud);
					}
				} catch (Exception e) {
					logger.error("遍历结算接收数据失败：{},{}", e.getMessage(), e);
					continue;
				}
			}
		} catch (Exception e1) {
			logger.error("发送划扣数据请求到结算失败，请求参数列表：{},失败原因：{},{}", JSON.toJSON(payList), e1.getMessage(), e1);
			throw new RuntimeException("发送划扣数据请求到结算失败，请求参数列表：" + JSON.toJSON(payList) + ",失败原因：" + e1.getMessage() + "," + e1);
		}

		logger.info("向结算发起预约划扣用时:{}毫秒,SystemMemery:freeMemory：{},maxMemory：{},totalMemory：{}", System.currentTimeMillis() - start, Runtime.getRuntime().freeMemory(), Runtime.getRuntime().maxMemory(),
				Runtime.getRuntime().totalMemory());
	}
}

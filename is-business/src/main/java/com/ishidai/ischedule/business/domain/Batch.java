package com.ishidai.ischedule.business.domain;

/**
 * @note 结算中心--批次实体
 * @author wangmeng
 * @date 2015年10月9日 下午7:18:25
 */
public class Batch implements Comparable<Batch> {

	private String batchNo; // 批次号

	private String batchTime; // 批次时间

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getBatchTime() {
		return batchTime;
	}

	public void setBatchTime(String batchTime) {
		this.batchTime = batchTime;
	}

	@Override
	public int compareTo(Batch o) {
		return Integer.valueOf(batchNo).compareTo(Integer.valueOf(o.getBatchNo()));
	}

}

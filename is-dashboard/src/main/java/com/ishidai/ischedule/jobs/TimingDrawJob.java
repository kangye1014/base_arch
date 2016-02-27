package com.ishidai.ischedule.jobs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ishidai.ischedule.business.services.TimingDrawService;
import com.ishidai.ischedule.params.JobContent;
/**
 * 定时划扣任务
 * 
 * @author zengsongbin
 * 
 */
public class TimingDrawJob extends JobContent {
	@Autowired
	private TimingDrawService timingDrawService;
	private static Logger logger = LoggerFactory.getLogger(TimingDrawJob.class);

	public void executeDrawCharge() {
		long start = System.currentTimeMillis();
		timingDrawService.executeDrawCharge();
		logger.info("定时划扣操作用时 :{}毫秒,SystemMemery:freeMemory：{},maxMemory：{},totalMemory：{}", System.currentTimeMillis() - start, Runtime.getRuntime().freeMemory(), Runtime.getRuntime().maxMemory(),
				Runtime.getRuntime().totalMemory());
	}

}

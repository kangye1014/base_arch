package com.ishidai.ischedule.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ishidai.ischedule.business.dao.SystemRegionDao;
import com.ishidai.ischedule.business.domain.SystemRegion;

/**
 * @note 行政区manager实现层
 * @author wangmeng
 * @date 2015年10月23日 下午4:29:00
 */
@Component
public class SystemRegionService {
	@Autowired
	private SystemRegionDao systemRegionDao;

	/**
	 * @note 根据父节点code，节点name获取该节点对象
	 * @param parentCode
	 * @param name
	 * @return
	 * @author wangmeng
	 * @date 2015年10月23日 下午4:30:47
	 */
	public SystemRegion getByParCodeOveName(String parentCode, String name) {
		return systemRegionDao.selectByParCodeOveName(parentCode, name);
	}

	/**
	 * @note 获取城市code值
	 * @param parentCode
	 * @param name
	 * @return
	 * @author wangmeng
	 * @date 2015年10月23日 下午4:32:57
	 */
	public SystemRegion getCityCode(String parentCode, String name) {
		// 1:获取parentCode对象
		SystemRegion parentRegion = systemRegionDao.selectByCode(parentCode);
		if (parentRegion == null) {
			return null;
		}
		// 2:如果parentCode 是（北京、重庆、上海、天津）
		if ("上海市".equals(parentRegion.getName()) || "北京市".equals(parentRegion.getName()) || "天津市".equals(parentRegion.getName()) || "重庆市".equals(parentRegion.getName())) {
			return parentRegion;
		}
		// 3:如果parentCode 不是（北京、重庆、上海、天津）
		return systemRegionDao.selectByParCodeOveName(parentCode, name);
	}
}

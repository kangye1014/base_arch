package com.ishidai.ischedule.business.dao;

import org.apache.ibatis.annotations.Param;

import com.ishidai.ischedule.business.domain.SystemRegion;
import com.ishidai.ischedule.task.dao.BaseDao;

/**
 * @note 行政区dao层
 * @author wangmeng
 * @date 2015年10月22日 下午6:39:54
 */
public interface SystemRegionDao extends BaseDao {

	public SystemRegion selectByCode(String code);
	/**
	 * @note 根据父节点code，节点name获取该节点对象
	 * @param parentCode
	 * @param name
	 * @return
	 * @author wangmeng
	 * @date 2015年10月22日 下午8:24:31
	 */
	public SystemRegion selectByParCodeOveName(@Param("parentCode") String parentCode, @Param("name") String name);

}
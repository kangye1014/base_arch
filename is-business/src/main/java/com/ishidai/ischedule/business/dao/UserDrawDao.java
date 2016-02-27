package com.ishidai.ischedule.business.dao;

import java.util.List;

import com.ishidai.ischedule.business.domain.UserDraw;
import com.ishidai.ischedule.task.dao.BaseDao;

/**
 * 用户划扣Dao层
 * 
 * @author zengsongbin
 * 
 */
public interface UserDrawDao extends BaseDao {

	/**
	 * 
	 * @Title: selectListByParams
	 * @Description: 根据对象属性获取划扣记录列表
	 * @param record
	 * @return List<UserDraw>
	 * @author zengsongbin
	 * @date 2016年1月4日下午2:01:45
	 * @throws
	 */
	public List<UserDraw> selectListByParams(UserDraw record);

	/**
	 * 
	 * @Title: insertList
	 * @Description: 批量插入划扣记录
	 * @param recordList
	 * @return int
	 * @author zengsongbin
	 * @date 2016年1月4日下午2:27:12
	 * @throws
	 */
	public int insertList(List<UserDraw> recordList);

	/**
	 * 
	 * @Title: updateByPrimaryKey
	 * @Description: 根据划扣对象更新划扣记录
	 * @param record
	 * @return int
	 * @author zengsongbin
	 * @date 2016年1月4日下午2:27:44
	 * @throws
	 */
	public int updateByPrimaryKey(UserDraw record);

	/**
	 * 
	 * @Title: updateByOrderNum
	 * @Description: 根据订单编号修改划扣记录
	 * @param record
	 * @return int
	 * @author zengsongbin
	 * @date 2016年1月13日下午1:17:42
	 * @throws
	 */
	public int updateByOrderNum(UserDraw record);

	/**
	 * 
	 * @Title: selectListBylendRepayRecordId
	 * @Description: 根据还款列表id获得划扣信息
	 * @param record
	 * @return UserDraw
	 * @author zengsongbin
	 * @date 2016年1月13日下午5:20:05
	 * @throws
	 */
	public UserDraw selectListBylendRepayRecordId(UserDraw record);

	/**
	 * @Title: deleteByPrimaryKey
	 * @Description: 根据主键删除划扣记录
	 * @param id
	 * @return Long
	 * @author zengsongbin
	 * @date 2016年1月13日下午6:19:31
	 * @throws
	 */
	public int deleteByPrimaryKey(Long id);

	/**
	 * 根据划扣记录id更新划扣记录
	 * 
	 * @Title: updateByDrawId
	 * @Description:
	 * @param record
	 * @return int
	 * @author zengsongbin
	 * @date 2016年1月15日上午11:11:14
	 * @throws
	 */
	public int updateByDrawId(UserDraw record);

}
package com.ishidai.ischedule.task.dao;

import java.util.List;

import com.ishidai.ischedule.rest.model.ScheduleJob;

/**
 * 调度任务DAO
 * 
 * @author kangye
 */
public interface ScheduleJobDao extends BaseDao {

    int deleteByPrimaryKey(Long jobId);

    int insert(ScheduleJob record);

    int insertSelective(ScheduleJob record);

    ScheduleJob selectByPrimaryKey(Long jobId);

    int updateByPrimaryKeySelective(ScheduleJob record);

    int updateByPrimaryKey(ScheduleJob record);

    List<ScheduleJob> getAll();

}
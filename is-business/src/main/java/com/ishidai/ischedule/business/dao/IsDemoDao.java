package com.ishidai.ischedule.business.dao;

import com.ishidai.ischedule.business.domain.IsDemo;
import com.ishidai.ischedule.task.dao.BaseDao;

public interface IsDemoDao extends BaseDao {

    IsDemo getDemo(long id);

}

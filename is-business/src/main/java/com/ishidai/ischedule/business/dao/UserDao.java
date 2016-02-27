package com.ishidai.ischedule.business.dao;

import com.ishidai.ischedule.business.domain.User;
import com.ishidai.ischedule.task.dao.BaseDao;

public interface UserDao extends BaseDao {

    User findUserById(Long id);

}

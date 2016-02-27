package com.ishidai.ischedule.business.dao;

import java.util.List;

import com.ishidai.ischedule.business.domain.AppToken;
import com.ishidai.ischedule.business.domain.UserPersonalMessage;
import com.ishidai.ischedule.task.dao.BaseDao;

public interface MessageDao extends BaseDao {

    int savePersonalMessage(UserPersonalMessage personalMessage);

    List<AppToken> getDeviceTokenByUserId(Long userId);

}

package com.ishidai.ischedule.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ishidai.ischedule.business.dao.IsDemoDao;
import com.ishidai.ischedule.business.domain.IsDemo;

@Component
public class IsDemoService {

    @Autowired
    private IsDemoDao demoDao;

    public IsDemo getDemoById(long id) {
        return demoDao.getDemo(id);
    }

}

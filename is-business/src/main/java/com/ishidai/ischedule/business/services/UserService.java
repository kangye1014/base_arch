package com.ishidai.ischedule.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ishidai.ischedule.business.dao.UserDao;
import com.ishidai.ischedule.business.domain.User;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

}

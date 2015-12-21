package com.kangye.linui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kangye.linshow.core.dao.DemoDao;
import com.kangye.linshow.core.entity.Demo;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoDao demoDao;

    @Override
    @Transactional
    public int insert(Demo demo) {
        return demoDao.insertDemo(demo);
    }

    @Override
    public List<Demo> queryAll() {
        return demoDao.quertAll();
    }

}

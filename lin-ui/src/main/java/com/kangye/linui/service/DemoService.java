package com.kangye.linui.service;

import java.util.List;

import com.kangye.linshow.core.entity.Demo;

public interface DemoService {

    public int insert(Demo demo);

    public List<Demo> queryAll();
}

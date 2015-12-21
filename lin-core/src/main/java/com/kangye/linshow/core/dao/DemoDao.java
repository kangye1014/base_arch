package com.kangye.linshow.core.dao;

import java.util.List;

import com.kangye.linshow.core.base.BaseDao;
import com.kangye.linshow.core.entity.Demo;

public interface DemoDao extends BaseDao {

    public int insertDemo(Demo demo);

    public List<Demo> quertAll();

}

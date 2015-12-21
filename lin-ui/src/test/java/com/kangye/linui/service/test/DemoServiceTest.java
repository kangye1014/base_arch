package com.kangye.linui.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kangye.linshow.core.entity.Demo;
import com.kangye.linui.service.DemoService;
import com.kangye.linui.test.base.BaseTest;

public class DemoServiceTest extends BaseTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void insertTest() {

        Demo demo = new Demo();
        demo.setId(1);
        demo.setName("kevin");

        System.out.println(demoService.queryAll());
    }
}

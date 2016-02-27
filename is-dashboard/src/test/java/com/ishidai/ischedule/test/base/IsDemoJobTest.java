package com.ishidai.ischedule.test.base;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ishidai.ischedule.jobs.IsDemojob;

public class IsDemoJobTest extends BaseTest {

    @Autowired
    private IsDemojob isDemojob;

    @Test
    public void startTest() {
        // isDemoService.getDemoById(1);
        isDemojob.printName();
    }

    @Test
    public void intermindTheProject() {
        try {

            TimeUnit.SECONDS.sleep(60);
            System.out.println("project exit!!");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

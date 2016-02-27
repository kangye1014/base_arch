package com.ishidai.ischedule.jobs;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ishidai.ischedule.business.domain.IsDemo;
import com.ishidai.ischedule.business.services.IsDemoService;
import com.ishidai.ischedule.params.JobContent;

@Component("demojob")
public class IsDemojob extends JobContent {

    Logger logger = LoggerFactory.getLogger(IsDemojob.class);

    @Autowired
    private IsDemoService isDemoService;

    public void printName() {

        long id = (new Random().nextInt(5)) + 1;
        logger.info("current demo id is :{}", id);
        logger.info("current demo params is {}", getExtJosnObject());
        IsDemo isDemo = isDemoService.getDemoById(id);
        logger.info("current demo is :{}", isDemo);
    }

    public void printName2() {

        long id = (new Random().nextInt(5)) + 1;
        logger.info("【printName2】current demo id is :{}", id);
        logger.info("【printName2】current demo params is {}", getExtJosnObject());
        IsDemo isDemo = isDemoService.getDemoById(id);
        logger.info("【printName2】current demo is :{}", isDemo);

    }
}

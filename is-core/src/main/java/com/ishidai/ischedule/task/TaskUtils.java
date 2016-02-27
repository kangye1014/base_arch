package com.ishidai.ischedule.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ishidai.ischedule.params.JobContent;
import com.ishidai.ischedule.rest.model.ScheduleJob;
import com.ishidai.ischedule.support.spring.SpringUtils;

/**
 * 任务工具类
 * 
 * @author kangye
 */
public class TaskUtils {

    public final static Logger log = LoggerFactory.getLogger(TaskUtils.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     * 
     * @param scheduleJob
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void invokMethod(ScheduleJob scheduleJob) {

        Object object = null;
        Class clazz = null;

        if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
            object = SpringUtils.getBean(scheduleJob.getSpringId());
        } else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
            try {
                clazz = Class.forName(scheduleJob.getBeanClass());
                object = clazz.newInstance();
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }
        if (object == null) {
            log.error("任务:{}未启动成功，请检查是否配置正确！！！", scheduleJob.getJobName());
            return;
        }
        if (object instanceof JobContent && null != scheduleJob.getExtParams()) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(scheduleJob.getExtParams());
                ((JobContent) object).setExtJosnObject(jsonObject);
            } catch (Exception e) {
                log.error("任务:{}参数绑定错误，请检查是否配置正确！！！", scheduleJob.getJobName());
                return;
            }
        }
        clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
        } catch (NoSuchMethodException e) {
            log.error("任务:{}未启动成功，请检查是否配置正确！！！", scheduleJob.getJobName());
            return;
        } catch (SecurityException e) {
            log.error("方法代理错误SecurityException,{}", e.getMessage());
            return;
        }
        if (method != null) {
            try {
                method.invoke(object);
            } catch (IllegalAccessException e) {
                log.error("方法代理错误IllegalAccessException,{}", e.getMessage());
                return;
            } catch (IllegalArgumentException e) {
                log.error("方法代理错误IllegalArgumentException,{}", e.getMessage());
                return;
            } catch (InvocationTargetException e) {
                log.error("方法代理错误InvocationTargetException,{}", e.getMessage());
                return;
            }
        }
        log.info("任务:{}执行成功", scheduleJob.getJobName());
    }
}

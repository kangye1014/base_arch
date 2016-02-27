package com.ishidai.ischedule.task.controller;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ishidai.ischedule.rest.model.OperateResult;
import com.ishidai.ischedule.rest.model.ScheduleJob;
import com.ishidai.ischedule.support.spring.SpringUtils;
import com.ishidai.ischedule.task.service.JobTaskService;

@Controller
@RequestMapping("/task")
@SuppressWarnings({ "all" })
public class JobTaskController {

    Logger logger = LoggerFactory.getLogger(JobTaskController.class);
    public final static String[] JOB_COMMOND_CHAR = new String[] { "start", "stop" };

    @Autowired
    private JobTaskService taskService;

    @RequestMapping("add")
    @ResponseBody
    public OperateResult taskList(HttpServletRequest request, ScheduleJob scheduleJob) {

        OperateResult operateResult = new OperateResult();
        operateResult.setFlag(false);

        try {
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
        } catch (Exception e) {
            operateResult.setMsg("cron表达式有误，不能被解析！");
            return operateResult;
        }
        Object obj = null;
        try {
            if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
                obj = SpringUtils.getBean(scheduleJob.getSpringId());
            } else {
                Class clazz = Class.forName(scheduleJob.getBeanClass());
                obj = clazz.newInstance();
            }
        } catch (Exception e) {
            // do nothing.........
        }
        if (obj == null) {
            operateResult.setMsg("未找到目标类！");
            return operateResult;
        } else {
            Class clazz = obj.getClass();
            Method method = null;
            try {
                method = clazz.getMethod(scheduleJob.getMethodName(), null);
            } catch (Exception e) {
                // do nothing.....
            }
            if (method == null) {
                operateResult.setMsg("未找到目标方法！");
                return operateResult;
            }
        }
        try {
            taskService.addTask(scheduleJob);
        } catch (Exception e) {
            e.printStackTrace();
            operateResult.setFlag(false);
            operateResult.setMsg("保存失败，检查 name group 组合是否有重复！");
            return operateResult;
        }

        operateResult.setFlag(true);
        return operateResult;
    }

    @RequestMapping("changeJobStatus")
    @ResponseBody
    public OperateResult changeJobStatus(HttpServletRequest request, Long jobId, String cmd) {

        OperateResult operateResult = new OperateResult();
        operateResult.setFlag(false);

        if (jobId == null || !ArrayUtils.contains(JOB_COMMOND_CHAR, cmd)) {
            operateResult.setMsg("参数不合法！");
            return operateResult;
        }

        try {
            taskService.changeStatus(jobId, cmd);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            operateResult.setMsg("任务状态改变失败！");
            return operateResult;
        }
        operateResult.setFlag(true);
        return operateResult;
    }

    @RequestMapping("updateCron")
    @ResponseBody
    public OperateResult updateCron(HttpServletRequest request, Long jobId, String cron) {

        OperateResult operateResult = new OperateResult();
        operateResult.setFlag(false);

        try {
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        } catch (Exception e) {
            operateResult.setMsg("cron表达式有误，不能被解析！");
            return operateResult;
        }
        try {
            taskService.updateCron(jobId, cron);
        } catch (SchedulerException e) {
            operateResult.setMsg("cron更新失败！");
            return operateResult;
        }
        operateResult.setFlag(true);
        return operateResult;
    }

    @RequestMapping("updateExtParams")
    @ResponseBody
    public OperateResult updateExtParams(HttpServletRequest request, Long jobId, String extParams) {

        OperateResult operateResult = new OperateResult();
        operateResult.setFlag(false);

        ScheduleJob job = taskService.getTaskById(jobId);
        if (job == null || ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
            operateResult.setMsg("任务正在运行或不存在，请确保任务存在并停止任务，保存成功后重新启动");
            return operateResult;
        }

        try {
            JSON.parseObject(extParams);
        } catch (Exception e1) {
            operateResult.setMsg("参数需要是json格式");
            return operateResult;
        }

        try {
            taskService.updateExtParams(jobId, extParams);
        } catch (Exception e) {
            operateResult.setMsg("参数更新失败！");
            logger.error("参数更新失败！{}", e.getMessage());
            return operateResult;
        }

        operateResult.setFlag(true);
        return operateResult;
    }

}

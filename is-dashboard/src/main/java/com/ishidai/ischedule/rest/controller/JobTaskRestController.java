package com.ishidai.ischedule.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSON;
import com.ishidai.ischedule.rest.api.ScheduleRestUrl;
import com.ishidai.ischedule.rest.exception.ScheduleJobNotFoundException;
import com.ishidai.ischedule.rest.model.OperateResult;
import com.ishidai.ischedule.rest.model.ScheduleJob;
import com.ishidai.ischedule.task.service.JobTaskService;

@Controller
@RequestMapping(ScheduleRestUrl.SCHEDULE_CONTROLLER_ROOT)
public class JobTaskRestController {

    Logger logger = LoggerFactory.getLogger(JobTaskRestController.class);

    @Autowired
    private JobTaskService taskService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ScheduleJob[] getAllJobs(HttpServletRequest request) {

        logger.info("查询所有任务");
        List<ScheduleJob> taskList = taskService.getAllTask();

        return taskList.toArray(new ScheduleJob[0]);
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeries(@PathVariable("jobId") long id) {
        logger.info("在这里删除任务,id:{}", id);
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.GET)
    public ResponseEntity<ScheduleJob> getSchedule(@PathVariable("jobId") long id) {

        ScheduleJob job = taskService.getTaskById(id);
        if (job == null) {
            return new ResponseEntity<ScheduleJob>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ScheduleJob>(job, HttpStatus.OK);
    }

    @RequestMapping(value = ScheduleRestUrl.SCHEDULE_UPDATE_STATUS_REST, method = RequestMethod.GET)
    @ResponseBody
    public OperateResult changeJobStatus(HttpServletRequest request, @PathVariable("jobId") Long jobId,
            @PathVariable("cmd") String cmd) {

        logger.info("输入参数：jobId:{},cmd:{}", jobId, cmd);
        OperateResult operateResult = new OperateResult();
        operateResult.setFlag(false);

        ScheduleJob job = taskService.getTaskById(jobId);
        if (job == null) {
            operateResult.setMsg("job不存在！");
            return operateResult;
        }
        if (ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus()) && "start".equals(cmd)) {
            operateResult.setMsg("未改变状态,job正在运行！");
            return operateResult;
        }
        if (ScheduleJob.STATUS_NOT_RUNNING.equals(job.getJobStatus()) && "stop".equals(cmd)) {
            operateResult.setMsg("未改变状态,job已经停止！");
            return operateResult;
        }

        try {
            taskService.changeStatus(jobId, cmd);
        } catch (SchedulerException e) {
            logger.error("任务状态更新失败:{}", e.getMessage());
            operateResult.setMsg("任务状态改变失败！");
            return operateResult;
        }
        operateResult.setFlag(true);
        return operateResult;
    }

    @RequestMapping(value = "/updateScheduleExtParams", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateScheduleExtParams(@Valid @RequestBody ScheduleJob scheduleJob, HttpServletRequest request,
            HttpServletResponse response) {

        logger.info("修改附加参数：{}", JSON.toJSONString(scheduleJob));
        ScheduleJob job = taskService.getTaskById(scheduleJob.getJobId());
        if (job == null) {
            throw new ScheduleJobNotFoundException(scheduleJob.getJobId());
        }
        if (ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
            throw new RuntimeException("任务尚在运行，请先关闭");
        }
        taskService.updateExtParams(scheduleJob.getJobId(), scheduleJob.getExtParams());
        response.setHeader("Location", request.getRequestURL().append("/").append(scheduleJob.getJobId()).toString());
    }

    @RequestMapping(value = "/updateScheduleCronExpression", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateScheduleCronExpression(@Valid @RequestBody ScheduleJob scheduleJob, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        logger.info("修改cron表达式：{}", JSON.toJSONString(scheduleJob));
        ScheduleJob job = taskService.getTaskById(scheduleJob.getJobId());
        if (job == null) {
            throw new ScheduleJobNotFoundException(scheduleJob.getJobId());
        }
        taskService.updateCron(job, scheduleJob.getCronExpression());
        response.setHeader("Location", request.getRequestURL().append("/").append(scheduleJob.getJobId()).toString());
    }
}

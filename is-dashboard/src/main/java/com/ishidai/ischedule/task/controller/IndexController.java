package com.ishidai.ischedule.task.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ishidai.ischedule.rest.model.ScheduleJob;
import com.ishidai.ischedule.task.service.JobTaskService;

@Controller
@SuppressWarnings({ "all" })
public class IndexController {

    Logger logger = LoggerFactory.getLogger(JobTaskController.class);

    @Autowired
    private JobTaskService taskService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {

        List<ScheduleJob> taskList = taskService.getAllTask();
        request.setAttribute("taskList", taskList);
        return "index";
    }

    @RequestMapping(value = "/favicon.ico", method = RequestMethod.GET)
    public String favicon() {
        return "redirect:/";
    }
}

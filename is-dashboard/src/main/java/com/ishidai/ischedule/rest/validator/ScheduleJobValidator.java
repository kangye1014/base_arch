package com.ishidai.ischedule.rest.validator;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.alibaba.fastjson.JSON;
import com.ishidai.ischedule.rest.api.RestUrlSpecial;
import com.ishidai.ischedule.rest.model.ScheduleJob;

public class ScheduleJobValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return clazz.equals(ScheduleJob.class);
    }

    @SuppressWarnings("unused")
    public void validate(Object target, Errors errors) {

        if (null == target) {
            errors.reject("schedule.isNull", "Invalidate value schedule");
        }

        ScheduleJob scheduleJob = (ScheduleJob) target;
        String extParams = RestUrlSpecial.reductionUrlSpecialChars(scheduleJob.getExtParams());
        String cronExpression = RestUrlSpecial.reductionUrlSpecialChars(scheduleJob.getCronExpression());

        // ValidationUtils.rejectIfEmpty(errors, "jobId", "jobId.empty");
        if (StringUtils.isNotEmpty(extParams)) {
            try {
                JSON.parseObject(extParams);
                scheduleJob.setExtParams(extParams);
            } catch (Exception e1) {
                errors.rejectValue("extParams", "extParams.not.valid", "附加参数不符合json格式");
            }
        }

        if (StringUtils.isNotEmpty(cronExpression)) {
            try {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
                scheduleJob.setCronExpression(cronExpression);
            } catch (Exception e) {
                errors.rejectValue("cronExpression", "cronExpression.not.valid", "参数 cronExpression不符合cron表达式格式");
            }
        }

    }
}

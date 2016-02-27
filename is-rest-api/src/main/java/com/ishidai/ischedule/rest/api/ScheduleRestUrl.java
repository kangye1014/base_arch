package com.ishidai.ischedule.rest.api;

public final class ScheduleRestUrl {

    public final static String SCHEDULE_CONTROLLER_ROOT = "taskRest";
    public final static String SCHEDULE_UPDATE_PARAMS_REST = "/{jobId}/updateExtParams/{extParams}";
    public final static String SCHEDULE_UPDATE_CRON_REST = "/{jobId}/updateCron/{cron}";
    public final static String SCHEDULE_UPDATE_STATUS_REST = "/{jobId}/changeJobStatus/{cmd}";
}

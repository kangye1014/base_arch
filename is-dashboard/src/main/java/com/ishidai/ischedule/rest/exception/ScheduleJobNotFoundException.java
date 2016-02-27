package com.ishidai.ischedule.rest.exception;

public class ScheduleJobNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3845574518872003019L;

    public ScheduleJobNotFoundException() {
        super();
    }

    public ScheduleJobNotFoundException(String message) {
        super(message);
    }

    public ScheduleJobNotFoundException(Long jobId) {
        super("任务不存在,jobId:" + jobId);
    }
}

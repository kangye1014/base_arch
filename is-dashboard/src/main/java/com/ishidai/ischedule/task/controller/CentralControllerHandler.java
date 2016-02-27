package com.ishidai.ischedule.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.ishidai.ischedule.rest.exception.ScheduleJobNotFoundException;
import com.ishidai.ischedule.rest.validator.ScheduleJobValidator;

@ControllerAdvice
public class CentralControllerHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new ScheduleJobValidator());
    }

    @ExceptionHandler({ ScheduleJobNotFoundException.class })
    public ResponseEntity<String> handlePersonNotFound(ScheduleJobNotFoundException pe) {
        return new ResponseEntity<String>(pe.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException pe) {

        StringBuilder sb = new StringBuilder("输入参数非法:");
        for (ObjectError error : pe.getBindingResult().getAllErrors()) {
            sb.append("[").append(error.getDefaultMessage()).append("] ");
        }

        return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<String> handleException(Exception pe) {
        return new ResponseEntity<String>(pe.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

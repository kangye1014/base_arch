package com.ishidai.ischedule.test.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.ishidai.ischedule.rest.api.RestUrlSpecial;
import com.ishidai.ischedule.rest.model.OperateResult;
import com.ishidai.ischedule.rest.model.ScheduleJob;
import com.ishidai.ischedule.test.base.BaseTest;

public class JobTaskRestControllerTest extends BaseTest {

    private static final String BASE_URI = "http://192.168.137.131/isdash/taskRest";
    private RestTemplate restTemplate = new RestTemplate();

    @Before
    public void setup() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new StringHttpMessageConverter());
        converters.add(new Jaxb2RootElementHttpMessageConverter());
        converters.add(new MappingJacksonHttpMessageConverter());
        restTemplate.setMessageConverters(converters);
    }

    // @Test
    public void testGetAllTaskList() {
        ScheduleJob[] jobs = restTemplate.getForObject(BASE_URI, ScheduleJob[].class);

        assertNotNull(jobs);
        for (ScheduleJob scheduleJob : jobs) {
            System.out.println(JSON.toJSONString(scheduleJob));
        }

    }

    // @Test
    public void testCreateJob() {
        ScheduleJob shcJob = initScheduleJob(70);
        URI newUserLocation = restTemplate.postForLocation(BASE_URI, shcJob);
        assertEquals(BASE_URI + "/70", newUserLocation.toString());
    }

    @Test
    public void testUpdateJobCron() {
        String uri = BASE_URI + "/{jobId}/changeJobStatus/{cmd}";
        // ScheduleJob shcJob = initScheduleJob(1L);
        // restTemplate.put(BASE_URI, shcJob, 1L);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jobId", 12);
        params.put("cmd", RestUrlSpecial.replaceUrlSpecialChars("0/10 * * * * ?"));
        System.out.println(params);
        ResponseEntity<OperateResult> operateResult = restTemplate.getForEntity(uri, OperateResult.class, params);
        System.out.println(JSON.toJSONString(operateResult));
    }

    // @Test
    public void testUpdateExtParams() {
        String uri = BASE_URI + "/{jobId}/updateExtParams/{extParams}";
        // ScheduleJob shcJob = initScheduleJob(1L);
        // restTemplate.put(BASE_URI, shcJob, 1L);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jobId", 12);
        params.put("extParams", "start");
        ResponseEntity<OperateResult> operateResult = restTemplate.getForEntity(uri, OperateResult.class, params);
        System.out.println(JSON.toJSONString(operateResult));
    }

    // @Test
    public void testupdateCron() {
        String uri = BASE_URI + "/{jobId}/updateCron/{cron}";
        // ScheduleJob shcJob = initScheduleJob(1L);
        // restTemplate.put(BASE_URI, shcJob, 1L);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jobId", 12);
        params.put("cron", "start");
        ResponseEntity<OperateResult> operateResult = restTemplate.getForEntity(uri, OperateResult.class, params);
        System.out.println(JSON.toJSONString(operateResult));
    }

    // @Test
    public void deleteJob() {
        String uri = BASE_URI + "/{jobId}";
        restTemplate.delete(uri, 2L);

        try {
            restTemplate.getForEntity(uri, ScheduleJob.class, 2L);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    private ScheduleJob initScheduleJob(long id) {
        ScheduleJob schedule = new ScheduleJob();
        schedule.setJobId(id);
        schedule.setCronExpression("");
        schedule.setDescription("for rest test");
        schedule.setJobGroup("test");
        schedule.setExtParams("ext_params");
        schedule.setIsConcurrent("0");
        schedule.setJobName("testJOb");
        schedule.setJobStatus("1");
        schedule.setMethodName("test");
        schedule.setSpringId("spring_id");
        return schedule;
    }

    /**
     * Tests updating an existing person
     */
    // @Test
    public void updateValidPerson() {
        String uri = "http://localhost:8081/rest-controlleradvice/spring/persons";
        ScheduleJob shcJob = initScheduleJob(21);
        restTemplate.put(uri, shcJob, 2l);

        uri = "http://localhost:8081/rest-controlleradvice/spring/persons/{personId}";
        ScheduleJob createdPerson = restTemplate.getForObject(uri, ScheduleJob.class, 2l);
        assertEquals(shcJob, createdPerson);
        assertNotNull(createdPerson.getJobId());
        assertEquals("George", shcJob.getJobName());
    }

    /**
     * Tests error handling when trying to update an existing person with
     * invalid data
     */
    // @Test
    public void updateInvalidPerson() {
        String uri = "http://localhost:8081/rest-controlleradvice/spring/persons";
        ScheduleJob shcJob = initScheduleJob(21);
        try {
            restTemplate.put(uri, shcJob, 2l);
            throw new AssertionError("Should have returned an 400 error code");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    /**
     * Tests error handling when trying to update a non existing person
     */
    // @Test
    public void updateNonExistingPerson() {
        String uri = "http://localhost:8081/rest-controlleradvice/spring/persons";
        ScheduleJob shcJob = initScheduleJob(51);
        try {
            restTemplate.put(uri, shcJob, 5l);
            throw new AssertionError("Should have returned an 404 error code");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

}

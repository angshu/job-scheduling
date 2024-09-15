package com.example.job_acceptor_scanner.controller;

import com.example.job_acceptor_scanner.service.JobService;
import com.example.job_acceptor_scanner.service.JobServiceImpl;
import com.example.jobmodels.ScheduledJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
public class JobAcceptorController {

    final KafkaTemplate kafkaTemplate;
    final JobService jobService;

    Logger logger = LoggerFactory.getLogger(JobAcceptorController.class);

    @Autowired
    public JobAcceptorController(KafkaTemplate kafkaTemplate, JobService jobService) {
        this.kafkaTemplate = kafkaTemplate;
        this.jobService = jobService;
    }

    @PostMapping
    public Map<String, Object> scheduleJob(@RequestBody ScheduledJob job) {
        String jobUuid = UUID.randomUUID().toString();
        job.setId(jobUuid);
        Map<String, Object> response = new HashMap<>();
        response.put("events", jobService.scheduleEventJobs(job));
        //postJobForScheduling(job);
        response.put("ackId", jobUuid);
        return response;
    }

}

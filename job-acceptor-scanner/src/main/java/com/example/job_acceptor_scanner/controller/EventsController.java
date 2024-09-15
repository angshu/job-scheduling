package com.example.job_acceptor_scanner.controller;

import com.example.job_acceptor_scanner.service.JobService;
import com.example.job_acceptor_scanner.service.JobServiceImpl;
import com.example.jobmodels.ScheduledEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventsController {

    final JobService jobService;

    Logger logger = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    public EventsController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/{id}")
    public List<ScheduledEvent> getEvent(@PathVariable String id) {
        return jobService.findById(id).map(scheduledEvent -> Arrays.asList(scheduledEvent)).orElse(Collections.emptyList());
    }

    @GetMapping("/{id}/scan")
    public List<ScheduledEvent> scan(@PathVariable String id) {
            return jobService.findMatchingEvents(id);
    }

    @DeleteMapping("/{id}")
    public void removeEvent(@PathVariable String id) {
        jobService.removeById(id);
    }

}

package com.example.job_acceptor_scanner.service;

import com.example.jobmodels.ScheduledEvent;
import com.example.jobmodels.ScheduledJob;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<ScheduledEvent> scheduleEventJobs(ScheduledJob job);
    List<ScheduledEvent> findMatchingEvents(String id);
    ScheduledEvent save(ScheduledEvent event);
    void save(List<ScheduledEvent> events);
    Optional<ScheduledEvent> findById(String id);
    void removeById(String id);
}

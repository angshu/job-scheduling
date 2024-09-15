package com.example.job_acceptor_scanner.service;

import com.example.job_acceptor_scanner.listener.JobAcceptedEvent;
import com.example.job_acceptor_scanner.repository.JobRepository;
import com.example.job_acceptor_scanner.util.Utils;
import com.example.jobmodels.ScheduledEvent;
import com.example.jobmodels.ScheduledJob;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.KeyScanOptions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private static final long NUM_EVENTS_TO_PROCESS = 1000;
    Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    final private RedisTemplate<String, Object> redisTemplate;
    final private JobRepository jobRepository;
    final private ApplicationEventPublisher applicationEventPublisher;


//    private HashOperations<String, Long, ScheduledJob> hashOperations;

    @Autowired
    public JobServiceImpl(RedisTemplate<String, Object> redisTemplate, JobRepository jobRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.redisTemplate = redisTemplate;
        this.jobRepository = jobRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostConstruct
    public void init() {
        //this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public ScheduledEvent save(ScheduledEvent event) {
        //hashOperations.put(EMPLOYEE, job.hashCode(), job);
        return jobRepository.save(event);
    }

    @Override
    public Optional<ScheduledEvent> findById(String id) {
        return jobRepository.findById(id);
    }

    @Override
    public void removeById(String id) {
        //return (ScheduledJob) hashOperations.get(EMPLOYEE, id);
        jobRepository.deleteById(id);
    }

    public void save(List<ScheduledEvent> events) {
        jobRepository.saveAll(events);
    }

    @Override
    public List<ScheduledEvent> findMatchingEvents(String id) {
        String pattern = String.format("ScheduledEvent:%s*", id);
        logger.debug(String.format("context: scanning events for pattern: %s:", pattern));
        RedisConnection redisConnection = null;
        List<String> scannedKeys = new ArrayList<>();
        try {
            redisConnection = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();
            ScanOptions options = KeyScanOptions.scanOptions().match(pattern).count(NUM_EVENTS_TO_PROCESS).build();
            try (Cursor<byte[]> scanCursor = redisConnection.commands().scan(options)) {
                while (scanCursor.hasNext()) {
                    String aKey = new String(scanCursor.next());
                    scannedKeys.add(aKey);
                }
            }
        } catch (Exception e) {
            logger.error("error occurred while trying to execute scan", e);
        } finally {
            Objects.requireNonNull(redisConnection).close();
        }

        if (scannedKeys.isEmpty()) {
            logger.debug(String.format("context: no matching keys found for %s", pattern));
            return Collections.emptyList();
        }
        List<ScheduledEvent> events = new ArrayList<>();
        scannedKeys.forEach(aKey -> {
            logger.debug(String.format("context: fetching record for aKey: %s", aKey));
            Optional<ScheduledEvent> eventById = jobRepository.findById(aKey.replace("ScheduledEvent:", ""));
            eventById.ifPresent(events::add);
        });
        return events;
    }

    @Override
    public List<ScheduledEvent> scheduleEventJobs(ScheduledJob job) {
        return Optional.ofNullable(job.getRepeat()).map(eventTiming -> {
            if (eventTiming.getEventsAt().isEmpty()) {
                throw new RuntimeException("Job event times are empty");
            }
            List<ScheduledEvent> scheduledEvents = generateTimedEventsForScheduling(job, eventTiming.getEventsAt());
            this.save(scheduledEvents);
            applicationEventPublisher.publishEvent(new JobAcceptedEvent(scheduledEvents));
            return scheduledEvents;
        }).orElseThrow(() -> new RuntimeException("Could not schedule jobs"));
    }

    private List<ScheduledEvent> generateTimedEventsForScheduling(ScheduledJob job, List<LocalDateTime> dates) {
        List<ScheduledEvent> events = new ArrayList<>();
        if (!dates.isEmpty()) {
            for (int i = 0; i < dates.size(); i++) {
                logger.info("Event Date: " + dates.get(0));
                String eventId = String.format("%s:%s:%d", Utils.getEventKey(dates.get(i)), job.getId(), i);
                //String eventId = Utils.getEventKey(dates.get(i)).concat(":").concat(jobUuid).concat(":").concat(String.valueOf(i));
                events.add(ScheduledEvent.builder()
                        .id(eventId)
                        .jobType(job.getJobType())
                        .title(job.getTitle())
                        .data(job.getEvent())
                        .build());
            }
        }
        return events;
    }
}

package com.example.job_acceptor_scanner.scheduler;


import com.example.job_acceptor_scanner.Constants;
import com.example.job_acceptor_scanner.service.JobServiceImpl;
import com.example.job_acceptor_scanner.util.Utils;
import com.example.jobmodels.ScheduledEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JobScanner {

    private static final Logger logger = LoggerFactory.getLogger(JobScanner.class);
    public static final int JOB_SCAN_OFFSET_IN_MINUTES = 1;
    final private JobServiceImpl jobService;
    final KafkaTemplate kafkaTemplate;

    @Value("${scheduled-jobs.enabled:true}")
    private boolean isJobEnabled;

    @Autowired
    public JobScanner(JobServiceImpl jobService, KafkaTemplate kafkaTemplate) {
        this.jobService = jobService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 60000)
    @ConditionalOnProperty(value = "jobs.enabled", matchIfMissing = true, havingValue = "true")
    public void scanScheduledEvents() {
        if (!isJobEnabled) {
            return;
        }

        LocalDateTime date = LocalDateTime.now().plusMinutes(JOB_SCAN_OFFSET_IN_MINUTES);
        String eventKey = Utils.getEventKey(date);
        logger.info("Scanning for scheduled events. Key: " + eventKey);
        List<ScheduledEvent> matchingEvents = jobService.findMatchingEvents(eventKey);
        logger.info("Scanned for scheduled events. Count: " + matchingEvents.size());
        matchingEvents.forEach(event -> {
            logger.info("Publishing event:" + event.getId());
            this.kafkaTemplate.send(Constants.REMINDERS_TOPIC, event);
        });
    }
}

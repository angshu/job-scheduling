package com.example.job_acceptor_scanner.scheduler;


import com.example.job_acceptor_scanner.Constants;
import com.example.job_acceptor_scanner.service.JobServiceImpl;
import com.example.job_acceptor_scanner.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JobScanner {

    private static final Logger logger = LoggerFactory.getLogger(JobScanner.class);
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

        LocalDateTime date = LocalDateTime.now().plusMinutes(1);
        String eventKey = Utils.getEventKey(date);
        logger.info("Scanning for scheduled events. Key: " + eventKey);
        jobService.findMatchingEvents(eventKey).forEach(event -> {
            logger.info("Publishing event:" + event.getId());
            this.kafkaTemplate.send(Constants.REMINDERS_TOPIC, event);
        });
    }
}

package com.example.job_acceptor_scanner.listener;

import com.example.job_acceptor_scanner.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobAcceptedEventListener implements ApplicationListener<JobAcceptedEvent> {

    Logger logger = LoggerFactory.getLogger(JobAcceptedEventListener.class);

    final KafkaTemplate kafkaTemplate;

    @Autowired
    public JobAcceptedEventListener(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void onApplicationEvent(JobAcceptedEvent event) {
        logger.info("New job events scheduled: " + event.getSource());
        //this.kafkaTemplate.send(Constants.SCHEDULED_JOBS_TOPIC, event.getSource());
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}

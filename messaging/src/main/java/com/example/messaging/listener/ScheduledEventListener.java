package com.example.messaging.listener;

import com.example.jobmodels.ScheduledEvent;
import com.example.messaging.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ScheduledEventListener {

    Logger logger = LoggerFactory.getLogger(ScheduledEventListener.class);

    @KafkaListener(topics = Constants.REMINDERS_TOPIC)
    public void listener(@Payload ScheduledEvent event, ConsumerRecord<String, ScheduledEvent> cr) {
        logger.info("Topic [task-reminders-1] Received message from {} with {} ID ", event.getTitle(), event.getId());
        logger.info(cr.toString());
    }
}

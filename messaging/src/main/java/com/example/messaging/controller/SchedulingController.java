package com.example.messaging.controller;

import com.example.jobmodels.ScheduledEvent;
import com.example.messaging.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping ("/api/task-reminders")
@RestController
public class SchedulingController {

    final KafkaTemplate kafkaTemplate;


    Logger logger = LoggerFactory.getLogger(SchedulingController.class);

    public SchedulingController(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String sentMessage(@RequestBody ScheduledEvent event) {
        this.kafkaTemplate.send(Constants.REMINDERS_TOPIC, event);
        return "Hello World!";
    }

    @KafkaListener(topics = Constants.REMINDERS_TOPIC)
    public void listener(@Payload ScheduledEvent event,  ConsumerRecord<String, ScheduledEvent> cr) {
        logger.info("Topic [task-reminders-1] Received message from {} with {} PLN ", event.getTitle(), event.getInfo());
        logger.info(cr.toString());
    }


}
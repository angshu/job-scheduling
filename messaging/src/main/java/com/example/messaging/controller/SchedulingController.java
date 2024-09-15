package com.example.messaging.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping ("/api/task-reminders")
@RestController
public class SchedulingController {

    final KafkaTemplate kafkaTemplate;


    Logger logger = LoggerFactory.getLogger(SchedulingController.class);

    @Autowired
    public SchedulingController(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

}
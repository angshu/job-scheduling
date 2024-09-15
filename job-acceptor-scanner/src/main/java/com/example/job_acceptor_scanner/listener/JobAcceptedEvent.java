package com.example.job_acceptor_scanner.listener;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class JobAcceptedEvent extends ApplicationEvent {
    public JobAcceptedEvent(Object source) {
        super(source);
    }

    public JobAcceptedEvent(Object source, Clock clock) {
        super(source, clock);
    }
}

package com.example.job_acceptor_scanner.controller;

import com.example.jobmodels.ScheduledJob;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
public class JobAcceptorController {
    @PostMapping
    public Map<String, Object> saveEmployee(@RequestBody ScheduledJob job) {
        UUID uuid = UUID.randomUUID();
        Map<String, Object> response = new HashMap<>();
        response.put("ackId", uuid.toString());
        return response;
    }
}

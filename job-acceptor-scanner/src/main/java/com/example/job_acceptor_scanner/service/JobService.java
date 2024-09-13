package com.example.job_acceptor_scanner.service;

import com.example.job_acceptor_scanner.repository.JobRepository;
import com.example.jobmodels.ScheduledEvent;
import com.example.jobmodels.ScheduledJob;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    private RedisTemplate<String, Object> redisTemplate;
    private JobRepository jobRepository;

    private HashOperations<String, Long, ScheduledJob> hashOperations;

    @Autowired
    public JobService(RedisTemplate<String, Object> redisTemplate, JobRepository jobRepository) {
        this.redisTemplate = redisTemplate;
        this.jobRepository = jobRepository;
    }

    @PostConstruct
    public void init() {
        //this.hashOperations = redisTemplate.opsForHash();
    }

    public ScheduledEvent save(ScheduledEvent event) {
        //hashOperations.put(EMPLOYEE, job.hashCode(), job);
        return jobRepository.save(event);
    }

    public ScheduledEvent findById(String id) {
        //return (ScheduledJob) hashOperations.get(EMPLOYEE, id);
        return jobRepository.findById(id).orElse(null);
    }
}

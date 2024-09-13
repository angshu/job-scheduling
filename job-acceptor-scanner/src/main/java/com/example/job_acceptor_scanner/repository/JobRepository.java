package com.example.job_acceptor_scanner.repository;

import com.example.jobmodels.ScheduledEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<ScheduledEvent, String> {}

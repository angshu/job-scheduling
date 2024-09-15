package com.example.job_acceptor_scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobAcceptorScannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobAcceptorScannerApplication.class, args);
	}

}

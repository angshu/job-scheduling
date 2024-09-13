package com.example.jobmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("ScheduledEvent")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledEvent implements Serializable {
    @Id
    private String id;
    private String jobType;
    private String title;
    private EventDetails info;
}

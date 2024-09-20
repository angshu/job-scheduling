package com.example.jobmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@RedisHash("ScheduledEvent")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScheduledEvent implements Serializable {
    @Id
    private String id;
    private String jobType;
    private String title;
    private EventDetails data;
    @TimeToLive
    private Long expirationInSeconds;
}

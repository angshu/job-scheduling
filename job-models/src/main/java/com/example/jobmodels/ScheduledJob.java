package com.example.jobmodels;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledJob implements Serializable {
    private String jobType;
    private String title;
    private EventTiming repeat;
    private EventDetails event;
}

package com.example.jobmodels;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventTiming implements Serializable {
    @JsonFormat(pattern = "dd-M-yyyy HH:mm")
    private List<LocalDateTime> eventsAt = new ArrayList<>();
    @JsonFormat(pattern = "dd-M-yyyy HH:mm")
    private LocalDateTime fromDate;
    @JsonFormat(pattern = "dd-M-yyyy HH:mm")
    private LocalDateTime toDate;
    private List<String> timeOfDay = new ArrayList<>();
    private List<DayOfWeek> daysOfWeek = new ArrayList<>();
}

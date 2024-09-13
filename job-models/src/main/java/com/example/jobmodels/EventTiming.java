package com.example.jobmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventTiming implements Serializable {
    private List<Date> eventsAt = new ArrayList<>();
    private Date fromDate;
    private Date toDate;
    private List<String> timeOfDay = new ArrayList<>();
    private List<DayOfWeek> daysOfWeek = new ArrayList<>();
}

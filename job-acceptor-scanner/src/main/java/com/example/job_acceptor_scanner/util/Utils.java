package com.example.job_acceptor_scanner.util;

import java.time.LocalDateTime;
import java.util.Calendar;

public class Utils {
    public static String getEventKey(LocalDateTime eventDate) {
        String part1 = String.format("%d%02d%02d", eventDate.getYear(), eventDate.getMonthValue(), eventDate.getDayOfMonth());
        String part2 = String.format("%02d%02d", eventDate.getHour(), eventDate.getMinute());
        return part1.concat(":").concat(part2);
    }
}

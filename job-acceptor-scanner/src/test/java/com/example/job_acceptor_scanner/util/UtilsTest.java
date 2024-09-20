package com.example.job_acceptor_scanner.util;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {

    @SneakyThrows
    @Test
    void generateKeyForEventDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy HH:mm:ss").withLocale(Locale.ENGLISH);
        assertEquals("20150122:1015", Utils.getEventKey(LocalDateTime.parse("22-01-2015 10:15:00", formatter)));
        assertEquals("20151122:2315", Utils.getEventKey(LocalDateTime.parse("22-11-2015 23:15:00", formatter)));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-M-yyyy HH:mm");
        assertEquals("20150122:1015", Utils.getEventKey(LocalDateTime.parse("22-01-2015 10:15", formatter2)));
        assertEquals("20150122:2215", Utils.getEventKey(LocalDateTime.parse("22-01-2015 22:15", formatter2)));
    }

    @Test
    void generateEventDates() {
        java.util.ArrayList<String> dates = new java.util.ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            dates.add("\"20-09-2024 21:51\"");
        }
        System.out.println(String.join(",", dates));
    }

}
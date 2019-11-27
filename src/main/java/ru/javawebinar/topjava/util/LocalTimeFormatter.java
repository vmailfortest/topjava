package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.time.LocalTime;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String time, Locale locale) {
        return DateTimeUtil.parseLocalTime(time);
    }

    @Override
    public String print(LocalTime time, Locale locale) {
        return time.toString();
    }
}
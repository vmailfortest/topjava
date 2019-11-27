package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String date, Locale locale) {
        return DateTimeUtil.parseLocalDate(date);
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        return date.toString();
    }
}
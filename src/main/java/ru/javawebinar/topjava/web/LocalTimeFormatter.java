package ru.javawebinar.topjava.web;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        if (s.length() == 0) {
            return null;
        }
        return LocalTime.parse(s, DateTimeFormatter.ISO_LOCAL_TIME);
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        if (localTime == null) {
            return "";
        }
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}

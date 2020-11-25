package ru.javawebinar.topjava.web.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        return parseLocalDate(s);
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        if (localDate == null) {
            return "";
        }
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

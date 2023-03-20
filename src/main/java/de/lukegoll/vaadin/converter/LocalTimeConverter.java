package de.lukegoll.vaadin.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverter implements Converter<LocalTime, String> {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public Result<String> convertToModel(LocalTime localTime, ValueContext valueContext) {
        try {
            return Result.ok(localTime.toString());
        } catch (Exception e) {
            return Result.error("Datum nicht konvertierbar!");
        }
    }

    @Override
    public LocalTime convertToPresentation(String s, ValueContext valueContext) {
        if (s == null || s.isEmpty()) {
            return null;
        } else {
            return LocalTime.parse(s, TIME_FORMATTER);
        }
    }
}

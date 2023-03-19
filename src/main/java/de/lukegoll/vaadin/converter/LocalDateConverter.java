package de.lukegoll.vaadin.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements Converter<LocalDate, String> {


    @Override
    public Result<String> convertToModel(LocalDate localDate, ValueContext valueContext) {
        try {
            return Result.ok(localDate.toString());
        } catch (Exception e) {
            return Result.error("Datum nicht konvertierbar!");
        }
    }

    @Override
    public LocalDate convertToPresentation(String s, ValueContext valueContext) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String dateTime = s.replace("/", "-");
            char[] chars = dateTime.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < chars.length; i++) {
                if (Character.isDigit(chars[i]) || chars[i] == '-') {
                    builder.append(chars[i]);
                }
            }
            dateTime = builder.toString();
            LocalDate date = LocalDate.parse(dateTime, formatter);
            return date;
        } catch (Exception e) {
            return null;
        }
    }
}

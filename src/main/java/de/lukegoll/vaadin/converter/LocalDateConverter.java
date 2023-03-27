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
            String date = localDate.toString() + "T00:00";
            return Result.ok(date);
        } catch (Exception e) {
            return Result.error("Datum nicht konvertierbar!");
        }
    }

    @Override
    public LocalDate convertToPresentation(String s, ValueContext valueContext) {
        try {
            char[] chars = s.toCharArray();
            boolean foundT = false;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] != 'T' && foundT == false) {
                    builder.append(chars[i]);
                }
                if (chars[i] == 'T') {
                    foundT = true;
                }
            }
            LocalDate date = LocalDate.parse(builder.toString());
            return date;
        } catch (Exception e) {
            return null;
        }
    }
}

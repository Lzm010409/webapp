package de.lukegoll.vaadin.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class IntegerConverter implements Converter<String, Integer> {


    @Override
    public Result<Integer> convertToModel(String number, ValueContext valueContext) {
        try {
            return Result.ok(Integer.parseInt(number));
        } catch (Exception e) {
            return Result.error("Datum nicht konvertierbar!");
        }
    }

    @Override
    public String convertToPresentation(Integer s, ValueContext valueContext) {
       return String.valueOf(s);
    }
}

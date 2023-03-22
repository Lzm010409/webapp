package de.lukegoll.vaadin.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.data.entity.persons.Kunde;

public class KundeConverter implements Converter<Kontakt, Kunde> {


    @Override
    public Result<Kunde> convertToModel(Kontakt kontakt, ValueContext valueContext) {
        try {
            return Result.ok((Kunde) kontakt);
        } catch (Exception e) {
            return Result.error("Datum nicht konvertierbar!");
        }
    }

    @Override
    public Kontakt convertToPresentation(Kunde kunde, ValueContext valueContext) {
       return (Kontakt) kunde;
    }
}

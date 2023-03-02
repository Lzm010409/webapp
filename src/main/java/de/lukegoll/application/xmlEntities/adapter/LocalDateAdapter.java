package de.lukegoll.application.xmlEntities.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
        public LocalDate unmarshal(String v) throws Exception {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(v, formatter);
            return date;
        }

        public String marshal(LocalDate v){
            return v.toString();
        }

    }


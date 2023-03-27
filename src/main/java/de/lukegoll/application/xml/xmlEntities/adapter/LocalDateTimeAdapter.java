package de.lukegoll.application.xml.xmlEntities.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    public LocalDateTime unmarshal(String v) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            LocalDateTime date = LocalDateTime.parse(v);
            return date;
        }catch (Exception e){
            return null;
        }

    }

    public String marshal(LocalDateTime v) {
        return v.toString();
    }

}


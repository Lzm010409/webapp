package de.lukegoll.application.otheradapters;

import java.time.LocalDateTime;

public class SpecializedLocalDateTimeAdapter {

    public String unmarshal(LocalDateTime localDateTime) {
        try{
            String time = String.valueOf(localDateTime.getDayOfMonth()) + "/";
            time += String.valueOf(localDateTime.getMonthValue())+"/";
            time += String.valueOf(localDateTime.getYear());
            return time;
        }catch (Exception e){
            return "";
        }

    }
}

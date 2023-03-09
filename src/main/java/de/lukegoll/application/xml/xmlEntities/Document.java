package de.lukegoll.application.xml.xmlEntities;


import de.lukegoll.application.xml.xmlEntities.adapter.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Date;

@XmlRootElement
public class Document {

    private LocalDateTime date;

    private Case fall;
    private ClaimnetDistribution claimnetDistribution;

    public Document(){

    }



    public Document(LocalDateTime date, Case fall, ClaimnetDistribution claimnetDistribution) {
        this.date = date;
        this.fall = fall;
        this.claimnetDistribution = claimnetDistribution;
    }

    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    @XmlElement
    public ClaimnetDistribution getClaimnetDistribution() {
        return claimnetDistribution;
    }

    public void setClaimnetDistribution(ClaimnetDistribution claimnetDistribution) {
        this.claimnetDistribution = claimnetDistribution;
    }
    @XmlElement(name = "case")
    public Case getFall() {
        return fall;
    }

    public void setFall(Case fall) {
        this.fall = fall;
    }
}

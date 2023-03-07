package de.lukegoll.application.xml.xmlEntities;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Document {

    private Date date;

    private Case fall;
    private ClaimnetDistribution claimnetDistribution;

    public Document(){

    }



    public Document(Date date, Case fall, ClaimnetDistribution claimnetDistribution) {
        this.date = date;
        this.fall = fall;
        this.claimnetDistribution = claimnetDistribution;
    }

    @XmlElement
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

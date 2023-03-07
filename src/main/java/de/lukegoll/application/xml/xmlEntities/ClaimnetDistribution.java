package de.lukegoll.application.xml.xmlEntities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClaimnetDistribution {
    private String receiver_id;
    private String sender_id;
    private String external_id;

    public ClaimnetDistribution() {

    }

    public ClaimnetDistribution(String receiver_id, String sender_id, String external_id) {
        this.receiver_id = receiver_id;
        this.sender_id = sender_id;
        this.external_id = external_id;
    }

    @XmlElement
    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    @XmlElement
    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    @XmlElement
    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }
}

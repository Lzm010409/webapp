package de.lukegoll.application.xml.xmlEntities.caseData.participantData;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {
    private String name;
    private String value;

    public Contact() {

    }

    public Contact(String name1, String value) {
        this.name = name1;
        this.value = value;
    }
    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlElement
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

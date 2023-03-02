package de.lukegoll.application.xmlEntities.caseData.participantData;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Contacts {

    private List<Contact>contact;

    public Contacts(){

    }
    @XmlElement
    public List<Contact> getContact() {
        return contact;
    }

    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }
}

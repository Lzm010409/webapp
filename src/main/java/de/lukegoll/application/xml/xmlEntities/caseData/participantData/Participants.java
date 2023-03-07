package de.lukegoll.application.xml.xmlEntities.caseData.participantData;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Participants {

    private List<Participant>participant;

    public Participants(List<Participant> participant) {
        this.participant = participant;
    }
    public Participants(){

    }
    @XmlElement
    public List<Participant> getParticipant() {
        return participant;
    }

    public void setParticipant(List<Participant> participant) {
        this.participant = participant;
    }
}

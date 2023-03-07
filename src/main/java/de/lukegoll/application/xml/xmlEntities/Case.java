package de.lukegoll.application.xml.xmlEntities;

import de.lukegoll.application.xml.xmlEntities.caseData.Admin_Data;
import de.lukegoll.application.xml.xmlEntities.caseData.Attachments;
import de.lukegoll.application.xml.xmlEntities.caseData.ClaimnetInfo;
import de.lukegoll.application.xml.xmlEntities.caseData.Vehicle;
import de.lukegoll.application.xml.xmlEntities.caseData.participantData.Participants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Case {
    private Vehicle vehicle;
    private Admin_Data admin_data;
   // private Calculation calculation;
    private Participants participants;
    private Attachments attachments;
    private ClaimnetInfo claimnetInfo;

    public Case(){

    }

    public Case(Vehicle vehicle, Admin_Data admin_data, Participants participants, Attachments attachments, ClaimnetInfo claimnetInfo) {
        this.vehicle = vehicle;
        this.admin_data = admin_data;
       // this.calculation = calculation;
        this.participants = participants;
        this.attachments = attachments;
        this.claimnetInfo = claimnetInfo;
    }

    @XmlElement
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @XmlElement
    public Admin_Data getAdmin_data() {
        return admin_data;
    }

    public void setAdmin_data(Admin_Data admin_data) {
        this.admin_data = admin_data;
    }

   /* @XmlElement
    public Calculation getCalculation() {
        return calculation;
    }

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }*/
   @XmlElement
    public Participants getParticipants() {
        return participants;
    }

    public void setParticipants(Participants participants) {
        this.participants = participants;
    }
    @XmlElement
    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }

    @XmlElement
    public ClaimnetInfo getClaimnetInfo() {
        return claimnetInfo;
    }

    public void setClaimnetInfo(ClaimnetInfo claimnetInfo) {
        this.claimnetInfo = claimnetInfo;
    }
}

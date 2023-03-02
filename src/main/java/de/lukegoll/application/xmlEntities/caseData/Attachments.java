package de.lukegoll.application.xmlEntities.caseData;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Attachments {

    private List<Attachment> attachment;

    public Attachments(){

    }

    public Attachments(List<Attachment> attachment) {
        this.attachment = attachment;
    }
    @XmlElement
    public List<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
    }
}

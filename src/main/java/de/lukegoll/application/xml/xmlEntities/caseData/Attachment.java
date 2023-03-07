package de.lukegoll.application.xml.xmlEntities.caseData;


import de.lukegoll.application.xml.xmlEntities.constants.DocumentType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="attachment")
public class Attachment {

    private String base64;
    private String filename;
    private String file_extension;
    private DocumentType document_type;

    public Attachment(){

    }

    public Attachment(String base64, String filename, String file_extension, DocumentType document_type) {
        this.base64 = base64;
        this.filename = filename;
        this.file_extension = file_extension;
        this.document_type = document_type;
    }
    @XmlElement
    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
    @XmlElement
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    @XmlElement
    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
    }
    @XmlElement
    public DocumentType getDocument_type() {
        return document_type;
    }

    public void setDocument_type(DocumentType document_type) {
        this.document_type = document_type;
    }
}

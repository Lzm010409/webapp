package de.lukegoll.application.mailService;

import java.util.List;

public class Mail {

    private String sender;
    private List<String> empfänger;
    private String time;
    private List<Anhänge> files;
    private String betreff;

    public Mail() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getEmpfänger() {
        return empfänger;
    }

    public void setEmpfänger(List<String> empfänger) {
        this.empfänger = empfänger;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Anhänge> getFiles() {
        return files;
    }

    public void setFiles(List<Anhänge> files) {
        this.files = files;
    }

    public String getBetreff() {
        return betreff;
    }

    public void setBetreff(String betreff) {
        this.betreff = betreff;
    }
}

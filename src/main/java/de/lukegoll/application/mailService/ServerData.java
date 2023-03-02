package de.lukegoll.application.mailService;

public enum ServerData {

    SMTPHOST("smtp.strato.de"),
    SMTPPORT("465"),
    IMAPHOST("imap.strato.de"),
    IMAPPORT("993");
    private String data;

    ServerData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

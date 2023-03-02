package de.lukegoll.application.mailService;

import java.io.File;

public class Anhänge {

    private File file;

    private String title;

    public Anhänge() {
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

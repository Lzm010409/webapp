package de.lukegoll.vaadin.views.auftragsanlage;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import de.lukegoll.application.beans.AuftragsVerarbeitungBean;
import de.lukegoll.application.events.LogEvent;
import de.lukegoll.application.logWriter.LogWriter;
import de.lukegoll.application.mailService.empfänger.ReceiveMailService;
import de.lukegoll.vaadin.views.MainLayout;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.Registration;
import javax.swing.event.ChangeEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

@PageTitle("Auftragsanlage")
@Route(value = "auftragsanlage", layout = MainLayout.class)
public class AuftragsanlageView extends VerticalLayout {
    //TextArea loggerScreen = new TextArea();
    Button startButton = new Button("Start");
    Button stopButton = new Button("Stop");
    VerticalLayout loggerScreen = new VerticalLayout();
    StringBuilder logBuilder = new StringBuilder();
    List<String> logList = new LinkedList<>();
    boolean runCondition = false;

    int logLength = 0;

    private AuftragsVerarbeitungBean thread;

    ProgressBar progressBar = new ProgressBar();
    ReceiveMailService receiveMailService = new ReceiveMailService();


    public AuftragsanlageView() {
        progressBar.setWidth("15em");
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        //configureLoggerScreen();
        configureStartButton();
        configureStopButton();
        add(loggerScreen, progressBar, startButton, stopButton);
    }

    private void configureStartButton() {
        startButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);
        loggerScreen.add(new Span("Waiting for updates"));
        startButton.addClickListener(buttonClickEvent -> {
            thread = new AuftragsVerarbeitungBean(buttonClickEvent.getSource().getUI().orElseThrow(), this);
            thread.start();
        });

    }

    public void updateUi(UI ui, String result) {
        logLength += 1;
        if (logLength <= 10) {
            ui.access(() -> {
                loggerScreen.add(new Span(result));
                //progressBar.setVisible(false);
            });
        } else {
            ui.access(() -> {
                loggerScreen.removeAll();
                logLength = 1;
                loggerScreen.add(new Span(result));
            });
        }

    }


    private void configureStopButton() {
        stopButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);
        stopButton.addClickListener(buttonClickEvent -> {
            thread.interrupt();
            thread = null;
        });
    }


    public Button getStartButton() {
        return startButton;
    }

    public void setStartButton(Button startButton) {
        this.startButton = startButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public void setStopButton(Button stopButton) {
        this.stopButton = stopButton;
    }

    public boolean isRunCondition() {
        return runCondition;
    }

    public void setRunCondition(boolean runCondition) {
        this.runCondition = runCondition;
    }

    public VerticalLayout getLoggerScreen() {
        return loggerScreen;
    }

    public void setLoggerScreen(VerticalLayout loggerScreen) {
        this.loggerScreen = loggerScreen;
    }

    public StringBuilder getLogBuilder() {
        return logBuilder;
    }

    public void setLogBuilder(StringBuilder logBuilder) {
        this.logBuilder = logBuilder;
    }

    public List<String> getLogList() {
        return logList;
    }

    public void setLogList(List<String> logList) {
        this.logList = logList;
    }


    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public ReceiveMailService getReceiveMailService() {
        return receiveMailService;
    }

    public void setReceiveMailService(ReceiveMailService receiveMailService) {
        this.receiveMailService = receiveMailService;
    }
}

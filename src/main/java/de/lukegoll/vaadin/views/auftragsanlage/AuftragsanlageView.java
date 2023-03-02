package de.lukegoll.vaadin.views.auftragsanlage;

import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.lukegoll.application.AuftragsAnlageService;
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.vaadin.views.MainLayout;

import java.util.List;

@PageTitle("Auftragsanlage")
@Route(value = "auftragsanlage", layout = MainLayout.class)
public class AuftragsanlageView extends VerticalLayout {
    TextArea loggerScreen = new TextArea();
    Button startButton = new Button("Start");
    Button stopButton = new Button("Stop");
    boolean runCondition = false;

    AuftragsAnlageService auftragsAnlageService = new AuftragsAnlageService();

    public AuftragsanlageView() {
        configureLoggerScreen();
        configureStartButton();
        configureStopButton();
        add(loggerScreen, startButton, stopButton);
    }

    private void configureLoggerScreen() {
        loggerScreen.setHeightFull();
        loggerScreen.setLabel("Log");
        loggerScreen.setEnabled(false);
        loggerScreen.setHeight("500px");
        loggerScreen.setWidth("500px");
    }

    private void configureStartButton() {
        startButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);
        startButton.addClickListener(buttonClickEvent -> startAuftragsService(true));
    }

    private void configureStopButton() {

        stopButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);
        startButton.addClickListener(buttonClickEvent -> setRunCondition(false));
    }

    private void startAuftragsService(boolean start) {
        runCondition = start;
        while (runCondition = true) {
            List<Auftrag> auftragList = this.auftragsAnlageService.startAuftragsService();
        }
    }

    public TextArea getLoggerScreen() {
        return loggerScreen;
    }

    public void setLoggerScreen(TextArea loggerScreen) {
        this.loggerScreen = loggerScreen;
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

    public AuftragsAnlageService getAuftragsAnlageService() {
        return auftragsAnlageService;
    }

    public void setAuftragsAnlageService(AuftragsAnlageService auftragsAnlageService) {
        this.auftragsAnlageService = auftragsAnlageService;
    }
}

package de.lukegoll.vaadin.views.auftragsanlage;

import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.lukegoll.application.AuftragsAnlageService;
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.mailService.Mail;
import de.lukegoll.application.mailService.empfänger.ReceiveMailService;
import de.lukegoll.vaadin.views.MainLayout;
import jakarta.mail.MessagingException;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.LinkedList;
import java.util.List;

@PageTitle("Auftragsanlage")
@Route(value = "auftragsanlage", layout = MainLayout.class)
public class AuftragsanlageView extends VerticalLayout {
    TextArea loggerScreen = new TextArea();
    Button startButton = new Button("Start");
    Button stopButton = new Button("Stop");
    boolean runCondition = false;

    ProgressBar progressBar = new ProgressBar();
    AuftragsAnlageService auftragsAnlageService = new AuftragsAnlageService();
    ReceiveMailService receiveMailService = new ReceiveMailService();

    public AuftragsanlageView() {
        progressBar.setWidth("15em");
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        configureLoggerScreen();
        configureStartButton();
        configureStopButton();
        add(loggerScreen, progressBar, startButton, stopButton);
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
        startButton.addClickListener(buttonClickEvent -> {
            try {

                UI ui = buttonClickEvent.getSource().getUI().orElseThrow();
                progressBar.setVisible(true);


                //stopButton.addClickListener(e -> future.cancel(true));
              /*  ListenableFuture<String> future = receiveMailService.downloadNewMails();
                ListenableFuture<List<Auftrag>> futureAufträge ;
                for (int i = 0; i < future.get().size(); i++) {
                }
                future.addCallback(
                        successResult -> updateUi(ui, "Task finished. Anzahl der heruntergeladenen Mails:" + successResult.size()),
                        failureResult -> updateUi(ui, "Task failed" + failureResult.getMessage())
                );*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateUi(UI ui, String result) {
        ui.access(() -> {
            Notification.show(result);
            progressBar.setVisible(false);
        });
    }

    private void configureStopButton() {

        stopButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);
    }

  /*  private void startAuftragsService(boolean start) throws InterruptedException {

        runCondition = start;
        while (runCondition = true) {
            List<Auftrag> auftragList = this.auftragsAnlageService.startAuftragsService(receiveMailService);
            Thread.sleep(10000);
        }
    }*/

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

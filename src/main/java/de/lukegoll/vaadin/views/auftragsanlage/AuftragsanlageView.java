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
import de.lukegoll.application.xml.xmlTranslator.XMLTranslator;
import de.lukegoll.vaadin.views.MainLayout;
import jakarta.mail.MessagingException;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@PageTitle("Auftragsanlage")
@Route(value = "auftragsanlage", layout = MainLayout.class)
public class AuftragsanlageView extends VerticalLayout {
    TextArea loggerScreen = new TextArea();
    Button startButton = new Button("Start");
    Button stopButton = new Button("Stop");
    StringBuilder logBuilder = new StringBuilder();
    List<String> logList = new LinkedList<>();
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
        try {
            receiveMailService.login("Entwicklung@gollenstede-entwicklung.de", "rijdyg-tatquw-8pebzU");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
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

                ListenableFuture<List<Mail>> mailFuture = receiveMailService.downloadNewMails();
                ListenableFuture<List<Auftrag>> auftragFuture = new AuftragsAnlageService().startAuftragsService(mailFuture.get());
                ListenableFuture<List<String>> xmlFuture = new XMLTranslator().writeXmlRequests(auftragFuture.get());

                stopButton.addClickListener(e -> mailFuture.cancel(true));

                for (int i = 0; i < mailFuture.get().size(); i++) {
                }
                mailFuture.addCallback(
                        successResult -> updateUi(ui, "Task finished. Anzahl der heruntergeladenen Mails:" + successResult.size()),
                        failureResult -> updateUi(ui, "Task failed" + failureResult.getMessage())
                );
                auftragFuture.addCallback(
                        successResult -> updateUi(ui, "Anzahl der verarbeiteten Aufträge" + successResult.size()),
                        failureResult -> updateUi(ui, "Task failed" + failureResult.getMessage())
                );
                xmlFuture.addCallback(
                        successResult -> updateUi(ui, "Es wurden: " + successResult.size() + " Aufträge umgewandelt"),
                        failureResult -> updateUi(ui, "Task failed" + failureResult.getMessage())
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateUi(UI ui, String result) {
        if ((logList.size() - 1) > 50) {
            logBuilder.setLength(0);
            logList.clear();
        } else {
            logList.add(result);
            logBuilder.append(result + "/n");
        }
        ui.access(() -> {
            Notification.show(result);
            loggerScreen.setValue(logBuilder.toString());
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

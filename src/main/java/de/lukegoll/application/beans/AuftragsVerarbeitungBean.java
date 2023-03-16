package de.lukegoll.application.beans;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.logWriter.LogWriter;
import de.lukegoll.application.mailService.Mail;
import de.lukegoll.application.mailService.empfänger.ReceiveMailService;
import de.lukegoll.application.restfulapi.requests.Request;
import de.lukegoll.application.textextractor.AuftragDataExtractor;
import de.lukegoll.application.xml.xmlTranslator.XMLTranslator;
import de.lukegoll.vaadin.views.auftragsanlage.AuftragsanlageView;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import org.jboss.logging.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AuftragsVerarbeitungBean extends Thread {
    private final Logger logger = Logger.getLogger(AuftragsVerarbeitungBean.class);
    boolean runCondition = true;
    private boolean isAlive = true;
    ReceiveMailService receiveMailService = new ReceiveMailService();
    AtomicBoolean run = new AtomicBoolean(false);
    AtomicReference<List<String>> progressList = new AtomicReference<>();

    private final UI ui;
    private final AuftragsanlageView view;

    private int count = 0;

    public AuftragsVerarbeitungBean(UI ui, AuftragsanlageView view) {
        this.ui = ui;
        this.view = view;
    }


    public void run() {
        while (isAlive) {
            if (run.compareAndSet(false, true)) {
                List<String> stringList = new LinkedList<>();
                try {

                    receiveMailService.login("Entwicklung@gollenstede-entwicklung.de", "");
                    ListenableFuture<List<Message>> messageFuture = receiveMailService.downloadNewMails();
                    messageFuture.addCallback(
                            successResult -> view.updateUi(ui, LocalDateTime.now().toString() +" Mails heruntergeladen: " + successResult.size()),
                            failureResult -> view.updateUi(ui, LocalDateTime.now().toString() + " Fehler beim herunterladen der Mails:" + failureResult));
                    if (messageFuture.get().size() > 0) {
                        List<Message> messages = messageFuture.get();
                        ListenableFuture<List<Mail>> mailFuture = receiveMailService.extractAttachments(messages);
                        mailFuture.addCallback(
                                successResult -> view.updateUi(ui, LocalDateTime.now().toString() +" Dateien aus Mails extrahiert: " + successResult.size()),
                                failureResult -> view.updateUi(ui, LocalDateTime.now().toString() +" Fehler beim extrahieren der Dateien:" + failureResult));
                        ListenableFuture<List<Auftrag>> auftragFuture = startAuftragsService(mailFuture.get());
                        auftragFuture.addCallback(
                                successResult -> view.updateUi(ui, LocalDateTime.now().toString() +" Es wurden: " + successResult.size() + " Aufträge erstellt"),
                                failureResult -> view.updateUi(ui, LocalDateTime.now().toString() +" Fehler beim erstellen der Aufträge: " + failureResult));
                        ListenableFuture<List<String>> xmlFuture = new XMLTranslator().writeXmlRequests(auftragFuture.get());
                        xmlFuture.addCallback(
                                successResult -> view.updateUi(ui, LocalDateTime.now().toString() +" Es wurden: " + successResult.size() + " XML-Dateien erstellt!"),
                                failureResult -> view.updateUi(ui, LocalDateTime.now().toString() +" Fehler beim erstellen der XML-Dateien:" + failureResult));
                        for(int i=0; i<xmlFuture.get().size();i++){
                            ListenableFuture<String> restFuture = new Request().httpPost(xmlFuture.get().get(i),
                                    "https://intacc01-api.onrex.de/interfaces/orders", "ZDJhMGQzYWUtN2QyMy00YTMxLWI5YjMtM2MyMDJlMDAyM2EyOjA4YjE2MWQyLTEyZmQtNGEzMS1iNTM2LTlkMDAyM2I3N2RhNA==");
                            restFuture.addCallback(
                                    successResult -> view.updateUi(ui, LocalDateTime.now().toString() +" Übertragung an Dynarex-Server erfolgreich: " +successResult),
                                    failureResult -> view.updateUi(ui, LocalDateTime.now().toString() +" Fehler beim übertragen an Dynarex-Server: " + failureResult));
                        }
                        //receiveMailService.moveMails(mailFuture.get());
                    }
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    progressList.set(stringList);
                    run.set(false);
                    rest(5000);
                }
            }
        }
    }

    public boolean getAlive() {
        return isAlive;
    }

    synchronized void rest(long x) {
        try {
            this.wait(x);
        } catch (Exception e) {
        }
    }

    synchronized void wakeupToDie() {
        isAlive = false;
        this.notify();
    }

    @Async
    public ListenableFuture<List<Auftrag>> startAuftragsService(List<Mail> mail) {
        List<Auftrag> auftragList = new LinkedList<>();
        try {
            for (int i = 0; i < mail.size(); i++) {
                for (int j = 0; j < mail.get(i).getFiles().size(); j++) {
                    Auftrag auftrag = new Auftrag();
                    if (mail.get(i).getFiles().get(j).getFile().getName().contains("Aufnahmebogen")) {
                        AuftragDataExtractor auftragDataExtractor = new AuftragDataExtractor();
                        try {
                            auftrag = ((Auftrag) auftragDataExtractor.extractText(mail.get(i).getFiles().get(j).getFile()));
                            auftragList.add(
                                    auftrag
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            }
            return AsyncResult.forValue(auftragList);
        } catch (Exception e) {
            return AsyncResult.forExecutionException(e);
        }
    }

    public boolean isRunCondition() {
        return runCondition;
    }

    public void setRunCondition(boolean runCondition) {
        this.runCondition = runCondition;
    }

    public ReceiveMailService getReceiveMailService() {
        return receiveMailService;
    }

    public void setReceiveMailService(ReceiveMailService receiveMailService) {
        this.receiveMailService = receiveMailService;
    }

    public AtomicBoolean getRun() {
        return run;
    }

    public List<String> getProgressList() {
        return progressList.get();
    }


    public static void main(String[] args) {

    }
}
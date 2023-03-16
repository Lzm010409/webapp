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
import de.lukegoll.application.textextractor.AuftragDataExtractor;
import de.lukegoll.application.xml.xmlTranslator.XMLTranslator;
import de.lukegoll.vaadin.views.auftragsanlage.AuftragsanlageView;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class AuftragsVerarbeitungBean extends Thread {
    boolean runCondition = true;
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
        if (run.compareAndSet(false, true)) {
            List<String> stringList = new LinkedList<>();
            try {

                receiveMailService.login("Entwicklung@gollenstede-entwicklung.de", "");
                ListenableFuture<List<Mail>> mailFuture = receiveMailService.downloadNewMails();
                mailFuture.addCallback(
                        successResult -> ui.access(() -> view.getLoggerScreen().add(new Span("Task finished. Anzahl der heruntergeladenen Mails:" + successResult))),
                        failureResult -> ui.access(() -> view.getLoggerScreen().add(new Span("Task finished. Anzahl der heruntergeladenen Mails:" + failureResult))));
                if (mailFuture.get().size() > 0) {
                    ListenableFuture<List<Auftrag>> auftragFuture = startAuftragsService(mailFuture.get());
                    auftragFuture.addCallback(
                            successResult -> ui.access(() -> view.getLoggerScreen().add(new Span("Task finished. Anzahl der heruntergeladenen Mails:" + successResult))),
                            failureResult -> ui.access(() -> view.getLoggerScreen().add(new Span("Task finished. Anzahl der heruntergeladenen Mails:" + failureResult))));
                    ListenableFuture<List<String>> xmlFuture = new XMLTranslator().writeXmlRequests(auftragFuture.get());
                    auftragFuture.addCallback(
                            successResult -> ui.access(() -> view.getLoggerScreen().add(new Span("Task finished. Anzahl der heruntergeladenen Mails:" + successResult))),
                            failureResult -> ui.access(() -> view.getLoggerScreen().add(new Span("Task finished. Anzahl der heruntergeladenen Mails:" + failureResult))));
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

            }
        }
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

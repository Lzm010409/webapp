package de.lukegoll.application;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.mailService.Mail;
import de.lukegoll.application.mailService.empfänger.ReceiveMailService;
import de.lukegoll.application.textextractor.AuftragDataExtractor;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AuftragsAnlageService {
    boolean runCondition;

    @Async
    public ListenableFuture<Auftrag> startAuftragsService(Mail mail) {
        Auftrag auftrag = new Auftrag();
        try {
            for (int j = 0; j < mail.getFiles().size(); j++) {
                if (mail.getFiles().get(j).getFile().getName().contains("TEST")) {
                    AuftragDataExtractor auftragDataExtractor = new AuftragDataExtractor();
                    auftrag = ((Auftrag) auftragDataExtractor.extractText(mail.getFiles().get(j).getFile()));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AsyncResult.forValue(auftrag);
    }
}

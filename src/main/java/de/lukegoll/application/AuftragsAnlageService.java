package de.lukegoll.application;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.mailService.Mail;
import de.lukegoll.application.mailService.empfänger.ReceiveMail;
import de.lukegoll.application.textextractor.AuftragDataExtractor;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AuftragsAnlageService {
    boolean runCondition;

    public List<Auftrag> startAuftragsService() {

        ReceiveMail receiveMail = new ReceiveMail();
        List<Auftrag> auftragQueue = new LinkedList<>();
        try {
            receiveMail.login("", "");
            receiveMail.checkNewMessages();
            List<Mail> mailList = receiveMail.downloadNewMails();

            for (int i = 0; i < mailList.size(); i++) {
                for (int j = 0; j < mailList.get(i).getFiles().size(); j++) {
                    if (mailList.get(i).getFiles().get(j).getFile().getName().contains("TEST")) {
                        AuftragDataExtractor auftragDataExtractor = new AuftragDataExtractor();
                        auftragQueue.add((Auftrag) auftragDataExtractor.extractText(mailList.get(i).getFiles().get(j).getFile()));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return auftragQueue;
    }
}

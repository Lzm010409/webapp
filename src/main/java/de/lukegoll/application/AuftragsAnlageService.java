package de.lukegoll.application;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.mailService.Mail;
import de.lukegoll.application.mailService.empfänger.ReceiveMailService;
import de.lukegoll.application.textextractor.AuftragDataExtractor;
import de.lukegoll.application.xml.xmlTranslator.XMLTranslator;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AuftragsAnlageService {
    boolean runCondition = true;

    @Async
    public ListenableFuture<String> startAuftragsService() {

        ReceiveMailService receiveMailService = new ReceiveMailService();
        List<Mail> mail = new LinkedList<>();
        List<Auftrag> auftragList = new LinkedList<>();
        while (runCondition == true) {
            try {
                receiveMailService.login("", "");
                if (receiveMailService.checkNewMessages() == false) {
                    Thread.sleep(10000);
                } else {
                    mail = receiveMailService.downloadNewMails();
                }
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                for (int i = 0; i < auftragList.size(); i++) {
                    String filename = "auftrag" + i + ".xml";
                    new XMLTranslator().writeXmlFile(auftragList.get(i), new File(filename));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return AsyncResult.forValue("");
    }


    public static void main(String[] args) {
        new AuftragsAnlageService().startAuftragsService();
    }
}

package de.lukegoll.application.mailService.empfänger;


import com.sun.mail.imap.IMAPStore;
import de.lukegoll.application.mailService.Anhänge;
import de.lukegoll.application.mailService.Mail;
import de.lukegoll.application.mailService.ServerData;
import jakarta.mail.*;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@Component
public class ReceiveMailService {

    protected Store imapStore;
    private final Logger logger = Logger.getLogger(ReceiveMailService.class);


    public void login(String username, String password) throws MessagingException {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.port", ServerData.IMAPPORT.getData());
            properties.put("mail.imap.starttls.enable", "true");
            Session mailSession = Session.getDefaultInstance(properties);
            Store store = mailSession.getStore("imaps");
            store.connect(ServerData.IMAPHOST.getData(), username, password);
            this.imapStore = store;

        } catch (Exception e) {
            logger.log(Logger.Level.WARN, "Es ist folgender Fehler beim einloggen in Ihren Mail Account passiert:"
                    + e.getMessage());

        }

    }

    public boolean checkNewMessages() throws MessagingException, IOException {
        if (imapStore == null) {
            throw new IllegalStateException("Zuerst einloggen!");
        }
        Folder mailFolder = imapStore.getFolder("INBOX");
        mailFolder.open(Folder.READ_ONLY);

        System.out.println("Es sind " + mailFolder.getUnreadMessageCount() + " ungelesen");

        if (mailFolder.getUnreadMessageCount() != 0) {
            return true;
        } else {
            return false;
        }

    }

    @Async
    public ListenableFuture<List<Message>> downloadNewMails() throws MessagingException {
        if (imapStore == null) {
            throw new IllegalStateException("Zuerst einloggen!");
        }
        List<Mail> mails = new LinkedList<>();
        try {
            Folder mailFolder = imapStore.getFolder("INBOX");
            mailFolder.open(Folder.READ_ONLY);
            Message[] messages = mailFolder.getMessages();
            return AsyncResult.forValue(Arrays.asList(messages));
        } catch (
                NoSuchProviderException e) {
            e.printStackTrace();
            return AsyncResult.forExecutionException(e);
        } catch (
                MessagingException e) {
            e.printStackTrace();
            return AsyncResult.forExecutionException(e);
        } catch (
                Exception e) {
            e.printStackTrace();
            return AsyncResult.forExecutionException(e);
        }


    }

    public ListenableFuture<List<Mail>> extractAttachments(List<Message> messages) {
        List<Mail> mails = new LinkedList<>();
        try {
            for (int i = 0; i < messages.size(); i++) {
                Message message = messages.get(i);
                Mail mail = new Mail();
                Address[] toAddress =
                        message.getRecipients(Message.RecipientType.TO);
                mail.setBetreff(message.getSubject());
                mail.setSender(message.getFrom()[0].toString());
                logger.log(Logger.Level.INFO, "Details of Email Message "
                        + (i + 1) + " :" + "Subject: " + message.getSubject() + "From: " + message.getFrom()[0]
                );
                List<String> empfänger = new LinkedList<>();
                for (int j = 0; j < toAddress.length; j++) {
                    System.out.println(toAddress[j].toString());
                    empfänger.add(toAddress[j].toString());
                }
                mail.setEmpfänger(empfänger);
                Multipart multipart = (Multipart) message.getContent();
                List<Anhänge> anhängeList = new LinkedList<>();
                for (int k = 0; k < multipart.getCount(); k++) {


                    BodyPart bodyPart = multipart.getBodyPart(k);
                    int number = (int) (Math.random() * 100);
                    String filePath = String.format("/Users/lukegollenstede/Desktop/TEST/Files/%s-%d.\\%s", bodyPart.getFileName(), number, "pdf");
                    if (bodyPart.getFileName() == null) {
                        continue;
                    }
                    InputStream stream =
                            (InputStream) bodyPart.getInputStream();
                    byte[] bytes = stream.readAllBytes();
                    File targetFile = new File(filePath);
                    OutputStream outputStream = new FileOutputStream(targetFile);
                    outputStream.write(bytes);
                    Anhänge anhänge = new Anhänge();
                    anhänge.setFile(targetFile);
                    anhänge.setTitle(bodyPart.getFileName());
                    anhängeList.add(anhänge);
                    System.out.println(bodyPart.getFileName());
                    logger.log(Logger.Level.INFO, "Attachment: " + k + ", " + bodyPart.getFileName()
                    );
                }
                if (anhängeList.size() != 0) {
                    mail.setFiles(anhängeList);
                    mails.add(mail);
                }

            }
            return AsyncResult.forValue(mails);
        } catch (
                Exception e) {
            return AsyncResult.forExecutionException(e);
        }

    }

    public ListenableFuture<String> moveMails(List<Mail> mailList) {
        if (imapStore == null) {
            throw new IllegalStateException("Zuerst einloggen!");
        }
        try {
            Folder verarbeitet = imapStore.getFolder("Verarbeitet");
            Folder mailFolder = imapStore.getFolder("INBOX");
            mailFolder.open(Folder.READ_WRITE);
            Message[] messages = mailFolder.getMessages();
            mailFolder.copyMessages(messages, verarbeitet);
            mailFolder.setFlags(messages, new Flags(Flags.Flag.DELETED), true);
            mailFolder.expunge();
            imapStore.close();
            return AsyncResult.forValue("Alle Mails erfolreich in den Papierkorb verschoben!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    public Store getImapStore() {
        return imapStore;
    }

    public void setImapStore(IMAPStore imapStore) {
        this.imapStore = imapStore;
    }

    public Logger getLogger() {
        return logger;
    }
}

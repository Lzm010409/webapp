package de.lukegoll.application.beans;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.data.enums.AuftragStatus;
import de.lukegoll.application.data.service.AuftragService;
import de.lukegoll.application.data.service.FahrzeugService;
import de.lukegoll.application.data.service.KontaktService;
import de.lukegoll.application.logWriter.LogWriter;
import de.lukegoll.application.mailService.Mail;
import de.lukegoll.application.mailService.empfänger.ReceiveMailService;
import de.lukegoll.application.pdf.PdfEditor;
import de.lukegoll.application.restfulapi.requests.Request;
import de.lukegoll.application.textextractor.AuftragDataExtractor;
import de.lukegoll.application.xml.xmlTranslator.XMLTranslator;
import de.lukegoll.vaadin.views.auftragsanlage.AuftragsanlageView;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class AuftragsVerarbeitungBean extends Thread {
    private final Logger logger = Logger.getLogger(AuftragsVerarbeitungBean.class);
    boolean runCondition = true;
    private boolean isAlive = true;
    ReceiveMailService receiveMailService;
    AtomicBoolean run = new AtomicBoolean(false);
    AtomicReference<List<String>> progressList = new AtomicReference<>();
    List<Message> messageFuture = new LinkedList<Message>();
    @Value("${spring.mail.username}")
    private String userName;

    @Value("${spring.mail.password}")
    private String userPassword;

    @Value("${dynarex.server}")
    private String dynServerData;

    @Value("${dynarex.token}")
    private String dynToken;


    private int count = 0;

    AuftragService auftragService;
    FahrzeugService fahrzeugService;
    KontaktService kontaktService;

    @Autowired
    public AuftragsVerarbeitungBean(AuftragService auftragService, FahrzeugService fahrzeugService, KontaktService kontaktService, ReceiveMailService receiveMailService) {
        this.auftragService = auftragService;
        this.receiveMailService = receiveMailService;
        this.fahrzeugService = fahrzeugService;
        this.kontaktService = kontaktService;

    }

    @Scheduled(fixedDelay = 10000)
    public void run() {
        String aufnahmebogenPath = "";
        String abtretungsPath = "";
        try {
            receiveMailService.login(userName, userPassword);
            messageFuture = receiveMailService.downloadNewMails();
            if (messageFuture.size() > 0) {
                List<Mail> mailFuture = receiveMailService.extractAttachments(messageFuture);
                receiveMailService.moveMails(messageFuture);
                for (int i = 0; i < messageFuture.size(); i++) {
                    for (int j = 0; j < mailFuture.get(i).getFiles().size(); j++) {
                        if (mailFuture.get(i).getFiles().get(j).getFile().getName().contains("Abtretung")) {
                            abtretungsPath = mailFuture.get(i).getFiles().get(j).getFile().getAbsolutePath();
                        }
                        if (mailFuture.get(i).getFiles().get(j).getFile().getName().contains("Aufnahmebogen")) {
                            aufnahmebogenPath = mailFuture.get(i).getFiles().get(j).getFile().getAbsolutePath();
                        }
                    }
                    Auftrag auftragFuture = startAuftragsService(mailFuture.get(i));
                    auftragFuture.setData(new PdfEditor().generateAbtretungAsByteArray(auftragFuture, abtretungsPath));
                    ListenableFuture<Auftrag> restFuture = new Request().httpPostAuftrag(auftragFuture,
                            dynServerData, dynToken);
                    Auftrag auftrag = restFuture.get();
                    fahrzeugService.saveFahrzeug(auftrag.getFahrzeug());
                    if (auftrag.getKontakte() instanceof Set<Kontakt>) {
                        Object[] kontaktArray = auftrag.getKontakte().toArray();
                        for (int j = 0; j < kontaktArray.length; j++) {
                            if (kontaktArray[j] instanceof Kontakt)
                                kontaktService.saveKontakt((Kontakt) kontaktArray[j]);
                        }
                    }
                    auftragService.saveAuftrag(auftrag);
                    auftrag.setAuftragStatus(AuftragStatus.VERARBEITET);
                    auftragService.update(auftrag);
                    cleanFileDirectory(mailFuture.get(i));
                }
            }
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

    }


    public void cleanFileDirectory(Mail mail) {
        for (int i = 0; i < mail.getFiles().size(); i++) {
            File file = mail.getFiles().get(i).getFile();
            file.delete();
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


    public Auftrag startAuftragsService(Mail mail) {
        Auftrag auftrag = new Auftrag();
        try {
            for (int j = 0; j < mail.getFiles().size(); j++) {
                if (mail.getFiles().get(j).getFile().getName().contains("Aufnahmebogen")) {
                    AuftragDataExtractor auftragDataExtractor = new AuftragDataExtractor(auftragService, fahrzeugService, kontaktService);
                    File file = mail.getFiles().get(j).getFile();
                    if (fileHasFormularFields(file)) {
                        logger.log(Logger.Level.INFO, "Formular wird ausgelesen...");
                        auftrag = auftragDataExtractor.extractTextFromFormular(file);

                    } else {
                        auftrag = ((Auftrag) auftragDataExtractor.extractText(file));
                    }
                }

            }

            return auftrag;
        } catch (IOException e) {
            return null;
        }
    }


    public boolean fileHasFormularFields(File file) {
        try {
            PdfReader pdfReader = new PdfReader(file);
            PdfDocument doc = new PdfDocument(pdfReader);
            PdfAcroForm pdfAcroForm = PdfAcroForm.getAcroForm(doc, false);
            if (pdfAcroForm != null) {
                Map<String, PdfFormField> pdfFormFieldMap = pdfAcroForm.getFormFields();
                if (pdfFormFieldMap.size() > 50) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (IOException e) {
            return false;

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

    @Autowired
    public void setReceiveMailService(ReceiveMailService receiveMailService) {
        this.receiveMailService = receiveMailService;
    }

    public AtomicBoolean getRun() {
        return run;
    }

    public List<String> getProgressList() {
        return progressList.get();
    }

    public Logger getLogger() {
        return logger;
    }

    public void setRun(AtomicBoolean run) {
        this.run = run;
    }

    public void setProgressList(AtomicReference<List<String>> progressList) {
        this.progressList = progressList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AuftragService getAuftragService() {
        return auftragService;
    }

    public void setAuftragService(AuftragService auftragService) {
        this.auftragService = auftragService;
    }

    public FahrzeugService getFahrzeugService() {
        return fahrzeugService;
    }

    public void setFahrzeugService(FahrzeugService fahrzeugService) {
        this.fahrzeugService = fahrzeugService;
    }

    public KontaktService getKontaktService() {
        return kontaktService;
    }

    public void setKontaktService(KontaktService kontaktService) {
        this.kontaktService = kontaktService;
    }


    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public String getDynServerData() {
        return dynServerData;
    }

    public void setDynServerData(String dynServerData) {
        this.dynServerData = dynServerData;
    }

    public String getDynToken() {
        return dynToken;
    }

    public void setDynToken(String dynToken) {
        this.dynToken = dynToken;
    }
}

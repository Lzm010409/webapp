package de.lukegoll.application.beans;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.data.enums.AuftragStatus;
import de.lukegoll.application.data.service.AuftragService;
import de.lukegoll.application.data.service.FahrzeugService;
import de.lukegoll.application.data.service.KontaktService;
import de.lukegoll.application.restfulapi.requests.Request;
import de.lukegoll.vaadin.views.auftragsübersicht.AuftragsübersichtView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class ReprocessBean {
    AuftragService auftragService;
    FahrzeugService fahrzeugService;
    KontaktService kontaktService;

    private UI ui;
    private AuftragsübersichtView auftragsübersichtView;
    @Value("${dynarex.server}")
    private String dynServerData;

    @Value("${dynarex.token}")
    private String dynToken;

    @Autowired
    public ReprocessBean(AuftragService auftragService, FahrzeugService fahrzeugService, KontaktService kontaktService) {
        this.auftragService = auftragService;
        this.fahrzeugService = fahrzeugService;
        this.kontaktService = kontaktService;
    }

    public boolean reprocessAuftrag(Auftrag auftrag, UI ui, AuftragsübersichtView auftragsübersichtView) {
        setUi(ui);
        setAuftragsübersichtView(auftragsübersichtView);
        try {
            ui.access(() -> {
                auftragsübersichtView.generateNotifications("Auftrag wird noch einmal versendet!", AuftragStatus.INBEARBEITUNG);
            });
            ListenableFuture<Auftrag> restFuture = new Request().httpPostAuftrag(auftrag,
                    dynServerData, dynToken);
            restFuture.addCallback(
                    successResult -> auftragsübersichtView.generateNotifications("Auftrag wurde erfolgreich versendet!", AuftragStatus.VERARBEITET),
                    failureResult -> auftragsübersichtView.generateNotifications(failureResult.toString(), AuftragStatus.RESTFEHLER));
            auftrag = restFuture.get();
            fahrzeugService.update(auftrag.getFahrzeug());
            if (auftrag.getKontakte() instanceof Set<Kontakt>) {
                Object[] kontaktArray = auftrag.getKontakte().toArray();
                for (int j = 0; j < kontaktArray.length; j++) {
                    if (kontaktArray[j] instanceof Kontakt)
                        kontaktService.update((Kontakt) kontaktArray[j]);
                }
            }
            auftragService.update(auftrag);
            auftrag.setAuftragStatus(AuftragStatus.VERARBEITET);
            auftragService.update(auftrag);

            return true;
        } catch (Exception e) {

            return false;
        }
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

    public UI getUi() {
        return ui;
    }

    public void setUi(UI ui) {
        this.ui = ui;
    }

    public void setAuftragsübersichtView(AuftragsübersichtView auftragsübersichtView) {
        this.auftragsübersichtView = auftragsübersichtView;
    }

    public AuftragsübersichtView getAuftragsübersichtView() {
        return auftragsübersichtView;
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

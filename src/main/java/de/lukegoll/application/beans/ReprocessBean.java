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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ReprocessBean {
    AuftragService auftragService;
    FahrzeugService fahrzeugService;
    KontaktService kontaktService;

    private final UI ui;
    private final AuftragsübersichtView auftragsübersichtView;
    @Value("${dynarex.server}")
    private String dynServerData;

    @Value("${dynarex.token}")
    private String dynToken;

    public ReprocessBean(UI ui, AuftragsübersichtView auftragsübersichtView, AuftragService auftragService, FahrzeugService fahrzeugService, KontaktService kontaktService) {
        this.ui = ui;
        this.auftragsübersichtView = auftragsübersichtView;
        this.auftragService = auftragService;
        this.fahrzeugService = fahrzeugService;
        this.kontaktService = kontaktService;
    }

    public boolean reprocessAuftrag(Auftrag auftrag) {
        try {
            ui.access(() -> {
               auftragsübersichtView.generateNotifications("Auftrag wird noch einmal versendet!", AuftragStatus.INBEARBEITUNG);
            });
            ListenableFuture<Auftrag> restFuture = new Request().httpPostAuftrag(auftrag,
                    dynServerData, dynToken);
            restFuture.addCallback(
                    successResult -> auftragsübersichtView.generateNotifications("Auftrag wurde erfolgreich versendet!", AuftragStatus.VERARBEITET),
                    failureResult -> auftragsübersichtView.generateNotifications(failureResult.toString(), AuftragStatus.RESTFEHLER));
            auftrag=restFuture.get();
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
}

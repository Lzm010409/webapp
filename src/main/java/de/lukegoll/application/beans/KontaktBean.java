package de.lukegoll.application.beans;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.data.service.KontaktService;
import de.lukegoll.application.xml.xmlEntities.constants.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Component
public class KontaktBean extends VerticalLayout {

    KontaktService kontaktService;

    @Autowired
    public KontaktBean(KontaktService kontaktService) {
        this.kontaktService = kontaktService;
    }


    public Kontakt fetchKunde(String vName, String nName) {
        try {
            List<Kontakt> kontakts = kontaktService.findKontakt(vName, nName, PersonType.KUNDE);
            if (kontakts.size() == 1) {
                return kontakts.get(0);
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Kontakt fetchVersicherung(String vName, String nName) {
        try {
            List<Kontakt> kontakts = kontaktService.findKontakt(vName, nName, PersonType.VERSICHERUNG);
            if (kontakts.size() == 1) {
                return kontakts.get(0);
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Kontakt fetchRechtsanwalt(String vName, String nName) {
        try {
            List<Kontakt> kontakts = kontaktService.findKontakt(vName, nName, PersonType.RECHTSANWALT);
            if (kontakts.size() == 1) {
                return kontakts.get(0);
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }
}

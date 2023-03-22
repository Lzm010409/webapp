package de.lukegoll.application.data.entity.persons;

import de.lukegoll.application.xml.xmlEntities.constants.PersonType;

public class Kunde extends Kontakt {

    public Kunde() {
        setPersonType(PersonType.KUNDE);
    }

    public Kontakt convertToKontakt(Kunde kunde) {
        Kontakt kontakt = new Kontakt();
        kontakt.setId(kunde.getId());
        kontakt.setAnrede(kunde.getAnrede());
        kontakt.setvName(kunde.getvName());
        kontakt.setnName(kunde.getnName());
        kontakt.setAdresse(kunde.getAdresse());
        kontakt.setPlz(kunde.getPlz());
        kontakt.setStadt(kunde.getStadt());
        kontakt.setTel(kunde.getTel());
        kontakt.setMail(kunde.getMail());
        kontakt.setAuftragList(kunde.getAuftragList());
        return kontakt;
    }
}

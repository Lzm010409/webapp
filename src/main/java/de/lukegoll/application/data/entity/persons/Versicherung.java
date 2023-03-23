package de.lukegoll.application.data.entity.persons;

import de.lukegoll.application.xml.xmlEntities.constants.PersonType;

public class Versicherung extends Kontakt {

    public Versicherung() {
        setPersonType(PersonType.RECHTSANWALT);
    }

    public Kontakt convertToKontakt(Versicherung versicherung) {
        Kontakt kontakt = new Kontakt();
        kontakt.setId(versicherung.getId());
        kontakt.setVersion(versicherung.getVersion());
        kontakt.setAnrede(versicherung.getAnrede());
        kontakt.setvName(versicherung.getvName());
        kontakt.setnName(versicherung.getnName());
        kontakt.setAdresse(versicherung.getAdresse());
        kontakt.setPlz(versicherung.getPlz());
        kontakt.setStadt(versicherung.getStadt());
        kontakt.setTel(versicherung.getTel());
        kontakt.setMail(versicherung.getMail());
        kontakt.setAuftragList(versicherung.getAuftragList());
        kontakt.setPersonType(PersonType.VERSICHERUNG);
        return kontakt;
    }
}

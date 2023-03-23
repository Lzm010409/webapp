package de.lukegoll.application.data.entity.persons;

import de.lukegoll.application.xml.xmlEntities.constants.PersonType;

public class Rechtsanwalt extends Kontakt {

    public Rechtsanwalt() {
        setPersonType(PersonType.RECHTSANWALT);
    }

    public Kontakt convertToKontakt(Rechtsanwalt rechtsanwalt) {
        Kontakt kontakt = new Kontakt();
        kontakt.setId(rechtsanwalt.getId());
        kontakt.setVersion(rechtsanwalt.getVersion());
        kontakt.setAnrede(rechtsanwalt.getAnrede());
        kontakt.setvName(rechtsanwalt.getvName());
        kontakt.setnName(rechtsanwalt.getnName());
        kontakt.setAdresse(rechtsanwalt.getAdresse());
        kontakt.setPlz(rechtsanwalt.getPlz());
        kontakt.setStadt(rechtsanwalt.getStadt());
        kontakt.setTel(rechtsanwalt.getTel());
        kontakt.setMail(rechtsanwalt.getMail());
        kontakt.setAuftragList(rechtsanwalt.getAuftragList());
        kontakt.setPersonType(PersonType.RECHTSANWALT);
        return kontakt;
    }
}

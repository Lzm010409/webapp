package de.lukegoll.application.data.entity.persons;


import de.lukegoll.application.data.entity.AbstractEntity;
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.xml.xmlEntities.constants.PersonType;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Kontakt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    // The initial value is to account for data.sql demo data ids
    @SequenceGenerator(name = "idgenerator", initialValue = 1000)
    private Long id;
    private String anrede;
    private String vName;
    private String nName;

    private String adresse;
    private String plz;
    private String stadt;
    private String tel;
    private String mail;
    private PersonType personType;
    @Version
    private int version;
    @ManyToMany(
            mappedBy = "kontakte"
    )
    private Set<Auftrag> auftragList;

    public Kontakt(String anrede, String vName, String nName, String adresse, String plz, String stadt, String tel, String mail, PersonType personType) {
        this.anrede = anrede;
        this.vName = vName;
        this.nName = nName;
        this.adresse = adresse;
        this.plz = plz;
        this.stadt = stadt;
        this.tel = tel;
        this.mail = mail;
        this.personType = personType;
    }

    public Kontakt() {

    }

    public Kunde convertToKunde(Kontakt kontakt) {
        Kunde kunde = new Kunde();
        kunde.setId(kontakt.getId());
        kunde.setVersion(kontakt.getVersion());
        kunde.setvName(kontakt.getvName());
        kunde.setnName(kontakt.getnName());
        kunde.setAnrede(kontakt.getAnrede());
        kunde.setAdresse(kontakt.getAdresse());
        kunde.setPlz(kontakt.getPlz());
        kunde.setStadt(kontakt.getStadt());
        kunde.setTel(kontakt.getTel());
        kunde.setMail(kontakt.getMail());
        kunde.setAuftragList(kontakt.getAuftragList());
        kunde.setPersonType(PersonType.KUNDE);
        return kunde;
    }

    public Rechtsanwalt convertToRechtsanwalt(Kontakt kontakt) {
        Rechtsanwalt rechtsanwalt = new Rechtsanwalt();
        rechtsanwalt.setId(kontakt.getId());
        rechtsanwalt.setVersion(kontakt.getVersion());
        rechtsanwalt.setvName(kontakt.getvName());
        rechtsanwalt.setnName(kontakt.getnName());
        rechtsanwalt.setAnrede(kontakt.getAnrede());
        rechtsanwalt.setAdresse(kontakt.getAdresse());
        rechtsanwalt.setPlz(kontakt.getPlz());
        rechtsanwalt.setStadt(kontakt.getStadt());
        rechtsanwalt.setTel(kontakt.getTel());
        rechtsanwalt.setMail(kontakt.getMail());
        rechtsanwalt.setAuftragList(kontakt.getAuftragList());
        rechtsanwalt.setPersonType(PersonType.RECHTSANWALT);
        return rechtsanwalt;
    }

    public Versicherung convertToVersicherung(Kontakt kontakt) {
        Versicherung versicherung = new Versicherung();
        versicherung.setId(kontakt.getId());
        versicherung.setVersion(kontakt.getVersion());
        versicherung.setvName(kontakt.getvName());
        versicherung.setnName(kontakt.getnName());
        versicherung.setAnrede(kontakt.getAnrede());
        versicherung.setAdresse(kontakt.getAdresse());
        versicherung.setPlz(kontakt.getPlz());
        versicherung.setStadt(kontakt.getStadt());
        versicherung.setTel(kontakt.getTel());
        versicherung.setMail(kontakt.getMail());
        versicherung.setAuftragList(kontakt.getAuftragList());
        versicherung.setPersonType(PersonType.VERSICHERUNG);
        return versicherung;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public Set<Auftrag> getAuftragList() {
        return auftragList;
    }

    public void setAuftragList(Set<Auftrag> auftragList) {
        this.auftragList = auftragList;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

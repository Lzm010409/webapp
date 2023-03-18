package de.lukegoll.application.data.entity.persons;


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
    @Column(name = "kontaktId")
    private Long Id;
    private String anrede;
    private String vName;
    private String nName;

    private String adresse;
    private String plz;
    private String stadt;
    private String tel;
    private String mail;
    private PersonType personType = PersonType.KUNDE;
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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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
}

package de.lukegoll.application.data.entity.persons;


import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.xmlEntities.constants.PersonType;

import javax.persistence.*;
import java.util.List;

@Entity
public class Versicherung extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    // The initial value is to account for data.sql demo data ids
    @SequenceGenerator(name = "idgenerator", initialValue = 1000)
    private Long Id;
    private PersonType personType = PersonType.VERSICHERUNG;

    @OneToMany(mappedBy = "versicherung")
    private List<Auftrag> auftragList;

    public Versicherung(String anrede, String vName, String nName, String adresse, String plz, String stadt, String tel, String mail) {
        super(anrede, vName, nName, adresse, plz, stadt, tel, mail);
    }

    public Versicherung() {
    }

    public Long getVersicherungId() {
        return Id;
    }

    public void setVersicherungId(Long id) {
        this.Id = id;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public List<Auftrag> getAuftragList() {
        return auftragList;
    }

    public void setAuftragList(List<Auftrag> auftragList) {
        this.auftragList = auftragList;
    }
}

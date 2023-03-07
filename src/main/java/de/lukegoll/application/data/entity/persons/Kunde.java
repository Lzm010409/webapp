package de.lukegoll.application.data.entity.persons;


import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.xml.xmlEntities.constants.PersonType;

import javax.persistence.*;
import java.util.List;

@Entity
public class Kunde extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    // The initial value is to account for data.sql demo data ids
    @SequenceGenerator(name = "idgenerator", initialValue = 1000)
    @Column(name = "kundenId")
    private Long Id;
    private PersonType personType = PersonType.KUNDE;

    @OneToMany(mappedBy = "kunde")
    private List<Auftrag> auftragList;

    public Kunde(String anrede, String vName, String nName, String adresse, String plz, String stadt, String tel, String mail) {
        super(anrede, vName, nName, adresse, plz, stadt, tel, mail);
    }

    public Kunde() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
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

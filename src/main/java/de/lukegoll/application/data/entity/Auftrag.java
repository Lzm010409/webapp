package de.lukegoll.application.data.entity;


import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.data.enums.AuftragStatus;

import javax.persistence.*;
import java.sql.Blob;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "auftrag")
public class Auftrag extends AbstractEntity {
    private String auftragsDatum;
    private String schadenDatum;
    private String schadenOrt;
    private String gutachtenNummer;
    private String Schadennummer;
    private String kennzeichenUG;
    private String besichtigungsort;
    private String besichtigungsDatum;
    private String besichtigungsUhrzeit;
    private String schadenhergang;
    private String auftragsBesonderheiten;

    private AuftragStatus auftragStatus;
    @ManyToMany
    @JoinTable(
            name = "kontakte",
            joinColumns = {@JoinColumn(name = "kontakt_id")},
            inverseJoinColumns = {@JoinColumn(name = "auftrag_id")}
    )
    private Set<Kontakt> kontakte = new HashSet<>();

    @Transient
    private List<Kontakt> kontaktList = new LinkedList<>();
    @ManyToOne
    @JoinColumn(name = "fahrzeug", nullable = true)
    private Fahrzeug fahrzeug;

    @Lob
    private Blob data;

    public Auftrag() {
        auftragStatus = AuftragStatus.OFFEN;
    }

    public String getAuftragsDatum() {
        return auftragsDatum;
    }

    public void setAuftragsDatum(String auftragsDatum) {
        this.auftragsDatum = auftragsDatum;
    }

    public String getSchadenDatum() {
        return schadenDatum;
    }

    public void setSchadenDatum(String schadenDatum) {
        this.schadenDatum = schadenDatum;
    }

    public String getSchadenOrt() {
        return schadenOrt;
    }

    public void setSchadenOrt(String schadenOrt) {
        this.schadenOrt = schadenOrt;
    }

    public String getGutachtenNummer() {
        return gutachtenNummer;
    }

    public void setGutachtenNummer(String gutachtenNummer) {
        this.gutachtenNummer = gutachtenNummer;
    }

    public String getSchadennummer() {
        return Schadennummer;
    }

    public void setSchadennummer(String schadennummer) {
        Schadennummer = schadennummer;
    }


    public String getKennzeichenUG() {
        return kennzeichenUG;
    }

    public void setKennzeichenUG(String kennzeichenUG) {
        this.kennzeichenUG = kennzeichenUG;
    }


    public String getBesichtigungsort() {
        return besichtigungsort;
    }

    public void setBesichtigungsort(String besichtigungsort) {
        this.besichtigungsort = besichtigungsort;
    }

    public String getBesichtigungsDatum() {
        return besichtigungsDatum;
    }

    public void setBesichtigungsDatum(String bedichtigungsDatum) {
        this.besichtigungsDatum = bedichtigungsDatum;
    }

    public String getBesichtigungsUhrzeit() {
        return besichtigungsUhrzeit;
    }

    public void setBesichtigungsUhrzeit(String besichtigungsUhrzeit) {
        this.besichtigungsUhrzeit = besichtigungsUhrzeit;
    }

    public String getSchadenhergang() {
        return schadenhergang;
    }

    public void setSchadenhergang(String schadenhergang) {
        this.schadenhergang = schadenhergang;
    }

    public String getAuftragsBesonderheiten() {
        return auftragsBesonderheiten;
    }

    public void setAuftragsBesonderheiten(String auftragsBesonderheiten) {
        this.auftragsBesonderheiten = auftragsBesonderheiten;
    }


    public Fahrzeug getFahrzeug() {
        return fahrzeug;
    }

    public void setFahrzeug(Fahrzeug fahrzeug) {
        this.fahrzeug = fahrzeug;
    }

    public Set<Kontakt> getKontakte() {
        return kontakte;
    }

    public void setKontakte(Set<Kontakt> kontakte) {
        this.kontakte = kontakte;
    }

    public List<Kontakt> getKontaktList() {
        return kontaktList;
    }

    public void setKontaktList(List<Kontakt> kontaktList) {
        this.kontaktList = kontaktList;
    }

    public AuftragStatus getAuftragStatus() {
        return auftragStatus;
    }

    public void setAuftragStatus(AuftragStatus auftragStatus) {
        this.auftragStatus = auftragStatus;
    }

    public Blob getData() {
        return data;
    }

    public void setData(Blob data) {
        this.data = data;
    }

    private List<Kontakt> generateKontaktList(Set<Kontakt> kontaktSet) {
        List<Kontakt> kontakts = new LinkedList<>();
        Object[] objects = kontaktSet.toArray();
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof Kontakt) {
                kontakts.add(
                        (Kontakt) objects[i]
                );
            }
        }
        return kontakts;
    }

}

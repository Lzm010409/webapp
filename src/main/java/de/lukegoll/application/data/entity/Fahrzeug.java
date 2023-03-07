package de.lukegoll.application.data.entity;


import de.lukegoll.application.xml.xmlEntities.constants.DriveTrain;
import de.lukegoll.application.xml.xmlEntities.constants.Fuel;
import de.lukegoll.application.xml.xmlEntities.constants.VehicleColor;
import de.lukegoll.application.xml.xmlEntities.constants.VehicleRating;

import javax.persistence.*;
import java.util.List;

@Entity
public class Fahrzeug extends AbstractEntity{

    private String amtlKennzeichen;
    private String fahrzeugArt;
    private String hersteller;
    private String typ;
    private String hsntsn;
    private String fin;
    private String erstZulassung;
    private String letztZulassung;
    private int leistung;
    private int hubraum;
    private String hu;
    private int anzVorbesitzer;
    private int kmStand;
    private VehicleColor farbe;
    private String reifenVorne;
    private String reifenHinten;
    private String reifenHersteller;
    private String profilTiefe;
    private DriveTrain antriebsArt;
    private Fuel kraftstoff;
    private String schadstoffKlasse;
    private VehicleRating allgemein;
    private VehicleRating karosserie;
    private VehicleRating lack;

    private String ausstattung;
    @OneToMany(mappedBy = "fahrzeug")
    private List<Auftrag> auftragList;

    private String nichtBehSchäden;
    private String behSchäden;


    public Fahrzeug() {
    }

    public String getAmtlKennzeichen() {
        return amtlKennzeichen;
    }

    public void setAmtlKennzeichen(String amtlKennzeichen) {
        this.amtlKennzeichen = amtlKennzeichen;
    }

    public String getFahrzeugArt() {
        return fahrzeugArt;
    }

    public void setFahrzeugArt(String fahrzeugArt) {
        this.fahrzeugArt = fahrzeugArt;
    }

    public String getHersteller() {
        return hersteller;
    }

    public void setHersteller(String hersteller) {
        this.hersteller = hersteller;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getHsntsn() {
        return hsntsn;
    }

    public void setHsntsn(String hsntsn) {
        this.hsntsn = hsntsn;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getErstZulassung() {
        return erstZulassung;
    }

    public void setErstZulassung(String erstZulassung) {
        this.erstZulassung = erstZulassung;
    }

    public String getLetztZulassung() {
        return letztZulassung;
    }

    public void setLetztZulassung(String letztZulassung) {
        this.letztZulassung = letztZulassung;
    }

    public int getLeistung() {
        return leistung;
    }

    public void setLeistung(int leistung) {
        this.leistung = leistung;
    }

    public int getHubraum() {
        return hubraum;
    }

    public void setHubraum(int hubraum) {
        this.hubraum = hubraum;
    }

    public String getHu() {
        return hu;
    }

    public void setHu(String hu) {
        this.hu = hu;
    }

    public int getAnzVorbesitzer() {
        return anzVorbesitzer;
    }

    public void setAnzVorbesitzer(int anzVorbesitzer) {
        this.anzVorbesitzer = anzVorbesitzer;
    }

    public int getKmStand() {
        return kmStand;
    }

    public void setKmStand(int kmStand) {
        this.kmStand = kmStand;
    }

    public VehicleColor getFarbe() {
        return farbe;
    }

    public void setFarbe(VehicleColor farbe) {
        this.farbe = farbe;
    }

    public String getReifenVorne() {
        return reifenVorne;
    }

    public void setReifenVorne(String reifenVorne) {
        this.reifenVorne = reifenVorne;
    }

    public String getReifenHinten() {
        return reifenHinten;
    }

    public void setReifenHinten(String reifenHinten) {
        this.reifenHinten = reifenHinten;
    }

    public String getReifenHersteller() {
        return reifenHersteller;
    }

    public void setReifenHersteller(String reifenHersteller) {
        this.reifenHersteller = reifenHersteller;
    }

    public String getProfilTiefe() {
        return profilTiefe;
    }

    public void setProfilTiefe(String profilTiefe) {
        this.profilTiefe = profilTiefe;
    }

    public DriveTrain getAntriebsArt() {
        return antriebsArt;
    }

    public void setAntriebsArt(DriveTrain antriebsArt) {
        this.antriebsArt = antriebsArt;
    }

    public Fuel getKraftstoff() {
        return kraftstoff;
    }

    public void setKraftstoff(Fuel kraftstoff) {
        this.kraftstoff = kraftstoff;
    }

    public String getSchadstoffKlasse() {
        return schadstoffKlasse;
    }

    public void setSchadstoffKlasse(String schadstoffKlasse) {
        this.schadstoffKlasse = schadstoffKlasse;
    }

    public VehicleRating getAllgemein() {
        return allgemein;
    }

    public void setAllgemein(VehicleRating allgemein) {
        this.allgemein = allgemein;
    }

    public VehicleRating getKarosserie() {
        return karosserie;
    }

    public void setKarosserie(VehicleRating karosserie) {
        this.karosserie = karosserie;
    }

    public VehicleRating getLack() {
        return lack;
    }

    public void setLack(VehicleRating lack) {
        this.lack = lack;
    }

    public String getAusstattung() {
        return ausstattung;
    }

    public void setAusstattung(String ausstattung) {
        this.ausstattung = ausstattung;
    }

    public String getNichtBehSchäden() {
        return nichtBehSchäden;
    }

    public void setNichtBehSchäden(String nichtBehSchäden) {
        this.nichtBehSchäden = nichtBehSchäden;
    }

    public String getBehSchäden() {
        return behSchäden;
    }

    public void setBehSchäden(String behSchäden) {
        this.behSchäden = behSchäden;
    }

    public List<Auftrag> getAuftragList() {
        return auftragList;
    }

    public void setAuftragList(List<Auftrag> auftragList) {
        this.auftragList = auftragList;
    }
}

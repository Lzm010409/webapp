package de.lukegoll.application.data.entity.persons;


import javax.persistence.*;

@MappedSuperclass
public class Person {

    private String anrede;
    private String vName;
    private String nName;

    private String adresse;
    private String plz;
    private String stadt;
    private String tel;
    private String mail;

    public Person(String anrede, String vName, String nName, String adresse, String plz, String stadt, String tel, String mail) {
        this.anrede = anrede;
        this.vName = vName;
        this.nName = nName;
        this.adresse=adresse;
        this.plz=plz;
        this.stadt=stadt;
        this.tel = tel;
        this.mail = mail;
    }

    public Person() {

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




}

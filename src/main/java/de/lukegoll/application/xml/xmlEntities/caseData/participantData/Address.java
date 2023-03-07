package de.lukegoll.application.xml.xmlEntities.caseData.participantData;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Address {

    private java.lang.String street;
    private java.lang.String number;
    private java.lang.String postalcode;
    private java.lang.String city;
    private Country country;
    private Contacts contacts;

    public Address(){

    }

    public Address(java.lang.String street, java.lang.String number, java.lang.String postalcode, java.lang.String city, Country country, Contacts contacts) {
        this.street = street;
        this.number = number;
        this.postalcode = postalcode;
        this.city = city;
        this.country = country;
        this.contacts = contacts;
    }
    @XmlElement
    public java.lang.String getStreet() {
        return street;
    }

    public void setStreet(java.lang.String street) {
        this.street = street;
    }
    @XmlElement
    public java.lang.String getNumber() {
        return number;
    }

    public void setNumber(java.lang.String number) {
        this.number = number;
    }
    @XmlElement
    public java.lang.String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(java.lang.String postalcode) {
        this.postalcode = postalcode;
    }
    @XmlElement
    public java.lang.String getCity() {
        return city;
    }

    public void setCity(java.lang.String city) {
        this.city = city;
    }
    @XmlElement
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    @XmlElement
    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }
}

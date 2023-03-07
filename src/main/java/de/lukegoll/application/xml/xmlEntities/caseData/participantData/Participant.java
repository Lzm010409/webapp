package de.lukegoll.application.xml.xmlEntities.caseData.participantData;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Participant {

    private String type;
    private String gender;
    private String name1;
    private String name2;
    private String name3;
    private boolean tax_deduction;
    private Address address;

    public Participant (){

    }

    public Participant(String type, String gender, String name1, String name2, String name3, boolean tax_deduction, Address address) {
        this.type = type;
        this.gender = gender;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.tax_deduction = tax_deduction;
        this.address = address;
    }
    @XmlElement
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @XmlElement
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    @XmlElement
    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }
    @XmlElement
    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
    @XmlElement
    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }
    @XmlElement
    public boolean isTax_deduction() {
        return tax_deduction;
    }

    public void setTax_deduction(boolean tax_deduction) {
        this.tax_deduction = tax_deduction;
    }
    @XmlElement
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

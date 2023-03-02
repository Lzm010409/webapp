package de.lukegoll.application.xmlEntities.constants;

public enum ContactType {

    PHONE("phone"),
    MAIL("mail"),
    URL("url"),
    FAX("fax"),
    MOBILE("mobile");

String type;

    ContactType(String type){
        this.type=type;
    }

}

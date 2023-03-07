package de.lukegoll.application.xml.xmlEntities.constants;

public enum ClaimType {

    LIABILITYDAMAGE("liability damage", "00000000-0000-0000-0000-000000000001", "Haftpflichtschaden"),
    GENERALLIABILITYDAMAGE("general liability damage", "00000000-0000-0000-0000-000000000002", "Genereller Haftpflichtschaden"),
    COMPREHENSIVEDAMAGE("comprehensive damage", "00000000-0000-0000-0000-000000000003", "Kaskoschaden"),
    PARTIALYCOMPREHENSIVEDAMAGE("partialy comprehensive damage", "00000000-0000-0000-0000-000000000004", "Teils Kaskoschaden");

    String type;

    String id;

    String germanType;

    ClaimType(String type, String id, String germanType) {
        this.type = type;
        this.id = id;
        this.germanType = germanType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGermanType() {
        return germanType;
    }

    public void setGermanType(String germanType) {
        this.germanType = germanType;
    }
}

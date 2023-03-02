package de.lukegoll.application.xmlEntities.constants;

public enum Unit {

    KM("km"),
    MLS("mls"),
    HRS("hrs"),
    OTHER("other");

String unit;

    Unit(String unit){
        this.unit=unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

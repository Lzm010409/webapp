package de.lukegoll.application.xml.xmlEntities.caseData;


import de.lukegoll.application.xml.xmlEntities.constants.Unit;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Vehicle {

    private String manufaturer;

    private String model;

    private String plate_number;
    private String plate_number_opponent;
    private String vin;
    private String kba_numbers;
    private String first_registrations;
    private long mileage_read;
    private Unit mileage_read_unit_code;
    private long mileage_specified;
    private Unit mileage_specified_unit_code;
    private long mileage_estimated;
    private Unit mileage_estimated_unit_code;

    public Vehicle(){

    }

    public Vehicle(String manufaturer, String model, String plate_number, String plate_number_opponent, String vin, String kba_numbers, String first_registrations, long mileage_read, Unit mileage_read_unit_code, long mileage_specified, Unit mileage_specified_unit_code, long mileage_estimated, Unit mileage_estimated_unit_code) {
        this.manufaturer = manufaturer;
        this.model = model;
        this.plate_number = plate_number;
        this.plate_number_opponent = plate_number_opponent;
        this.vin = vin;
        this.kba_numbers = kba_numbers;
        this.first_registrations = first_registrations;
        this.mileage_read = mileage_read;
        this.mileage_read_unit_code = mileage_read_unit_code;
        this.mileage_specified = mileage_specified;
        this.mileage_specified_unit_code = mileage_specified_unit_code;
        this.mileage_estimated = mileage_estimated;
        this.mileage_estimated_unit_code = mileage_estimated_unit_code;
    }

    @XmlElement
    public String getManufaturer() {
        return manufaturer;
    }

    public void setManufaturer(String manufaturer) {
        this.manufaturer = manufaturer;
    }
    @XmlElement
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    @XmlElement
    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }
    @XmlElement
    public String getPlate_number_opponent() {
        return plate_number_opponent;
    }

    public void setPlate_number_opponent(String plate_number_opponent) {
        this.plate_number_opponent = plate_number_opponent;
    }
    @XmlElement
    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
    @XmlElement
    public String getKba_numbers() {
        return kba_numbers;
    }

    public void setKba_numbers(String kba_numbers) {
        this.kba_numbers = kba_numbers;
    }
    @XmlElement
    public String getFirst_registrations() {
        return first_registrations;
    }

    public void setFirst_registrations(String first_registrations) {
        this.first_registrations = first_registrations;
    }
    @XmlElement
    public long getMileage_read() {
        return mileage_read;
    }

    public void setMileage_read(long mileage_read) {
        this.mileage_read = mileage_read;
    }
    @XmlElement
    public Unit getMileage_read_unit_code() {
        return mileage_read_unit_code;
    }

    public void setMileage_read_unit_code(Unit mileage_read_unit_code) {
        this.mileage_read_unit_code = mileage_read_unit_code;
    }
    @XmlElement
    public long getMileage_specified() {
        return mileage_specified;
    }

    public void setMileage_specified(long mileage_specified) {
        this.mileage_specified = mileage_specified;
    }
    @XmlElement
    public Unit getMileage_specified_unit_code() {
        return mileage_specified_unit_code;
    }

    public void setMileage_specified_unit_code(Unit mileage_specified_unit_code) {
        this.mileage_specified_unit_code = mileage_specified_unit_code;
    }
    @XmlElement
    public long getMileage_estimated() {
        return mileage_estimated;
    }

    public void setMileage_estimated(long mileage_estimated) {
        this.mileage_estimated = mileage_estimated;
    }
    @XmlElement
    public Unit getMileage_estimated_unit_code() {
        return mileage_estimated_unit_code;
    }

    public void setMileage_estimated_unit_code(Unit mileage_estimated_unit_code) {
        this.mileage_estimated_unit_code = mileage_estimated_unit_code;
    }
}

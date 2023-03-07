package de.lukegoll.application.xml.xmlEntities.caseData;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
@XmlRootElement
public class ClaimnetInfo {

    private String order_type;
    private String comment;
    private String claim_type_gdv;
    private String claim_type_gdv_code;
    private String damage_division_gdv;
    private String damage_division_gdv_code;
    private String damage_circumstances_gdv;
    private String damage_circumstances_gdv_code;
    private String description_order;
    private String condition_insurance;
    private Double damage_costs_overall;
    private String damage_location;
    private Date damage_reporting_date;
    private Double discount_general;
    private String insurance_reference_number;
    private String risk_factor_gdv;
    private String risk_factor_gdv_code;

    public ClaimnetInfo(){

    }

    public ClaimnetInfo(String order_type, String comment, String claim_type_gdv, String claim_type_gdv_code, String damage_division_gdv, String damage_division_gdv_code, String damage_circumstances_gdv, String damage_circumstances_gdv_code, String description_order, String condition_insurance, Double damage_costs_overall, String damage_location, Date damage_reporting_date, Double discount_general, String insurance_reference_number, String risk_factor_gdv, String risk_factor_gdv_code) {
        this.order_type = order_type;
        this.comment = comment;
        this.claim_type_gdv = claim_type_gdv;
        this.claim_type_gdv_code = claim_type_gdv_code;
        this.damage_division_gdv = damage_division_gdv;
        this.damage_division_gdv_code = damage_division_gdv_code;
        this.damage_circumstances_gdv = damage_circumstances_gdv;
        this.damage_circumstances_gdv_code = damage_circumstances_gdv_code;
        this.description_order = description_order;
        this.condition_insurance = condition_insurance;
        this.damage_costs_overall = damage_costs_overall;
        this.damage_location = damage_location;
        this.damage_reporting_date = damage_reporting_date;
        this.discount_general = discount_general;
        this.insurance_reference_number = insurance_reference_number;
        this.risk_factor_gdv = risk_factor_gdv;
        this.risk_factor_gdv_code = risk_factor_gdv_code;
    }
    @XmlElement
    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
    @XmlElement
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    @XmlElement
    public String getClaim_type_gdv() {
        return claim_type_gdv;
    }

    public void setClaim_type_gdv(String claim_type_gdv) {
        this.claim_type_gdv = claim_type_gdv;
    }
    @XmlElement
    public String getClaim_type_gdv_code() {
        return claim_type_gdv_code;
    }

    public void setClaim_type_gdv_code(String claim_type_gdv_code) {
        this.claim_type_gdv_code = claim_type_gdv_code;
    }
    @XmlElement
    public String getDamage_division_gdv() {
        return damage_division_gdv;
    }

    public void setDamage_division_gdv(String damage_division_gdv) {
        this.damage_division_gdv = damage_division_gdv;
    }
    @XmlElement
    public String getDamage_division_gdv_code() {
        return damage_division_gdv_code;
    }

    public void setDamage_division_gdv_code(String damage_division_gdv_code) {
        this.damage_division_gdv_code = damage_division_gdv_code;
    }
    @XmlElement
    public String getDamage_circumstances_gdv() {
        return damage_circumstances_gdv;
    }

    public void setDamage_circumstances_gdv(String damage_circumstances_gdv) {
        this.damage_circumstances_gdv = damage_circumstances_gdv;
    }
    @XmlElement
    public String getDamage_circumstances_gdv_code() {
        return damage_circumstances_gdv_code;
    }

    public void setDamage_circumstances_gdv_code(String damage_circumstances_gdv_code) {
        this.damage_circumstances_gdv_code = damage_circumstances_gdv_code;
    }
    @XmlElement
    public String getDescription_order() {
        return description_order;
    }

    public void setDescription_order(String description_order) {
        this.description_order = description_order;
    }
    @XmlElement
    public String getCondition_insurance() {
        return condition_insurance;
    }

    public void setCondition_insurance(String condition_insurance) {
        this.condition_insurance = condition_insurance;
    }
    @XmlElement
    public Double getDamage_costs_overall() {
        return damage_costs_overall;
    }

    public void setDamage_costs_overall(Double damage_costs_overall) {
        this.damage_costs_overall = damage_costs_overall;
    }
    @XmlElement
    public String getDamage_location() {
        return damage_location;
    }

    public void setDamage_location(String damage_location) {
        this.damage_location = damage_location;
    }

    @XmlElement
    public Date getDamage_reporting_date() {
        return damage_reporting_date;
    }

    public void setDamage_reporting_date(Date damage_reporting_date) {
        this.damage_reporting_date = damage_reporting_date;
    }
    @XmlElement
    public Double getDiscount_general() {
        return discount_general;
    }

    public void setDiscount_general(Double discount_general) {
        this.discount_general = discount_general;
    }
    @XmlElement
    public String getInsurance_reference_number() {
        return insurance_reference_number;
    }

    public void setInsurance_reference_number(String insurance_reference_number) {
        this.insurance_reference_number = insurance_reference_number;
    }
    @XmlElement
    public String getRisk_factor_gdv() {
        return risk_factor_gdv;
    }

    public void setRisk_factor_gdv(String risk_factor_gdv) {
        this.risk_factor_gdv = risk_factor_gdv;
    }
    @XmlElement
    public String getRisk_factor_gdv_code() {
        return risk_factor_gdv_code;
    }

    public void setRisk_factor_gdv_code(String risk_factor_gdv_code) {
        this.risk_factor_gdv_code = risk_factor_gdv_code;
    }
}

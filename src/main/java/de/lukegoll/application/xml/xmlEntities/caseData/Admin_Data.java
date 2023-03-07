package de.lukegoll.application.xml.xmlEntities.caseData;



import de.lukegoll.application.xml.xmlEntities.adapter.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlRootElement
public class Admin_Data {

    private String case_no;
    private LocalDateTime claim_date;
    private boolean claim_date_usertime;

    private String claim_location;
    private String claim_number;
    private LocalDateTime case_date_of_order;
    private boolean case_date_of_order_usertime;
    private String case_ordered_by;
    private String policy_number;
    private double deductible_partial;
    private double deductible_comprehensive;
    private String claim_type;
    private String claim_type_id;
    private String case_comment;

    public Admin_Data (){

    }

    public Admin_Data(String case_no, LocalDateTime claim_date, boolean claim_date_usertime, String claim_location, String claim_number, LocalDateTime case_date_of_order, boolean case_date_of_order_usertime, String case_ordered_by, String policy_number, double deductible_partial, double deductible_comprehensive, String claim_type, String claim_type_id, String case_comment) {
        this.case_no = case_no;
        this.claim_date = claim_date;
        this.claim_date_usertime = claim_date_usertime;
        this.claim_location = claim_location;
        this.claim_number = claim_number;
        this.case_date_of_order = case_date_of_order;
        this.case_date_of_order_usertime = case_date_of_order_usertime;
        this.case_ordered_by = case_ordered_by;
        this.policy_number = policy_number;
        this.deductible_partial = deductible_partial;
        this.deductible_comprehensive = deductible_comprehensive;
        this.claim_type = claim_type;
        this.claim_type_id = claim_type_id;
        this.case_comment = case_comment;
    }
    @XmlElement
    public String getCase_no() {
        return case_no;
    }

    public void setCase_no(String case_no) {
        this.case_no = case_no;
    }
    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    public LocalDateTime getClaim_date() {
        return claim_date;
    }

    public void setClaim_date(LocalDateTime claim_date) {
        this.claim_date = claim_date;
    }
    @XmlElement
    public boolean isClaim_date_usertime() {
        return claim_date_usertime;
    }

    public void setClaim_date_usertime(boolean claim_date_usertime) {
        this.claim_date_usertime = claim_date_usertime;
    }
    @XmlElement
    public String getClaim_location() {
        return claim_location;
    }

    public void setClaim_location(String claim_location) {
        this.claim_location = claim_location;
    }
    @XmlElement
    public String getClaim_number() {
        return claim_number;
    }

    public void setClaim_number(String claim_number) {
        this.claim_number = claim_number;
    }
    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    public LocalDateTime getCase_date_of_order() {
        return case_date_of_order;
    }

    public void setCase_date_of_order(LocalDateTime case_date_of_order) {
        this.case_date_of_order = case_date_of_order;
    }
    @XmlElement
    public boolean isCase_date_of_order_usertime() {
        return case_date_of_order_usertime;
    }

    public void setCase_date_of_order_usertime(boolean case_date_of_order_usertime) {
        this.case_date_of_order_usertime = case_date_of_order_usertime;
    }
    @XmlElement
    public String getCase_ordered_by() {
        return case_ordered_by;
    }

    public void setCase_ordered_by(String case_ordered_by) {
        this.case_ordered_by = case_ordered_by;
    }
    @XmlElement
    public String getPolicy_number() {
        return policy_number;
    }

    public void setPolicy_number(String policy_number) {
        this.policy_number = policy_number;
    }
    @XmlElement
    public double getDeductible_partial() {
        return deductible_partial;
    }

    public void setDeductible_partial(double deductible_partial) {
        this.deductible_partial = deductible_partial;
    }
    @XmlElement
    public double getDeductible_comprehensive() {
        return deductible_comprehensive;
    }

    public void setDeductible_comprehensive(double deductible_comprehensive) {
        this.deductible_comprehensive = deductible_comprehensive;
    }
    @XmlElement
    public String getClaim_type() {
        return claim_type;
    }

    public void setClaim_type(String claim_type) {
        this.claim_type = claim_type;
    }
    @XmlElement
    public String getClaim_type_id() {
        return claim_type_id;
    }

    public void setClaim_type_id(String claim_type_id) {
        this.claim_type_id = claim_type_id;
    }
    @XmlElement
    public String getCase_comment() {
        return case_comment;
    }

    public void setCase_comment(String case_comment) {
        this.case_comment = case_comment;
    }


}

package de.lukegoll.application.restfulapi.requests.url;

public enum URL {

    CREATEINVOICE("/Invoice/Factory/saveInvoice"),
    CREATEVOUCHER("/Voucher/Factory/saveVoucher"),
    UPLOADVOUCHERFILE("/Voucher/Factory/uploadTempFile"),
    RETRIEVEALLCONTACTS("/Contact?depth=1&limit=none"),
    RETRIEVEALLCONTACTADRESSES("/ContactAddress?depth=1&limit=none"),
    CREATENEWCONTACT("/Contact"),

    CREATENEWCONTACTADRESS("/ContactAddress"),
    CREATETAG("/Tag/Factory/create"),
    CREATEPART("/Part"),
    GETALLPARTS("/Part"),
    DELETECONTACT("/Contact/");

    String extender;


    URL(String string) {
        this.extender = string;
    }

    public String getExtender() {
        return extender;
    }

    public void setExtender(String extender) {
        this.extender = extender;
    }
}

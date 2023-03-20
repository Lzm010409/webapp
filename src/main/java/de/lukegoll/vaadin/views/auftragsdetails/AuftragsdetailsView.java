package de.lukegoll.vaadin.views.auftragsdetails;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.Fahrzeug;
import de.lukegoll.application.data.service.AuftragService;
import de.lukegoll.application.data.service.FahrzeugService;
import de.lukegoll.vaadin.converter.IntegerConverter;
import de.lukegoll.vaadin.converter.LocalDateConverter;
import de.lukegoll.vaadin.converter.LocalTimeConverter;


public class AuftragsdetailsView extends FormLayout {

    AuftragService auftragService;
    FahrzeugService fahrzeugService;
    Binder<Auftrag> auftragsDetailsBinder = new Binder<>(Auftrag.class);

    Binder<Fahrzeug> fahrzeugBinder = new Binder<>(Fahrzeug.class);

    DatePicker auftragsDatum = new DatePicker("Auftragsdatum");
    DatePicker schadenDatum = new DatePicker("Schadendatum");
    TextField schadenOrt = new TextField("Schadenort");
    TextField gutachtenNummer = new TextField("Gutachtennummer");
    TextField schadennummer = new TextField("Schadennummer");
    TextField kennzeichenUG = new TextField("Kennzeichen Unfallgegner");
    TextField besichtigunsOrt = new TextField("Besichtigungsort");
    DatePicker besichtigungsDatum = new DatePicker("Besichtigungsdatum");
    TimePicker besichtigungsUhrzeit = new TimePicker("Besichtigungsuhrzeit");
    TextArea schadenhergang = new TextArea("Schadenhergang");
    TextArea auftragsBesonderheiten = new TextArea("Auftragsbesonderheiten");
    TextField anspruchsteller = new TextField("Anspruchsteller");
    ComboBox rechtsanwalt = new ComboBox("Rechtsanwalt");
    ComboBox versicherung = new ComboBox("Versicherung");


    TextField hersteller = new TextField("Hersteller");
    TextField model = new TextField("Model");
    TextField kennzeichen = new TextField("Kennzeichen");
    TextField fin = new TextField("FIN");
    TextField hsntsn = new TextField("HSN/TSN");
    DatePicker erstZulassung = new DatePicker("Erste Zulassung");
    TextField laufleistung = new TextField("Laufleistung");

    Button saveButton = new Button("Speichern");
    Button deleteButton = new Button("Löschen");
    Button cancelButton = new Button("Abbrechen");
    Button resendButton = new Button("Auftrag nochmal versenden");

    private Auftrag auftrag;
    private Fahrzeug fahrzeug;

    public AuftragsdetailsView() {
        addClassName("auftragsDetails");
        configureFahrzeugBinder();
        configureAuftragsDetailsBinder();
        add(
                auftragsDatum,
                schadenDatum,
                schadenOrt,
                gutachtenNummer,
                schadennummer,
                kennzeichenUG,
                besichtigunsOrt,
                besichtigungsDatum,
                besichtigungsUhrzeit,
                schadenhergang,
                auftragsBesonderheiten,
                anspruchsteller,
                rechtsanwalt,
                versicherung,
                hersteller,
                model,
                kennzeichen,
                fin,
                hsntsn,
                erstZulassung,
                laufleistung
                , createButtonLayout()
        );
    }

    private void configureAuftragsDetailsBinder() {
        auftragsDetailsBinder.forField(auftragsDatum).withConverter(new LocalDateConverter()).bind(Auftrag::getAuftragsDatum, Auftrag::setAuftragsDatum);
        auftragsDetailsBinder.forField(schadenDatum).withConverter(new LocalDateConverter()).bind(Auftrag::getSchadenDatum, Auftrag::setSchadenDatum);
        auftragsDetailsBinder.forField(schadenOrt).bind(Auftrag::getSchadenOrt, Auftrag::setSchadenOrt);
        auftragsDetailsBinder.forField(gutachtenNummer).bind(Auftrag::getGutachtenNummer, Auftrag::setGutachtenNummer);
        auftragsDetailsBinder.forField(schadennummer).bind(Auftrag::getSchadennummer, Auftrag::setSchadennummer);
        auftragsDetailsBinder.forField(kennzeichenUG).bind(Auftrag::getKennzeichenUG, Auftrag::setKennzeichenUG);
        auftragsDetailsBinder.forField(besichtigunsOrt).bind(Auftrag::getBesichtigungsort, Auftrag::setBesichtigungsort);
        auftragsDetailsBinder.forField(besichtigungsDatum).withConverter(new LocalDateConverter()).bind(Auftrag::getBesichtigungsDatum, Auftrag::setBesichtigungsDatum);
        auftragsDetailsBinder.forField(besichtigungsUhrzeit).withConverter(new LocalTimeConverter()).bind(Auftrag::getBesichtigungsUhrzeit, Auftrag::setBesichtigungsUhrzeit);
        auftragsDetailsBinder.forField(schadenhergang).bind(Auftrag::getSchadenhergang, Auftrag::setSchadenhergang);
        auftragsDetailsBinder.forField(auftragsBesonderheiten).bind(Auftrag::getAuftragsBesonderheiten, Auftrag::setAuftragsBesonderheiten);


    }

    private void configureFahrzeugBinder() {
        fahrzeugBinder.forField(hersteller).bind(Fahrzeug::getHersteller, Fahrzeug::setHersteller);
        fahrzeugBinder.forField(model).bind(Fahrzeug::getTyp, Fahrzeug::setTyp);
        fahrzeugBinder.forField(kennzeichen).bind(Fahrzeug::getAmtlKennzeichen, Fahrzeug::setAmtlKennzeichen);
        fahrzeugBinder.forField(fin).bind(Fahrzeug::getFin, Fahrzeug::setFin);
        fahrzeugBinder.forField(hsntsn).bind(Fahrzeug::getHsntsn, Fahrzeug::setHsntsn);
        fahrzeugBinder.forField(erstZulassung).withConverter(new LocalDateConverter()).bind(Fahrzeug::getErstZulassung, Fahrzeug::setErstZulassung);
        fahrzeugBinder.forField(laufleistung).withConverter(new IntegerConverter()).bind(Fahrzeug::getKmStand, Fahrzeug::setKmStand);
    }

    private HorizontalLayout createButtonLayout() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        resendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        saveButton.addClickListener(buttonClickEvent -> validateAndSave());
        deleteButton.addClickListener(buttonClickEvent -> fireEvent(new DeleteEvent(this, auftragsDetailsBinder.getBean(), fahrzeugBinder.getBean())));
        cancelButton.addClickListener(buttonClickEvent -> fireEvent(new CloseEvent(this)));

        auftragsDetailsBinder.addStatusChangeListener(statusChangeEvent -> saveButton.setEnabled(auftragsDetailsBinder.isValid()));
        fahrzeugBinder.addStatusChangeListener(statusChangeEvent -> saveButton.setEnabled(fahrzeugBinder.isValid()));

        return new HorizontalLayout(saveButton, deleteButton, cancelButton, resendButton);
    }

    private void validateAndSave() {
        if (auftragsDetailsBinder.isValid() && fahrzeugBinder.isValid()) {
            fireEvent(new SaveEvent(this, auftragsDetailsBinder.getBean(), fahrzeugBinder.getBean()));
        }
    }



    public void setAuftrag(Auftrag auftrag) {
        auftragsDetailsBinder.setBean(auftrag);
    }

    public void setFahrzeug(Fahrzeug fahrzeug) {
        fahrzeugBinder.setBean(fahrzeug);
    }

    public Binder<Auftrag> getAuftragsDetailsBinder() {
        return auftragsDetailsBinder;
    }

    public void setAuftragsDetailsBinder(Binder<Auftrag> auftragsDetailsBinder) {
        this.auftragsDetailsBinder = auftragsDetailsBinder;
    }

    public Binder<Fahrzeug> getFahrzeugBinder() {
        return fahrzeugBinder;
    }

    public void setFahrzeugBinder(Binder<Fahrzeug> fahrzeugBinder) {
        this.fahrzeugBinder = fahrzeugBinder;
    }

    public DatePicker getAuftragsDatum() {
        return auftragsDatum;
    }

    public void setAuftragsDatum(DatePicker auftragsDatum) {
        this.auftragsDatum = auftragsDatum;
    }

    public DatePicker getSchadenDatum() {
        return schadenDatum;
    }

    public void setSchadenDatum(DatePicker schadenDatum) {
        this.schadenDatum = schadenDatum;
    }

    public TextField getSchadenOrt() {
        return schadenOrt;
    }

    public void setSchadenOrt(TextField schadenOrt) {
        this.schadenOrt = schadenOrt;
    }

    public TextField getGutachtenNummer() {
        return gutachtenNummer;
    }

    public void setGutachtenNummer(TextField gutachtenNummer) {
        this.gutachtenNummer = gutachtenNummer;
    }

    public TextField getSchadennummer() {
        return schadennummer;
    }

    public void setSchadennummer(TextField schadennummer) {
        this.schadennummer = schadennummer;
    }

    public TextField getKennzeichenUG() {
        return kennzeichenUG;
    }

    public void setKennzeichenUG(TextField kennzeichenUG) {
        this.kennzeichenUG = kennzeichenUG;
    }


    public DatePicker getBesichtigungsDatum() {
        return besichtigungsDatum;
    }

    public void setBesichtigungsDatum(DatePicker besichtigungsDatum) {
        this.besichtigungsDatum = besichtigungsDatum;
    }

    public TimePicker getBesichtigungsUhrzeit() {
        return besichtigungsUhrzeit;
    }

    public void setBesichtigungsUhrzeit(TimePicker besichtigungsUhrzeit) {
        this.besichtigungsUhrzeit = besichtigungsUhrzeit;
    }

    public TextArea getSchadenhergang() {
        return schadenhergang;
    }

    public void setSchadenhergang(TextArea schadenhergang) {
        this.schadenhergang = schadenhergang;
    }

    public TextArea getAuftragsBesonderheiten() {
        return auftragsBesonderheiten;
    }

    public void setAuftragsBesonderheiten(TextArea auftragsBesonderheiten) {
        this.auftragsBesonderheiten = auftragsBesonderheiten;
    }

    public TextField getAnspruchsteller() {
        return anspruchsteller;
    }

    public void setAnspruchsteller(TextField anspruchsteller) {
        this.anspruchsteller = anspruchsteller;
    }

    public ComboBox getRechtsanwalt() {
        return rechtsanwalt;
    }

    public void setRechtsanwalt(ComboBox rechtsanwalt) {
        this.rechtsanwalt = rechtsanwalt;
    }

    public ComboBox getVersicherung() {
        return versicherung;
    }

    public void setVersicherung(ComboBox versicherung) {
        this.versicherung = versicherung;
    }

    public TextField getHersteller() {
        return hersteller;
    }

    public void setHersteller(TextField hersteller) {
        this.hersteller = hersteller;
    }

    public TextField getModel() {
        return model;
    }

    public void setModel(TextField model) {
        this.model = model;
    }

    public TextField getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(TextField kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public TextField getFin() {
        return fin;
    }

    public void setFin(TextField fin) {
        this.fin = fin;
    }

    public TextField getHsntsn() {
        return hsntsn;
    }

    public void setHsntsn(TextField hsntsn) {
        this.hsntsn = hsntsn;
    }

    public DatePicker getErstZulassung() {
        return erstZulassung;
    }

    public void setErstZulassung(DatePicker erstZulassung) {
        this.erstZulassung = erstZulassung;
    }

    public TextField getLaufleistung() {
        return laufleistung;
    }

    public void setLaufleistung(TextField laufleistung) {
        this.laufleistung = laufleistung;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public Button getResendButton() {
        return resendButton;
    }

    public void setResendButton(Button resendButton) {
        this.resendButton = resendButton;
    }


    public static abstract class AuftragsDetailsEvent extends ComponentEvent<AuftragsdetailsView> {
        private Auftrag auftrag;
        private Fahrzeug fahrzeug;

        protected AuftragsDetailsEvent(AuftragsdetailsView source, Auftrag auftrag, Fahrzeug fahrzeug) {
            super(source, false);
            this.auftrag = auftrag;
            this.fahrzeug = fahrzeug;
        }

        public Auftrag getAuftrag() {
            return auftrag;
        }

        public Fahrzeug getFahrzeug() {
            return fahrzeug;
        }
    }

    public static class SaveEvent extends AuftragsDetailsEvent {
        SaveEvent(AuftragsdetailsView source, Auftrag auftrag, Fahrzeug fahrzeug) {
            super(source, auftrag, fahrzeug);
        }
    }

    public static class DeleteEvent extends AuftragsDetailsEvent {
        DeleteEvent(AuftragsdetailsView source, Auftrag auftrag, Fahrzeug fahrzeug) {
            super(source, auftrag, fahrzeug);
        }

    }

    public static class CloseEvent extends AuftragsDetailsEvent {
        CloseEvent(AuftragsdetailsView source) {
            super(source, null, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}

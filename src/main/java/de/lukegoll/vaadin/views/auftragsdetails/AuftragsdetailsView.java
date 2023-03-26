package de.lukegoll.vaadin.views.auftragsdetails;

import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.Fahrzeug;
import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.data.entity.persons.Kunde;
import de.lukegoll.application.data.entity.persons.Rechtsanwalt;
import de.lukegoll.application.data.entity.persons.Versicherung;
import de.lukegoll.application.data.service.AuftragService;
import de.lukegoll.application.data.service.FahrzeugService;
import de.lukegoll.vaadin.converter.IntegerConverter;
import de.lukegoll.vaadin.converter.KundeConverter;
import de.lukegoll.vaadin.converter.LocalDateConverter;
import de.lukegoll.vaadin.converter.LocalTimeConverter;


import java.util.HashSet;
import java.util.Set;


public class AuftragsdetailsView extends FormLayout {

    AuftragService auftragService;
    FahrzeugService fahrzeugService;
    Binder<Auftrag> auftragsDetailsBinder = new Binder<>(Auftrag.class);

    Binder<Fahrzeug> fahrzeugBinder = new Binder<>(Fahrzeug.class);

    Binder<Kunde> kundenBinder = new Binder<>(Kunde.class);

    Binder<Rechtsanwalt> rechtsanwaltBinder = new Binder<>(Rechtsanwalt.class);

    Binder<Versicherung> versicherungBinder = new Binder<>(Versicherung.class);

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
    ComboBox versicherung = new ComboBox("Versicherung");


    TextField hersteller = new TextField("Hersteller");
    TextField model = new TextField("Model");
    TextField kennzeichen = new TextField("Kennzeichen");
    TextField fin = new TextField("FIN");
    TextField hsntsn = new TextField("HSN/TSN");
    DatePicker erstZulassung = new DatePicker("Erste Zulassung");
    TextField laufleistung = new TextField("Laufleistung");
    /**
     * Text-Fields für die Kundendaten
     */
    TextField anredeKunde = new TextField("Anrede");
    TextField vNameKunde = new TextField("Vorname");
    TextField nNameKunde = new TextField("Nachname");
    TextField adresseKunde = new TextField("Adresse");
    TextField plzKunde = new TextField("PLZ");
    TextField stadtKunde = new TextField("Stadt");
    TextField telKunde = new TextField("Tel");
    TextField mailKunde = new TextField("Mail");
    /**
     * Text-Fields für die Rechtsanwaltsdaten
     */
    TextField anredeRechtsanwalt = new TextField("Anrede");
    TextField vNameRechtsanwalt = new TextField("Vorname");
    TextField nNameRechtsanwalt = new TextField("Nachname");
    TextField adresseRechtsanwalt = new TextField("Adresse");
    TextField plzRechtsanwalt = new TextField("PLZ");
    TextField stadtRechtsanwalt = new TextField("Stadt");
    TextField telRechtsanwalt = new TextField("Tel");
    TextField mailRechtsanwalt = new TextField("Mail");

    /**
     * Text-Fields für die Versicherungsdaten
     */
    TextField anredeVersicherung = new TextField("Anrede");
    TextField vNameVersicherung = new TextField("Vorname");
    TextField nNameVersicherung = new TextField("Nachname");
    TextField adresseVersicherung = new TextField("Adresse");
    TextField plzVersicherung = new TextField("PLZ");
    TextField stadtVersicherung = new TextField("Stadt");
    TextField telVersicherung = new TextField("Tel");
    TextField mailVersicherung = new TextField("Mail");

    /**
     * Files für den Auftrag
     */
    PdfViewer pdfViewer = new PdfViewer();

    Button saveButton = new Button("Speichern");
    Button deleteButton = new Button("Löschen");
    Button cancelButton = new Button("Abbrechen");
    Button resendButton = new Button("Auftrag nochmal versenden");

    private Auftrag auftrag;
    private Fahrzeug fahrzeug;
    private Kontakt kunde;
    private Rechtsanwalt rechtsanwalt;


    public AuftragsdetailsView() {
        addClassName("auftragsDetails");
        configureFahrzeugBinder();
        configureAuftragsDetailsBinder();
        configureKundenBinder();
        configureRechtsanwaltBinder();
        configureVersicherungBinder();
        add(createTabSheet());
    }

    private Component createTabSheet() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth("600px");
        tabSheet.setMaxWidth("100%");
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_HIDE_SCROLL_BUTTONS);
        tabSheet.add("Auftragsdetails", createAuftragsTab());
        tabSheet.add("Fahrzeugdetails", createFahrzeugTab());
        tabSheet.add("Kontaktdetails", createKontaktTab());
        tabSheet.add("Dateien", createDataTab());

        return tabSheet;
    }

    private Component createKundenForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(
                anredeKunde,
                vNameKunde,
                nNameKunde,
                adresseKunde,
                plzKunde,
                stadtKunde,
                telKunde,
                mailKunde
        );
        return formLayout;
    }

    private Component createRechtsanwaltForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(
                anredeRechtsanwalt,
                vNameRechtsanwalt,
                nNameRechtsanwalt,
                adresseRechtsanwalt,
                plzRechtsanwalt,
                stadtRechtsanwalt,
                telRechtsanwalt,
                mailRechtsanwalt
        );
        return formLayout;
    }

    private Component createVersicherungForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(
                anredeVersicherung,
                vNameVersicherung,
                nNameVersicherung,
                adresseVersicherung,
                plzVersicherung,
                stadtVersicherung,
                telVersicherung,
                mailVersicherung
        );
        return formLayout;
    }

    private Component createFahrzeugTab() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(hersteller,
                model,
                kennzeichen,
                fin,
                hsntsn,
                erstZulassung,
                laufleistung
        );
        return formLayout;
    }

    private Component createKontaktTab() {
        Accordion customDetailsPanel = new Accordion();
        customDetailsPanel.add("Kunde", createKundenForm());
        customDetailsPanel.add("Rechtsanwalt", createRechtsanwaltForm());
        customDetailsPanel.add("Versicherung", createVersicherungForm());
        return customDetailsPanel;
    }

    private Component createAuftragsTab() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(auftragsDatum,
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
                createButtonLayout());
        return formLayout;
    }

    private Component createDataTab() {
        pdfViewer.setSrc("/Users/lukegollenstede/Downloads/Rechnung_3692157276.pdf");
        pdfViewer.setSizeFull();
        return pdfViewer;
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

    private void configureKundenBinder() {
        kundenBinder.forField(vNameKunde).bind(Kunde::getvName, Kunde::setvName);
        kundenBinder.forField(nNameKunde).bind(Kunde::getnName, Kunde::setnName);
        kundenBinder.forField(anredeKunde).bind(Kunde::getAnrede, Kunde::setAnrede);
        kundenBinder.forField(adresseKunde).bind(Kunde::getAdresse, Kunde::setAdresse);
        kundenBinder.forField(plzKunde).bind(Kunde::getPlz, Kunde::setPlz);
        kundenBinder.forField(stadtKunde).bind(Kunde::getStadt, Kunde::setStadt);
        kundenBinder.forField(telKunde).bind(Kunde::getTel, Kunde::setTel);
        kundenBinder.forField(mailKunde).bind(Kunde::getMail, Kunde::setMail);
    }

    private void configureRechtsanwaltBinder() {
        rechtsanwaltBinder.forField(vNameRechtsanwalt).bind(Rechtsanwalt::getvName, Rechtsanwalt::setvName);
        rechtsanwaltBinder.forField(nNameRechtsanwalt).bind(Rechtsanwalt::getnName, Rechtsanwalt::setnName);
        rechtsanwaltBinder.forField(anredeRechtsanwalt).bind(Rechtsanwalt::getAnrede, Rechtsanwalt::setAnrede);
        rechtsanwaltBinder.forField(adresseRechtsanwalt).bind(Rechtsanwalt::getAdresse, Rechtsanwalt::setAdresse);
        rechtsanwaltBinder.forField(plzRechtsanwalt).bind(Rechtsanwalt::getPlz, Rechtsanwalt::setPlz);
        rechtsanwaltBinder.forField(stadtRechtsanwalt).bind(Rechtsanwalt::getStadt, Rechtsanwalt::setStadt);
        rechtsanwaltBinder.forField(telRechtsanwalt).bind(Rechtsanwalt::getTel, Rechtsanwalt::setTel);
        rechtsanwaltBinder.forField(mailRechtsanwalt).bind(Rechtsanwalt::getMail, Rechtsanwalt::setMail);
    }

    private void configureVersicherungBinder() {
        versicherungBinder.forField(vNameVersicherung).bind(Versicherung::getvName, Versicherung::setvName);
        versicherungBinder.forField(nNameVersicherung).bind(Versicherung::getnName, Versicherung::setnName);
        versicherungBinder.forField(anredeVersicherung).bind(Versicherung::getAnrede, Versicherung::setAnrede);
        versicherungBinder.forField(adresseVersicherung).bind(Versicherung::getAdresse, Versicherung::setAdresse);
        versicherungBinder.forField(plzVersicherung).bind(Versicherung::getPlz, Versicherung::setPlz);
        versicherungBinder.forField(stadtVersicherung).bind(Versicherung::getStadt, Versicherung::setStadt);
        versicherungBinder.forField(telVersicherung).bind(Versicherung::getTel, Versicherung::setTel);
        versicherungBinder.forField(mailVersicherung).bind(Versicherung::getMail, Versicherung::setMail);
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
        resendButton.addClickListener(buttonClickEvent -> fireEvent(new ResendEvent(this, auftragsDetailsBinder.getBean(), fahrzeugBinder.getBean())));
        auftragsDetailsBinder.addStatusChangeListener(statusChangeEvent -> saveButton.setEnabled(auftragsDetailsBinder.isValid()));
        fahrzeugBinder.addStatusChangeListener(statusChangeEvent -> saveButton.setEnabled(fahrzeugBinder.isValid()));
        kundenBinder.addStatusChangeListener(statusChangeEvent -> saveButton.setEnabled(kundenBinder.isValid()));
        rechtsanwaltBinder.addStatusChangeListener(statusChangeEvent -> saveButton.setEnabled(rechtsanwaltBinder.isValid()));
        versicherungBinder.addStatusChangeListener(statusChangeEvent -> saveButton.setEnabled(versicherungBinder.isValid()));

        return new HorizontalLayout(saveButton, deleteButton, cancelButton, resendButton);
    }

    private void validateAndSave() {
        if (auftragsDetailsBinder.isValid() && fahrzeugBinder.isValid() && kundenBinder.isValid() && rechtsanwaltBinder.isValid()
                && versicherungBinder.isValid()) {
            Set<Kontakt> kontakts = new HashSet<>();
            kontakts.add(new Kunde().convertToKontakt(kundenBinder.getBean()));
            kontakts.add(new Rechtsanwalt().convertToKontakt(rechtsanwaltBinder.getBean()));
            kontakts.add(new Versicherung().convertToKontakt(versicherungBinder.getBean()));
            Auftrag auftrag1 = auftragsDetailsBinder.getBean();
            auftrag1.setKontakte(kontakts);
            fireEvent(new UpdateEvent(this, auftrag1, fahrzeugBinder.getBean(), kontakts));
        }
    }

    public void setKunde(Kunde kunde) {
        kundenBinder.setBean(kunde);
    }

    public void setRechtsanwalt(Rechtsanwalt rechtsanwalt) {
        rechtsanwaltBinder.setBean(rechtsanwalt);
    }

    public void setVersicherung(Versicherung versicherung) {
        versicherungBinder.setBean(versicherung);
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

        private Set<Kontakt> kontakts;

        protected AuftragsDetailsEvent(AuftragsdetailsView source, Auftrag auftrag, Fahrzeug fahrzeug, Set<Kontakt> kontakts) {
            super(source, false);
            this.auftrag = auftrag;
            this.fahrzeug = fahrzeug;
            this.kontakts = kontakts;
        }

        protected AuftragsDetailsEvent(AuftragsdetailsView source, Auftrag auftrag, Fahrzeug fahrzeug) {
            super(source, false);
            this.auftrag = auftrag;
            this.fahrzeug = fahrzeug;
        }

        protected AuftragsDetailsEvent(AuftragsdetailsView source, Auftrag auftrag) {
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

        public Set<Kontakt> getKontakts() {
            return kontakts;
        }
    }

    public static class SaveEvent extends AuftragsDetailsEvent {
        SaveEvent(AuftragsdetailsView source, Auftrag auftrag, Fahrzeug fahrzeug, Set<Kontakt> kontakts) {
            super(source, auftrag, fahrzeug, kontakts);
        }
    }

    public static class UpdateEvent extends AuftragsDetailsEvent {
        UpdateEvent(AuftragsdetailsView source, Auftrag auftrag, Fahrzeug fahrzeug, Set<Kontakt> kontakts) {
            super(source, auftrag, fahrzeug, kontakts);
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

    public static class ResendEvent extends AuftragsDetailsEvent {
        ResendEvent(AuftragsdetailsView source, Auftrag auftrag, Fahrzeug fahrzeug, Set<Kontakt> kontakts) {
            super(source, auftrag, fahrzeug, kontakts);
        }

        ResendEvent(AuftragsdetailsView source, Auftrag auftrag, Fahrzeug fahrzeug) {
            super(source, auftrag, fahrzeug);
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

    public Registration addResendListener(ComponentEventListener<ResendEvent> listener) {
        return addListener(ResendEvent.class, listener);
    }

    public Registration addUpdateListener(ComponentEventListener<UpdateEvent> listener) {
        return addListener(UpdateEvent.class, listener);
    }

    public AuftragService getAuftragService() {
        return auftragService;
    }

    public void setAuftragService(AuftragService auftragService) {
        this.auftragService = auftragService;
    }

    public FahrzeugService getFahrzeugService() {
        return fahrzeugService;
    }

    public void setFahrzeugService(FahrzeugService fahrzeugService) {
        this.fahrzeugService = fahrzeugService;
    }

    public Binder<Kunde> getKundenBinder() {
        return kundenBinder;
    }

    public void setKundenBinder(Binder<Kunde> kundenBinder) {
        this.kundenBinder = kundenBinder;
    }

    public Binder<Rechtsanwalt> getRechtsanwaltBinder() {
        return rechtsanwaltBinder;
    }

    public void setRechtsanwaltBinder(Binder<Rechtsanwalt> rechtsanwaltBinder) {
        this.rechtsanwaltBinder = rechtsanwaltBinder;
    }

    public Binder<Versicherung> getVersicherungBinder() {
        return versicherungBinder;
    }

    public void setVersicherungBinder(Binder<Versicherung> versicherungBinder) {
        this.versicherungBinder = versicherungBinder;
    }

    public TextField getBesichtigunsOrt() {
        return besichtigunsOrt;
    }

    public void setBesichtigunsOrt(TextField besichtigunsOrt) {
        this.besichtigunsOrt = besichtigunsOrt;
    }

    public TextField getAnredeKunde() {
        return anredeKunde;
    }

    public void setAnredeKunde(TextField anredeKunde) {
        this.anredeKunde = anredeKunde;
    }

    public TextField getvNameKunde() {
        return vNameKunde;
    }

    public void setvNameKunde(TextField vNameKunde) {
        this.vNameKunde = vNameKunde;
    }

    public TextField getnNameKunde() {
        return nNameKunde;
    }

    public void setnNameKunde(TextField nNameKunde) {
        this.nNameKunde = nNameKunde;
    }

    public TextField getAdresseKunde() {
        return adresseKunde;
    }

    public void setAdresseKunde(TextField adresseKunde) {
        this.adresseKunde = adresseKunde;
    }

    public TextField getPlzKunde() {
        return plzKunde;
    }

    public void setPlzKunde(TextField plzKunde) {
        this.plzKunde = plzKunde;
    }

    public TextField getStadtKunde() {
        return stadtKunde;
    }

    public void setStadtKunde(TextField stadtKunde) {
        this.stadtKunde = stadtKunde;
    }

    public TextField getTelKunde() {
        return telKunde;
    }

    public void setTelKunde(TextField telKunde) {
        this.telKunde = telKunde;
    }

    public TextField getMailKunde() {
        return mailKunde;
    }

    public void setMailKunde(TextField mailKunde) {
        this.mailKunde = mailKunde;
    }

    public TextField getAnredeRechtsanwalt() {
        return anredeRechtsanwalt;
    }

    public void setAnredeRechtsanwalt(TextField anredeRechtsanwalt) {
        this.anredeRechtsanwalt = anredeRechtsanwalt;
    }

    public TextField getvNameRechtsanwalt() {
        return vNameRechtsanwalt;
    }

    public void setvNameRechtsanwalt(TextField vNameRechtsanwalt) {
        this.vNameRechtsanwalt = vNameRechtsanwalt;
    }

    public TextField getnNameRechtsanwalt() {
        return nNameRechtsanwalt;
    }

    public void setnNameRechtsanwalt(TextField nNameRechtsanwalt) {
        this.nNameRechtsanwalt = nNameRechtsanwalt;
    }

    public TextField getAdresseRechtsanwalt() {
        return adresseRechtsanwalt;
    }

    public void setAdresseRechtsanwalt(TextField adresseRechtsanwalt) {
        this.adresseRechtsanwalt = adresseRechtsanwalt;
    }

    public TextField getPlzRechtsanwalt() {
        return plzRechtsanwalt;
    }

    public void setPlzRechtsanwalt(TextField plzRechtsanwalt) {
        this.plzRechtsanwalt = plzRechtsanwalt;
    }

    public TextField getStadtRechtsanwalt() {
        return stadtRechtsanwalt;
    }

    public void setStadtRechtsanwalt(TextField stadtRechtsanwalt) {
        this.stadtRechtsanwalt = stadtRechtsanwalt;
    }

    public TextField getTelRechtsanwalt() {
        return telRechtsanwalt;
    }

    public void setTelRechtsanwalt(TextField telRechtsanwalt) {
        this.telRechtsanwalt = telRechtsanwalt;
    }

    public TextField getMailRechtsanwalt() {
        return mailRechtsanwalt;
    }

    public void setMailRechtsanwalt(TextField mailRechtsanwalt) {
        this.mailRechtsanwalt = mailRechtsanwalt;
    }

    public TextField getAnredeVersicherung() {
        return anredeVersicherung;
    }

    public void setAnredeVersicherung(TextField anredeVersicherung) {
        this.anredeVersicherung = anredeVersicherung;
    }

    public TextField getvNameVersicherung() {
        return vNameVersicherung;
    }

    public void setvNameVersicherung(TextField vNameVersicherung) {
        this.vNameVersicherung = vNameVersicherung;
    }

    public TextField getnNameVersicherung() {
        return nNameVersicherung;
    }

    public void setnNameVersicherung(TextField nNameVersicherung) {
        this.nNameVersicherung = nNameVersicherung;
    }

    public TextField getAdresseVersicherung() {
        return adresseVersicherung;
    }

    public void setAdresseVersicherung(TextField adresseVersicherung) {
        this.adresseVersicherung = adresseVersicherung;
    }

    public TextField getPlzVersicherung() {
        return plzVersicherung;
    }

    public void setPlzVersicherung(TextField plzVersicherung) {
        this.plzVersicherung = plzVersicherung;
    }

    public TextField getStadtVersicherung() {
        return stadtVersicherung;
    }

    public void setStadtVersicherung(TextField stadtVersicherung) {
        this.stadtVersicherung = stadtVersicherung;
    }

    public TextField getTelVersicherung() {
        return telVersicherung;
    }

    public void setTelVersicherung(TextField telVersicherung) {
        this.telVersicherung = telVersicherung;
    }

    public TextField getMailVersicherung() {
        return mailVersicherung;
    }

    public void setMailVersicherung(TextField mailVersicherung) {
        this.mailVersicherung = mailVersicherung;
    }

    public Auftrag getAuftrag() {
        return auftrag;
    }

    public Fahrzeug getFahrzeug() {
        return fahrzeug;
    }

    public Kontakt getKunde() {
        return kunde;
    }

    public void setKunde(Kontakt kunde) {
        this.kunde = kunde;
    }

    public Rechtsanwalt getRechtsanwalt() {
        return rechtsanwalt;
    }

    public PdfViewer getPdfViewer() {
        return pdfViewer;
    }

    public void setPdfViewer(PdfViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
    }
}

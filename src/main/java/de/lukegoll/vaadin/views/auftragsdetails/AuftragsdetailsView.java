package de.lukegoll.vaadin.views.auftragsdetails;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.vaadin.converter.LocalDateConverter;


public class AuftragsdetailsView extends FormLayout {
    Binder<Auftrag> auftragsDetailsBinder = new Binder<>(Auftrag.class);

    DatePicker auftragsDatum = new DatePicker("Auftragsdatum");
    DatePicker schadenDatum = new DatePicker("Schadendatum");
    TextField schadenOrt = new TextField("Schadenort");
    TextField gutachtenNummer = new TextField("Gutachtennummer");
    TextField schadennummer = new TextField("Schadennummer");
    TextField kennzeichenUG = new TextField("Kennzeichen Unfallgegner");
    TextField besichtigungsOrt = new TextField("Besichtigungsort");
    DatePicker besichtigungsDatum = new DatePicker("Besichtigungsdatum");
    TimePicker besichtigungsUhrzeit = new TimePicker("Besichtigungsdatum");
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
    NumberField laufleistung = new NumberField("Laufleistung");

    Button saveButton = new Button("Speichern");
    Button deleteButton = new Button("Löschen");
    Button cancelButton = new Button("Abbrechen");
    Button resendButton = new Button("Auftrag nochmal versenden");

    public AuftragsdetailsView() {
        addClassName("auftragsDetails");

        add(
                auftragsDatum,
                schadenDatum,
                schadenOrt,
                gutachtenNummer,
                schadennummer,
                kennzeichenUG,
                besichtigungsOrt,
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
        auftragsDetailsBinder.forField(besichtigungsOrt).bind(
                Auftrag::getBesichtigungsort, Auftrag::setBesichtigungsort
        );


    }

    private HorizontalLayout createButtonLayout() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        resendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return new HorizontalLayout(saveButton, deleteButton, cancelButton, resendButton);
    }
}

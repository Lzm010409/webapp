package de.lukegoll.application.textextractor;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredTextEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;

import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.textextractor.coordinates.Coordinates;
import de.lukegoll.application.xml.xmlEntities.caseData.participantData.Contacts;
import de.lukegoll.application.xml.xmlEntities.caseData.participantData.Participant;
import de.lukegoll.application.xml.xmlEntities.constants.PersonType;
import org.jboss.logging.Logger;
import org.springframework.data.mapping.model.KotlinDefaultMask;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class PersonDataExtractor implements TextExtractor {
    private final Logger logger = Logger.getLogger(PersonDataExtractor.class);
    private Participant testLawyer = new Participant();

    public Set<Kontakt> extractTextFromFormular(File file) {
        try {

            PdfReader pdfReader = new PdfReader(file);
            PdfDocument doc = new PdfDocument(pdfReader);
            PdfAcroForm pdfAcroForm = PdfAcroForm.getAcroForm(doc, false);
            Map<String, PdfFormField> pdfFormFieldMap = pdfAcroForm.getFormFields();
            Kontakt kunde = buildCustomer(pdfFormFieldMap.get("Anspruchsteller").getValueAsString());
            kunde.setAnrede(pdfFormFieldMap.get("Anrede").getValueAsString());
            kunde.setTel(pdfFormFieldMap.get("Anspruchsteller Telefon").getValueAsString());
            kunde.setMail(pdfFormFieldMap.get("Anspruchsteller Mail").getValueAsString());
            kunde.setPersonType(PersonType.KUNDE);

            Set<Kontakt> kontakts = new HashSet<>();
            kontakts.add(kunde);
            logger.log(Logger.Level.INFO, String.format("Folgende Daten wurden ausgelesen: %s, %s %s  Tel.: %s, Mail: %s", kunde.getAnrede(),
                    kunde.getvName(), kunde.getnName(), kunde.getTel(), kunde.getMail()));

            return kontakts;
        } catch (FileNotFoundException e) {
            logger.log(Logger.Level.ERROR, "Auslesen nicht erfolgreich, folgender Fehler ist aufgetreten: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.log(Logger.Level.ERROR, "Auslesen nicht erfolgreich, folgender Fehler ist aufgetreten: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Kontakt> extractText(File file) throws IOException {
        StringBuilder builder = new StringBuilder();
        try {
            Rectangle rect;
            TextRegionEventFilter regionFilter;
            ITextExtractionStrategy strategy;
            String str;
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));

            rect = new Rectangle(Coordinates.AS.getX(), Coordinates.AS.getY(), Coordinates.AS.getWidth(), Coordinates.AS.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            Kontakt kunde = buildCustomer(str);

            if (kunde != null) {
                addGender(kunde, file);
                addContacts(kunde, file);
            }
            kunde.setPersonType(PersonType.KUNDE);
            logger.log(Logger.Level.INFO, String.format("Folgende Daten wurden ausgelesen: %s, Geschlecht: %s, Tel.: %s, Mail: %s", builder.toString(), kunde.getAnrede()
                    , kunde.getTel(), kunde.getMail()));
            Set<Kontakt> kontakts = new HashSet<>();
            kontakts.add(kunde);
            return kontakts;
        } catch (Exception e) {
            logger.log(Logger.Level.ERROR, "Auslesen nicht erfolgreich, folgender Fehler ist aufgetreten: " + e.getMessage());
            return null;
        }
    }

    private void addGender(Kontakt kunde, File file) throws IOException {
        Rectangle rect;
        TextRegionEventFilter regionFilter;
        ITextExtractionStrategy strategy;
        String str;
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
        rect = new Rectangle(Coordinates.GENDER.getX(), Coordinates.GENDER.getY(), Coordinates.GENDER.getWidth(), Coordinates.GENDER.getHeight());
        regionFilter = new TextRegionEventFilter(rect);
        strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
        str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
        kunde.setAnrede(str);
    }

    private Kontakt buildCustomer(String str) {
        List<String> list = new LinkedList<>(Arrays.asList(str.split("\n")));
        Kontakt participant = new Kontakt();


        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("AS:")) {
                //list.remove(i);
                continue;
            }
            if (isPlz(list.get(i))) {
                String[] arr = list.get(i).split(" ");
                participant.setPlz(arr[0]);
                participant.setStadt(arr[1]);
                //list.remove(i);
                continue;
            }
            if (isAdress(list.get(i))) {
                participant.setAdresse(list.get(i
                ));

            } else {
                setNames(participant, list.get(i));
            }


        }


        return participant;

    }

    private void setNames(Kontakt kunde, String string) {
        char[] chars = string.toCharArray();
        StringBuilder vnameBuilder = new StringBuilder();
        StringBuilder nnameBuilder = new StringBuilder();
        boolean vname = true;
        for (int i = 0; i < chars.length; i++) {
            if (!Character.isLetter(chars[i]) && vname == true && i != 0) {
                vname = false;
            }
            if (vname == true) {
                vnameBuilder.append(chars[i]);
            }
            if (vname == false) {
                nnameBuilder.append(chars[i]);
            }
        }

        kunde.setvName(vnameBuilder.toString());
        kunde.setnName(nnameBuilder.toString());
    }

    private boolean isAdress(String s) {
        boolean isAddress = false;
        List<String> tempList = new LinkedList<>(Arrays.asList(s.split(" ")));
        int digitCounter = 0;
        int letterCounter = 0;
        for (int i = 0; i < tempList.size(); i++) {
            char[] chars = tempList.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if (Character.isDigit(chars[j])) {
                    digitCounter += 1;
                } else {
                    letterCounter += 1;
                }
            }
            if (digitCounter < 5 && letterCounter < 2) {
                isAddress = true;
            }
            digitCounter = 0;
            letterCounter = 0;
        }
        return isAddress;
    }


    private void addContacts(Kontakt kunde, File file) throws IOException {
        Rectangle rect;
        TextRegionEventFilter regionFilter;
        ITextExtractionStrategy strategy;
        String str;
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
        Contacts contacts = new Contacts();
        String tel = "";
        String mail = "";
        rect = new Rectangle(Coordinates.TEL.getX(), Coordinates.TEL.getY(), Coordinates.TEL.getWidth(), Coordinates.TEL.getHeight());
        regionFilter = new TextRegionEventFilter(rect);
        strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
        str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
        tel = str;

        rect = new Rectangle(Coordinates.MAIL.getX(), Coordinates.MAIL.getY(), Coordinates.MAIL.getWidth(), Coordinates.MAIL.getHeight());
        regionFilter = new TextRegionEventFilter(rect);
        strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
        str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
        mail = str;


        kunde.setTel(tel);
        kunde.setMail(mail);
    }

    private boolean isPlz(String string) {
        boolean isPlz = false;
        int counter = 0;
        List<String> tempList = new LinkedList<>(Arrays.asList(string.split(" ")));
        for (int i = 0; i < tempList.size(); i++) {
            char[] chars = tempList.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if (Character.isDigit(chars[j])) {
                    counter += 1;
                }
                if (Character.isLetter(chars[j])) {
                    break;
                }
            }
            if (counter == 5) {
                isPlz = true;
                break;
            }
        }
        return isPlz;
    }

    public Participant getTestLawyer() {
        return testLawyer;
    }

    public void setTestLawyer(Participant testLawyer) {
        this.testLawyer = testLawyer;
    }


}

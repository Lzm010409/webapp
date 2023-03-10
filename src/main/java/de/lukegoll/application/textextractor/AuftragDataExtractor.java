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
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.Fahrzeug;
import de.lukegoll.application.data.entity.persons.Kunde;
import de.lukegoll.application.data.entity.persons.Rechtsanwalt;
import de.lukegoll.application.data.entity.persons.Versicherung;
import de.lukegoll.application.textextractor.coordinates.Coordinates;
import org.jboss.logging.Logger;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;


public class AuftragDataExtractor implements TextExtractor {
    public Auftrag extractTextFromFormular(File file) {
        try {
            PdfReader pdfReader = new PdfReader(file);
            PdfDocument doc = new PdfDocument(pdfReader);
            PdfAcroForm pdfAcroForm = PdfAcroForm.getAcroForm(doc, true);
            Map<String, PdfFormField> pdfFormFieldMap = pdfAcroForm.getFormFields();
            Auftrag auftrag = new Auftrag();
            auftrag.setAuftragsDatum(formatDate(pdfFormFieldMap.get("Auftragsdatum").getValueAsString()));
            auftrag.setBesichtigungsort(pdfFormFieldMap.get("Auftragsdatum").getValueAsString());
            auftrag.setSchadenhergang(pdfFormFieldMap.get("Schadenhergang").getValueAsString());
            auftrag.setSchadenDatum(formatDate(pdfFormFieldMap.get("Schadendatum").getValueAsString()));
            auftrag.setSchadenOrt(pdfFormFieldMap.get("Schadenort").getValueAsString());
            auftrag.setGutachtenNummer(pdfFormFieldMap.get("Gutachtennummer").getValueAsString());
            auftrag.setBedichtigungsDatum(pdfFormFieldMap.get("Besichtigungsdatum").getValueAsString());
            auftrag.setBesichtigungsUhrzeit(pdfFormFieldMap.get("Besichtigungsuhrzeit").getValueAsString());
            auftrag.setAuftragsBesonderheiten(pdfFormFieldMap.get("Notizen").getValueAsString());
            auftrag.setKennzeichenUG(pdfFormFieldMap.get("Kennzeichen-UG").getValueAsString());
            return auftrag;
        } catch (IOException e) {
            return null;

        }
    }

    private final Logger logger = Logger.getLogger(AuftragDataExtractor.class);

    @Override
    public Object extractText(File file) throws IOException {
        Rectangle rect;
        TextRegionEventFilter regionFilter;
        ITextExtractionStrategy strategy;
        String str;
        StringBuilder builder = new StringBuilder();
        Auftrag auftrag = new Auftrag();
        try {
            StringBuilder builder1 = new StringBuilder();
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
            rect = new Rectangle(Coordinates.ADATUM.getX(), Coordinates.ADATUM.getY(), Coordinates.ADATUM.getWidth(), Coordinates.ADATUM.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setAuftragsDatum(formatDate(str));
            rect = new Rectangle(Coordinates.SDATUM.getX(), Coordinates.SDATUM.getY(), Coordinates.SDATUM.getWidth(), Coordinates.SDATUM.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setSchadenDatum(formatDate(str));
            rect = new Rectangle(Coordinates.SORT.getX(), Coordinates.SORT.getY(), Coordinates.SORT.getWidth(), Coordinates.SORT.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setSchadenOrt(str);
            rect = new Rectangle(Coordinates.GNUMMER.getX(), Coordinates.GNUMMER.getY(), Coordinates.GNUMMER.getWidth(), Coordinates.GNUMMER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setGutachtenNummer(str);
            rect = new Rectangle(Coordinates.VERSICHERUNG.getX(), Coordinates.VERSICHERUNG.getY(), Coordinates.VERSICHERUNG.getWidth(), Coordinates.VERSICHERUNG.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            Versicherung versicherung = new Versicherung();
            builder1.append(str + "\t");
            versicherung.setnName(str);
            auftrag.setVersicherung(versicherung);
            rect = new Rectangle(Coordinates.SNUMMER.getX(), Coordinates.SNUMMER.getY(), Coordinates.SNUMMER.getWidth(), Coordinates.SNUMMER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setSchadennummer(str);
            rect = new Rectangle(Coordinates.KENNZEICHENUG.getX(), Coordinates.KENNZEICHENUG.getY(), Coordinates.KENNZEICHENUG.getWidth(), Coordinates.KENNZEICHENUG.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setKennzeichenUG(str);
            rect = new Rectangle(Coordinates.BORT.getX(), Coordinates.BORT.getY(), Coordinates.BORT.getWidth(), Coordinates.BORT.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setBesichtigungsort(str);
            rect = new Rectangle(Coordinates.BDATUM.getX(), Coordinates.BDATUM.getY(), Coordinates.BDATUM.getWidth(), Coordinates.BDATUM.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setBedichtigungsDatum(formatDate(str));
            rect = new Rectangle(Coordinates.BUHRZEIT.getX(), Coordinates.BUHRZEIT.getY(), Coordinates.BUHRZEIT.getWidth(), Coordinates.BUHRZEIT.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setBesichtigungsUhrzeit(str);
            rect = new Rectangle(Coordinates.NOTIZEN.getX(), Coordinates.NOTIZEN.getY(), Coordinates.NOTIZEN.getWidth(), Coordinates.NOTIZEN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setAuftragsBesonderheiten(str);
            rect = new Rectangle(Coordinates.SCHADENHERGANG.getX(), Coordinates.SCHADENHERGANG.getY(), Coordinates.SCHADENHERGANG.getWidth(), Coordinates.SCHADENHERGANG.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setSchadenhergang(str);
            rect = new Rectangle(Coordinates.RECHTSANWALT.getX(), Coordinates.RECHTSANWALT.getY(), Coordinates.RECHTSANWALT.getWidth(), Coordinates.RECHTSANWALT.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            Rechtsanwalt rechtsanwalt = new Rechtsanwalt();
            builder1.append(str + "\t");
            rechtsanwalt.setnName(str);
            auftrag.setRechtsanwalt(rechtsanwalt);

            auftrag.setFahrzeug(new VehicleDataExtractor().extractText(file));
            auftrag.setKunde(new PersonDataExtractor().extractText(file));
            logger.log(Logger.Level.INFO, String.format("Folgende Daten wurden ausgelesen: %s", builder1.toString()));
        } catch (Exception e) {
            logger.log(Logger.Level.WARN, "Es ist folgender Fehler aufgetreten: " + e.getMessage());
            e.printStackTrace();
        }

        return auftrag;
    }

    private String formatDate(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateTime = time.replace("/", "-");
        char[] chars = dateTime.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i]) || chars[i] == '-') {
                builder.append(chars[i]);
            }
        }
        dateTime = builder.toString();
        LocalDate date = LocalDate.parse(dateTime, formatter);
        return date.toString();
    }

    public Auftrag toAuftrag(Object object) {
        return (Auftrag) object;
    }

    /*
    Es m??ssen f??rs n??chste mal noch alle Logger ausgaben richtig konfiguriert werden
     */

    public static void main(String[] args) throws IOException {
        AuftragDataExtractor personDataExtractor = new AuftragDataExtractor();
        personDataExtractor.extractTextFromFormular(new File("/Users/lukegollenstede/Desktop/TEST/Aufnahmebogen Kfz-SAchversta??ndigenbu??rio Gollenstede.pdf"));
    }
}

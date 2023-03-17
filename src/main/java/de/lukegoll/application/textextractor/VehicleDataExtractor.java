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

import de.lukegoll.application.data.entity.Fahrzeug;
import de.lukegoll.application.data.entity.persons.Kunde;
import de.lukegoll.application.textextractor.coordinates.Coordinates;
import org.jboss.logging.Logger;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class VehicleDataExtractor implements TextExtractor {
    private final Logger logger = Logger.getLogger(VehicleDataExtractor.class);

    public Fahrzeug extractTextFromFormular(File file) {
        try {
            Fahrzeug fahrzeug = new Fahrzeug();
            PdfReader pdfReader = new PdfReader(file);
            PdfDocument doc = new PdfDocument(pdfReader);
            PdfAcroForm pdfAcroForm = PdfAcroForm.getAcroForm(doc, true);
            Map<String, PdfFormField> pdfFormFieldMap = pdfAcroForm.getFormFields();
           /* for (String key : pdfFormFieldMap.keySet()) {
                if (pdfFormFieldMap.get(key).getValueAsString().equalsIgnoreCase("off") || pdfFormFieldMap.get(key).getValueAsString() == "") {
                    pdfFormFieldMap.remove(key);
                }
            }*/
            fahrzeug.setAmtlKennzeichen(pdfFormFieldMap.get("Amtl Kennzeichen").getValueAsString());
            fahrzeug.setFahrzeugArt(pdfFormFieldMap.get("Fahrzeugart").getValueAsString());
            fahrzeug.setHersteller(pdfFormFieldMap.get("Hersteller").getValueAsString());
            fahrzeug.setTyp(pdfFormFieldMap.get("Typ Ausführung").getValueAsString());
            fahrzeug.setHsntsn(formatHsnTsn(pdfFormFieldMap.get("HSN TSN").getValueAsString()));
            fahrzeug.setFin(pdfFormFieldMap.get("FIN").getValueAsString());
            fahrzeug.setErstZulassung(formatDate(pdfFormFieldMap.get("Erste Zulassung").getValueAsString()));
            fahrzeug.setLetztZulassung(formatDate(pdfFormFieldMap.get("Letzte Zulassung").getValueAsString()));
            if (!(pdfFormFieldMap.get("Leistung").getValueAsString().isEmpty())) {
                fahrzeug.setLeistung(Integer.valueOf(pdfFormFieldMap.get("Leistung").getValueAsString()));
            }
            if (!(pdfFormFieldMap.get("Hubraum").getValueAsString().isEmpty())) {
                fahrzeug.setHubraum(Integer.valueOf(pdfFormFieldMap.get("Hubraum").getValueAsString()));
            }
            fahrzeug.setHu(pdfFormFieldMap.get("HU.1").getValueAsString());
            if (!(pdfFormFieldMap.get("Anzahl Vorbesitzer").getValueAsString().isEmpty())) {
                fahrzeug.setAnzVorbesitzer(Integer.parseInt(pdfFormFieldMap.get("Anzahl Vorbesitzer").getValueAsString()));
            }
            if (!(pdfFormFieldMap.get("Laufleistung").getValueAsString().isEmpty())) {
                fahrzeug.setKmStand(Integer.parseInt(pdfFormFieldMap.get("Laufleistung").getValueAsString()));
            }
            fahrzeug.setReifenVorne(pdfFormFieldMap.get("Bereifung vorne").getValueAsString());
            fahrzeug.setReifenHinten(pdfFormFieldMap.get("Bereifung hinten").getValueAsString());
            fahrzeug.setReifenHersteller(pdfFormFieldMap.get("Reifenhersteller").getValueAsString());
            fahrzeug.setProfilTiefe(pdfFormFieldMap.get("Profiltiefe").getValueAsString());
            fahrzeug.setSchadstoffKlasse(pdfFormFieldMap.get("Schadstoffklasse").getValueAsString());
            return fahrzeug;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Fahrzeug extractText(File file) throws IOException {
        try {
            Rectangle rect;
            TextRegionEventFilter regionFilter;
            ITextExtractionStrategy strategy;
            String str;
            StringBuilder builder = new StringBuilder();
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
            Fahrzeug vehicle = new Fahrzeug();

            logger.log(Logger.Level.INFO, "Auslesen des Textes aus: " + file.getAbsolutePath());

            rect = new Rectangle(Coordinates.KENNZEICHEN.getX(), Coordinates.KENNZEICHEN.getY(), Coordinates.KENNZEICHEN.getWidth(), Coordinates.KENNZEICHEN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setAmtlKennzeichen(formatLicencePlate(str));
            rect = new Rectangle(Coordinates.FHERSTELLER.getX(), Coordinates.FHERSTELLER.getY(), Coordinates.FHERSTELLER.getWidth(), Coordinates.FHERSTELLER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setHersteller(str);
            rect = new Rectangle(Coordinates.FART.getX(), Coordinates.FART.getY(), Coordinates.FART.getWidth(), Coordinates.FART.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setFahrzeugArt(str);
            rect = new Rectangle(Coordinates.FTYP.getX(), Coordinates.FTYP.getY(), Coordinates.FTYP.getWidth(), Coordinates.FTYP.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setTyp(str);
            rect = new Rectangle(Coordinates.FHSN.getX(), Coordinates.FHSN.getY(), Coordinates.FHSN.getWidth(), Coordinates.FHSN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setHsntsn(formatHsnTsn(str));
            rect = new Rectangle(Coordinates.FERSTZUL.getX(), Coordinates.FERSTZUL.getY(), Coordinates.FERSTZUL.getWidth(), Coordinates.FERSTZUL.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setErstZulassung(formatDate(str));
            rect = new Rectangle(Coordinates.FLETZTZUL.getX(), Coordinates.FLETZTZUL.getY(), Coordinates.FLETZTZUL.getWidth(), Coordinates.FLETZTZUL.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setLetztZulassung(formatDate(str));
            rect = new Rectangle(Coordinates.FLEISTUNG.getX(), Coordinates.FLEISTUNG.getY(), Coordinates.FLEISTUNG.getWidth(), Coordinates.FLEISTUNG.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setLeistung(Integer.valueOf(str));
            rect = new Rectangle(Coordinates.FHUBRAUM.getX(), Coordinates.FHUBRAUM.getY(), Coordinates.FHUBRAUM.getWidth(), Coordinates.FHUBRAUM.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setHubraum(Integer.valueOf(str));
            rect = new Rectangle(Coordinates.FHU.getX(), Coordinates.FHU.getY(), Coordinates.FHU.getWidth(), Coordinates.FHU.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setHu(str);
            rect = new Rectangle(Coordinates.FKILOMETER.getX(), Coordinates.FKILOMETER.getY(), Coordinates.FKILOMETER.getWidth(), Coordinates.FKILOMETER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setKmStand(Integer.valueOf(str));
           /* rect = new Rectangle(Coordinates.FFARBE.getX(), Coordinates.FFARBE.getY(), Coordinates.FFARBE.getWidth(), Coordinates.FFARBE.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setFarbe(str);*/
            rect = new Rectangle(Coordinates.FVBEREIFUNG.getX(), Coordinates.FVBEREIFUNG.getY(), Coordinates.FVBEREIFUNG.getWidth(), Coordinates.FVBEREIFUNG.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setReifenVorne(str);
            rect = new Rectangle(Coordinates.FHBEREIFUNG.getX(), Coordinates.FHBEREIFUNG.getY(), Coordinates.FHBEREIFUNG.getWidth(), Coordinates.FHBEREIFUNG.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setReifenHinten(str);
            rect = new Rectangle(Coordinates.FREIFENHERSTELLER.getX(), Coordinates.FREIFENHERSTELLER.getY(), Coordinates.FREIFENHERSTELLER.getWidth(), Coordinates.FREIFENHERSTELLER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setReifenHersteller(str);
            rect = new Rectangle(Coordinates.NBEHVORSCHADEN.getX(), Coordinates.NBEHVORSCHADEN.getY(), Coordinates.NBEHVORSCHADEN.getWidth(), Coordinates.NBEHVORSCHADEN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setNichtBehSchäden(str);
            rect = new Rectangle(Coordinates.BEHVORSCHADEN.getX(), Coordinates.BEHVORSCHADEN.getY(), Coordinates.BEHVORSCHADEN.getWidth(), Coordinates.BEHVORSCHADEN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setBehSchäden(str);
            rect = new Rectangle(Coordinates.FPROFILTIEFE.getX(), Coordinates.FPROFILTIEFE.getY(), Coordinates.FPROFILTIEFE.getWidth(), Coordinates.FPROFILTIEFE.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder.append(str + "\t");
            vehicle.setProfilTiefe(str);

            /**
             * Hier müssen unebdingt noch alle Daten die Enums enthalten ergänzt werden. z.B.: Karosserie, Allgemeinzustand, etc.
             */
            logger.log(Logger.Level.INFO, String.format("Folgende Daten wurden ausgelesen: %s", builder.toString()));
            return vehicle;
        } catch (Exception e) {
            logger.log(Logger.Level.ERROR, "Auslesen nicht erfolgreich, folgender Fehler ist aufgetreten: " + e.getMessage());
            return null;
        }

    }

    private String formatLicencePlate(String plate) {
        char[] chars = plate.toCharArray();
        boolean hitMinus = false;
        boolean hitFirstDigit = false;
        boolean startCodon = false;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (startCodon == true) {
                if (chars[i] != '-' && hitMinus == false) {
                    builder.append(chars[i]);
                }
                if (chars[i] == '-') {
                    hitMinus = true;
                    builder.append("-");
                }
                if (hitMinus == true && Character.isLetter(chars[i])) {
                    builder.append(chars[i]);
                }
                if (hitFirstDigit == false && Character.isDigit(chars[i])) {
                    builder.append("-");
                    builder.append(chars[i]);
                    hitFirstDigit = true;
                    continue;
                }
                if (Character.isDigit(chars[i]) && hitFirstDigit == true) {
                    builder.append(chars[i]);
                }
            } else {
                if (Character.isUpperCase(chars[i]) && Character.isUpperCase(chars[i + 1]) && chars[i + 2] == '-') {
                    startCodon = true;
                    builder.append(chars[i]);
                }
            }
        }
        return builder.toString();
    }

    private String formatDate(String time) {
        if (!time.isEmpty()) {
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
        } else {
            return new String("");
        }
    }

    private String formatHsnTsn(String hsn) {
        char[] chars = hsn.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i]) || Character.isDigit(chars[i])) {
                builder.append(chars[i]);
            }
        }
        return builder.toString();
    }

    private String formatMileage(String input) {
        char[] chars = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                builder.append(chars[i]);
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        VehicleDataExtractor personDataExtractor = new VehicleDataExtractor();
        personDataExtractor.extractTextFromFormular(new File("/Users/lukegollenstede/Desktop/TEST/Aufnahmebogen Kfz-SAchverständigenbürio Gollenstede.pdf"));
    }
}

package de.lukegoll.application.textextractor;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredTextEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.persons.Rechtsanwalt;
import de.lukegoll.application.data.entity.persons.Versicherung;
import de.lukegoll.application.textextractor.coordinates.Coordinates;
import org.jboss.logging.Logger;


import java.io.File;
import java.io.IOException;


public class AuftragDataExtractor implements TextExtractor {

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
            auftrag.setAuftragsDatum(str);
            rect = new Rectangle(Coordinates.SDATUM.getX(), Coordinates.SDATUM.getY(), Coordinates.SDATUM.getWidth(), Coordinates.SDATUM.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            builder1.append(str + "\t");
            auftrag.setSchadenDatum(str);
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
            auftrag.setBedichtigungsDatum(str);
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

    public Auftrag toAuftrag(Object object) {
        return (Auftrag) object;
    }

    /*
    Es müssen fürs nächste mal noch alle Logger ausgaben richtig konfiguriert werden
     */

    public static void main(String[] args) throws IOException {
        AuftragDataExtractor personDataExtractor = new AuftragDataExtractor();
        personDataExtractor.extractText(new File("/Users/lukegollenstede/Desktop/TEST/TEST.pdf"));
    }
}

package de.lukegoll.application.pdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfTextAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.Fahrzeug;
import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.xml.xmlEntities.constants.PersonType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class PdfEditor {


    private Auftrag auftrag;
    @Value("#{${coordinate.map}}")
    private Map<String, List<String>> coordinateMap;
    private String anrede;
    private String name;
    private String anschrift;
    private String stadt;
    private String fahrzeug;
    private String amtlKennzeichen;
    private String fin;
    private String schadendatum;
    private String gutachtenNummer;
    private String kennzeichenUG;
    private String versicherung;

    public PdfEditor(

    ) {


    }

    private File editPdf(String file) {
        File outputFile = new File("/Users/lukegollenstede/Desktop/TEST/Files/abtretung " + Math.random() * 100 + ".pdf");
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfReader(file), new PdfWriter(outputFile));
            PdfCanvas canvas = new PdfCanvas(pdfDocument.getFirstPage());
            PdfFont font = PdfFontFactory.createFont("/Users/lukegollenstede/Downloads/arial/arial.ttf");
            Rectangle rectangle;
            Text text;
            Paragraph paragraph;
            List<String> list;

            for (String keys : coordinateMap.keySet()) {
                switch (keys) {
                    case ("anrede"):
                        list = coordinateMap.get(keys);
                        rectangle = new Rectangle(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                        text = new Text(getAnrede()).setFont(font).setFontSize(8);
                        paragraph = new Paragraph().add(text);
                        new Canvas(canvas, rectangle).add(paragraph);
                        canvas.rectangle(rectangle);
                        break;
                    case ("name"):
                        list = coordinateMap.get(keys);
                        rectangle = new Rectangle(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                        text = new Text(getName()).setFont(font).setFontSize(8);
                        paragraph = new Paragraph().add(text);
                        new Canvas(canvas, rectangle).add(paragraph);
                        canvas.rectangle(rectangle);
                        break;
                    case ("anschrift"):
                        list = coordinateMap.get(keys);
                        rectangle = new Rectangle(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                        text = new Text(getAnschrift()).setFont(font).setFontSize(8);
                        paragraph = new Paragraph().add(text);
                        new Canvas(canvas, rectangle).add(paragraph);
                        canvas.rectangle(rectangle);
                        break;
                    case ("stadt"):
                        list = coordinateMap.get(keys);
                        rectangle = new Rectangle(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                        text = new Text(getStadt()).setFont(font).setFontSize(8);
                        paragraph = new Paragraph().add(text);
                        new Canvas(canvas, rectangle).add(paragraph);
                        canvas.rectangle(rectangle);
                        break;
                    case ("fahrzeug"):
                        list = coordinateMap.get(keys);
                        rectangle = new Rectangle(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                        text = new Text(getFahrzeug()).setFont(font).setFontSize(8);
                        paragraph = new Paragraph().add(text);
                        new Canvas(canvas, rectangle).add(paragraph);
                        canvas.rectangle(rectangle);
                        break;
                    case ("amtlKennzeichen"):
                        list = coordinateMap.get(keys);
                        rectangle = new Rectangle(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                        text = new Text(getAmtlKennzeichen()).setFont(font).setFontSize(8);
                        paragraph = new Paragraph().add(text);
                        new Canvas(canvas, rectangle).add(paragraph);
                        canvas.rectangle(rectangle);
                        break;
                    case ("fin"):
                        list = coordinateMap.get(keys);
                        rectangle = new Rectangle(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                        text = new Text(getFin()).setFont(font).setFontSize(8);
                        paragraph = new Paragraph().add(text);
                        new Canvas(canvas, rectangle).add(paragraph);
                        canvas.rectangle(rectangle);
                        break;
                    case ("schadendatum"):
                        list = coordinateMap.get(keys);
                        rectangle = new Rectangle(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                        text = new Text(getSchadendatum()).setFont(font).setFontSize(8);
                        paragraph = new Paragraph().add(text);
                        new Canvas(canvas, rectangle).add(paragraph);
                        canvas.rectangle(rectangle);
                        break;
                    case ("gutachtenNummer"):
                        list = coordinateMap.get(keys);
                        rectangle = new Rectangle(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                        text = new Text(getGutachtenNummer()).setFont(font).setFontSize(8);
                        paragraph = new Paragraph().add(text);
                        new Canvas(canvas, rectangle).add(paragraph);
                        canvas.rectangle(rectangle);
                        break;
                    case ("kennzeichenUG"):
                        list = coordinateMap.get(keys);
                        rectangle = new Rectangle(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                        text = new Text(getKennzeichenUG()).setFont(font).setFontSize(8);
                        paragraph = new Paragraph().add(text);
                        new Canvas(canvas, rectangle).add(paragraph);
                        canvas.rectangle(rectangle);
                        break;
                    case ("versicherung"):
                        list = coordinateMap.get(keys);
                        rectangle = new Rectangle(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                        text = new Text(getVersicherung()).setFont(font).setFontSize(8);
                        paragraph = new Paragraph().add(text);
                        new Canvas(canvas, rectangle).add(paragraph);
                        canvas.rectangle(rectangle);
                        break;
                }
            }
            pdfDocument.close();
        } catch (Exception e) {

        } finally {
            return outputFile;
        }
    }

    public File generateAbtretung(Auftrag auftrag, String file) {
        configurePdfEditor(auftrag);
        return editPdf(file);
    }

    public byte[] generateAbtretungAsByteArray(Auftrag auftrag, String file) {
        configurePdfEditor(auftrag);
        try {
            File abtretungsFile = editPdf(file);
            byte[] bytes = Files.readAllBytes(abtretungsFile.toPath());
            abtretungsFile.delete();
            return bytes;
        } catch (IOException e) {
            return null;
        }

    }

    private void configurePdfEditor(Auftrag auftrag) {
        this.auftrag = auftrag;
        Kontakt kunde = new Kontakt();
        Kontakt versicherung = new Kontakt();
        Iterator<Kontakt> iterator = auftrag.getKontakte().iterator();
        while (iterator.hasNext()) {
            Kontakt kontakt = iterator.next();
            if (kontakt.getPersonType() == PersonType.KUNDE) {
                kunde = kontakt;
            }
            if (kontakt.getPersonType() == PersonType.VERSICHERUNG) {
                versicherung = kontakt;
            }
        }

        if (kunde != null) {
            this.anrede = kunde.getAnrede();
            this.name = kunde.getvName() + " " + kunde.getnName();
            this.anschrift = kunde.getAdresse();
            this.stadt = kunde.getPlz() + " " + kunde.getStadt();
            Fahrzeug fahrzeug1 = auftrag.getFahrzeug();
            this.fahrzeug = fahrzeug1.getHersteller() + " " + fahrzeug1.getTyp();
            this.amtlKennzeichen = fahrzeug1.getAmtlKennzeichen();
            this.fin = fahrzeug1.getFin();
            this.schadendatum = auftrag.getSchadenDatum();
            this.gutachtenNummer = auftrag.getGutachtenNummer();
            this.kennzeichenUG = auftrag.getKennzeichenUG();
            if (versicherung.getvName() != null) {
                this.versicherung = versicherung.getvName();
            }

        }
    }


    public Auftrag getAuftrag() {
        return auftrag;
    }

    public void setAuftrag(Auftrag auftrag) {
        this.auftrag = auftrag;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getFahrzeug() {
        return fahrzeug;
    }

    public void setFahrzeug(String fahrzeug) {
        this.fahrzeug = fahrzeug;
    }

    public String getAmtlKennzeichen() {
        return amtlKennzeichen;
    }

    public void setAmtlKennzeichen(String amtlKennzeichen) {
        this.amtlKennzeichen = amtlKennzeichen;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getSchadendatum() {
        return schadendatum;
    }

    public void setSchadendatum(String schadendatum) {
        this.schadendatum = schadendatum;
    }


    public String getGutachtenNummer() {
        return gutachtenNummer;
    }

    public void setGutachtenNummer(String gutachtenNummer) {
        this.gutachtenNummer = gutachtenNummer;
    }

    public String getKennzeichenUG() {
        return kennzeichenUG;
    }

    public void setKennzeichenUG(String kennzeichenUG) {
        this.kennzeichenUG = kennzeichenUG;
    }

    public String getVersicherung() {
        return versicherung;
    }

    public void setVersicherung(String versicherung) {
        this.versicherung = versicherung;
    }
}

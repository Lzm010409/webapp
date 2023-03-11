package de.lukegoll.application.test;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestClas {

    public static void main (String[]args){
        try {
            PdfReader pdfReader = new PdfReader(new File("/Users/lukegollenstede/Desktop/TEST/Aufnahmebogen Kfz-SAchverständigenbürio Gollenstede.pdf"));
            PdfDocument doc = new PdfDocument(pdfReader);
            PdfAcroForm pdfAcroForm = PdfAcroForm.getAcroForm(doc,true);
            Map<String, PdfFormField> pdfFormFieldMap =pdfAcroForm.getFormFields();

            for(String key: pdfFormFieldMap.keySet()){
                System.out.println("Feld: " + key + " mit Wert: " + pdfFormFieldMap.get(key).getValueAsString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

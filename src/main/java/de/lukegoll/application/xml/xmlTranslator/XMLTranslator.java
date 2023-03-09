package de.lukegoll.application.xml.xmlTranslator;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.Fahrzeug;
import de.lukegoll.application.xml.xmlEntities.Case;
import de.lukegoll.application.xml.xmlEntities.Document;
import de.lukegoll.application.xml.xmlEntities.adapter.LocalDateTimeAdapter;
import de.lukegoll.application.xml.xmlEntities.caseData.Admin_Data;
import de.lukegoll.application.xml.xmlEntities.caseData.Vehicle;
import de.lukegoll.application.xml.xmlEntities.caseData.participantData.*;
import de.lukegoll.application.xml.xmlEntities.caseData.participantData.Country;
import de.lukegoll.application.xml.xmlEntities.constants.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class XMLTranslator {

    public XMLTranslator() {

    }

    public Vehicle auftragToVehicle(Auftrag auftrag) {
        Vehicle vehicle = new Vehicle();
        Fahrzeug fahrzeug = auftrag.getFahrzeug();
        vehicle.setManufaturer(fahrzeug.getHersteller());
        vehicle.setModel(fahrzeug.getTyp());
        vehicle.setPlate_number(fahrzeug.getAmtlKennzeichen());
        vehicle.setPlate_number_opponent(auftrag.getKennzeichenUG());
        vehicle.setVin(fahrzeug.getFin());
        vehicle.setKba_numbers(fahrzeug.getHsntsn());
        vehicle.setFirst_registrations(fahrzeug.getErstZulassung());
        vehicle.setMileage_read(fahrzeug.getKmStand());
        vehicle.setMileage_read_unit_code(Unit.KM);

        return vehicle;
    }

    public Admin_Data auftragToAdminData(Auftrag auftrag) {
        Admin_Data admin_data = new Admin_Data();
        admin_data.setCase_no(auftrag.getGutachtenNummer());
        try {
            admin_data.setClaim_date(new LocalDateTimeAdapter().unmarshal(auftrag.getSchadenDatum()));
            admin_data.setClaim_location(auftrag.getSchadenOrt());
            admin_data.setClaim_number(auftrag.getSchadennummer());
            admin_data.setCase_date_of_order(new LocalDateTimeAdapter().unmarshal(auftrag.getAuftragsDatum()));
            admin_data.setCase_ordered_by("Anspruchssteller");
            admin_data.setClaim_type(ClaimType.LIABILITYDAMAGE.getType());
            admin_data.setClaim_type_id(ClaimType.LIABILITYDAMAGE.getId());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return admin_data;
    }

    public Participants auftragToParticipants(Auftrag auftrag) {
        Participants participants = new Participants();
        List<Participant> participantsList = new LinkedList<>();

        Contacts versicherungsContacts = new Contacts();
        Contacts rechtsanwaltsContacts = new Contacts();

        try {
            Participant kunde = new Participant();
            Address kundenAddress = new Address();
            Contacts kundenContacts = new Contacts();
            List<Contact> contactList = new LinkedList<>();
            Contact tel = new Contact();
            Contact mail = new Contact();
            tel.setValue(auftrag.getKunde().getTel());
            tel.setName(ContactType.MOBILE.toString());
            mail.setValue(auftrag.getKunde().getMail());
            mail.setName(ContactType.MAIL.toString());
            contactList.add(tel);
            contactList.add(mail);
            kundenContacts.setContact(contactList);
            kundenAddress.setStreet(auftrag.getKunde().getAdresse());
            kundenAddress.setNumber(parseNumber(auftrag.getKunde().getAdresse()));
            kundenAddress.setPostalcode(auftrag.getKunde().getPlz());
            kundenAddress.setCity(auftrag.getKunde().getStadt());
            kundenAddress.setCountry(new Country(CountryCode.DE));
            kundenAddress.setContacts(kundenContacts);
            kunde.setAddress(kundenAddress);
            String type = ParticipantType.CU.toString() + "," + ParticipantType.CL.toString() + "," + ParticipantType.VO.toString();
            kunde.setType(type);
            kunde.setGender(auftrag.getKunde().getAnrede());
            kunde.setName1(auftrag.getKunde().getvName() + " " + auftrag.getKunde().getnName());
            if (auftrag.getKunde().getAnrede().equalsIgnoreCase("firma")) {
                kunde.setTax_deduction(true
                );
            }
            participantsList.add(kunde);
        } catch (Exception e) {
            e.getMessage();
        }
        try {
            Participant versicherung = new Participant();
            Address versicherungsAddress = new Address();
            Contacts versicherungContacts = new Contacts();
            List<Contact> contactList = new LinkedList<>();
            Contact tel = new Contact();
            Contact mail = new Contact();
            tel.setValue(auftrag.getVersicherung().getTel());
            tel.setName(ContactType.MOBILE.toString());
            mail.setValue(auftrag.getVersicherung().getMail());
            mail.setName(ContactType.MAIL.toString());
            contactList.add(tel);
            contactList.add(mail);
            versicherungContacts.setContact(contactList);
            versicherungsAddress.setStreet(auftrag.getVersicherung().getAdresse());
            versicherungsAddress.setNumber(parseNumber(auftrag.getVersicherung().getAdresse()));
            versicherungsAddress.setPostalcode(auftrag.getVersicherung().getPlz());
            versicherungsAddress.setCity(auftrag.getVersicherung().getStadt());
            versicherungsAddress.setCountry(new Country(CountryCode.DE));
            versicherungsAddress.setContacts(versicherungContacts);
            versicherung.setAddress(versicherungsAddress);
            java.lang.String type = ParticipantType.IS.name();
            versicherung.setType(type);
            versicherung.setGender(auftrag.getVersicherung().getAnrede());
            versicherung.setName1(auftrag.getVersicherung().getvName() + " " + auftrag.getVersicherung().getnName());
            if (auftrag.getVersicherung().getAnrede().equalsIgnoreCase("firma")) {
                versicherung.setTax_deduction(true
                );
            }
            participantsList.add(versicherung);
        } catch (Exception e) {
            e.getMessage();
        }
        try {
            Participant rechtsanwalt = new Participant();
            Address rechtsanwaltAddress = new Address();
            Contacts rechtsanwaltContacts = new Contacts();
            List<Contact> contactList = new LinkedList<>();
            Contact tel = new Contact();
            Contact mail = new Contact();
            tel.setValue(auftrag.getRechtsanwalt().getTel());
            tel.setName(ContactType.MOBILE.toString());
            mail.setValue(auftrag.getRechtsanwalt().getMail());
            mail.setName(ContactType.MAIL.toString());
            contactList.add(tel);
            contactList.add(mail);
            rechtsanwaltContacts.setContact(contactList);
            rechtsanwaltAddress.setStreet(auftrag.getRechtsanwalt().getAdresse());
            rechtsanwaltAddress.setNumber(parseNumber(auftrag.getRechtsanwalt().getAdresse()));
            rechtsanwaltAddress.setPostalcode(auftrag.getRechtsanwalt().getPlz());
            rechtsanwaltAddress.setCity(auftrag.getRechtsanwalt().getStadt());
            rechtsanwaltAddress.setCountry(new Country(CountryCode.DE));
            rechtsanwaltAddress.setContacts(rechtsanwaltContacts);
            rechtsanwalt.setAddress(rechtsanwaltAddress);
            java.lang.String type = ParticipantType.LA.name();
            rechtsanwalt.setType(type);
            rechtsanwalt.setGender(auftrag.getRechtsanwalt().getAnrede());
            rechtsanwalt.setName1(auftrag.getRechtsanwalt().getvName() + " " + auftrag.getRechtsanwalt().getnName());
            if (auftrag.getRechtsanwalt().getAnrede().equalsIgnoreCase("firma")) {
                rechtsanwalt.setTax_deduction(true
                );
            }
            participantsList.add(rechtsanwalt);
        } catch (Exception e) {
            e.getMessage();
        }
        participants.setParticipant(participantsList);

        return participants;
    }


    public void writeXmlFile(Auftrag auftrag, File file) {
        Document document = new Document();
        Case fall = new Case();
        fall.setAdmin_data(auftragToAdminData(auftrag));
        fall.setVehicle(auftragToVehicle(auftrag));
        fall.setParticipants(auftragToParticipants(auftrag));
        document.setFall(fall);
        try {
            JAXBContext jc = JAXBContext.newInstance(Document.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(document, new FileOutputStream(file));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public java.lang.String parseNumber(java.lang.String kunde) {
        char[] chars = kunde.toCharArray();
        StringBuilder builder = new StringBuilder();
        boolean number = false;
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                number = true;
                builder.append(chars[i]);
            }
            if (number == true) {
                builder.append(chars[i]);
            }
        }
        return builder.toString();
    }

    @Async
    public ListenableFuture<List<String>> writeXmlRequests(List<Auftrag> auftrag) {
        List<String> xmlList = new LinkedList<>();
        try {
            for (Auftrag auftrag1 : auftrag) {
                String filePath = "src/main/resources/xmlFiles/" + generateRandomString() + ".xml";
                Document document = new Document();
                Case fall = new Case();
                fall.setAdmin_data(auftragToAdminData(auftrag1));
                fall.setVehicle(auftragToVehicle(auftrag1));
                fall.setParticipants(auftragToParticipants(auftrag1));
                document.setFall(fall);
                try {
                    JAXBContext jc = JAXBContext.newInstance(Document.class);
                    Marshaller marshaller = jc.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    marshaller.marshal(document, new FileOutputStream(filePath));
                    xmlList.add(filePath);
                } catch (JAXBException e) {
                    throw new RuntimeException(e);
                }
            }
            return AsyncResult.forValue(xmlList);
        } catch (Exception e) {
            return AsyncResult.forExecutionException(e);
        }


    }

    public String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}

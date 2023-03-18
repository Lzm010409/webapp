package de.lukegoll.application.xml.xmlTranslator;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.Fahrzeug;
import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.xml.xmlEntities.Case;
import de.lukegoll.application.xml.xmlEntities.ClaimnetDistribution;
import de.lukegoll.application.xml.xmlEntities.Document;
import de.lukegoll.application.xml.xmlEntities.adapter.LocalDateTimeAdapter;
import de.lukegoll.application.xml.xmlEntities.caseData.Admin_Data;
import de.lukegoll.application.xml.xmlEntities.caseData.ClaimnetInfo;
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
import java.time.LocalDateTime;
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
        try {
            Object[] kontakts = auftrag.getKontakte().toArray();
            for (int i = 0; i < kontakts.length; i++) {
                if (kontakts[i] instanceof Kontakt) {
                    if (((Kontakt) kontakts[i]).getPersonType() == PersonType.KUNDE) {
                        Participant kunde = new Participant();
                        Address kundenAddress = new Address();
                        Contacts kundenContacts = new Contacts();
                        List<Contact> contactList = new LinkedList<>();
                        Contact tel = new Contact();
                        Contact mail = new Contact();
                        tel.setValue(((Kontakt) kontakts[i]).getTel());
                        tel.setName(ContactType.MOBILE.toString());
                        mail.setValue(((Kontakt) kontakts[i]).getMail());
                        mail.setName(ContactType.MAIL.toString());
                        contactList.add(tel);
                        contactList.add(mail);
                        kundenContacts.setContact(contactList);
                        kundenAddress.setStreet(((Kontakt) kontakts[i]).getAdresse());
                        kundenAddress.setNumber(parseNumber(((Kontakt) kontakts[i]).getAdresse()));
                        kundenAddress.setPostalcode(((Kontakt) kontakts[i]).getPlz());
                        kundenAddress.setCity(((Kontakt) kontakts[i]).getStadt());
                        kundenAddress.setCountry(new Country(CountryCode.DE));
                        kundenAddress.setContacts(kundenContacts);
                        kunde.setAddress(kundenAddress);
                        String type = ParticipantType.CU.toString() + "," + ParticipantType.CL.toString() + "," + ParticipantType.VO.toString();
                        kunde.setType(type);
                        kunde.setGender(((Kontakt) kontakts[i]).getAnrede());
                        kunde.setName1(((Kontakt) kontakts[i]).getvName() + " " + ((Kontakt) kontakts[i]).getnName());
                        if (((Kontakt) kontakts[i]).getAnrede().equalsIgnoreCase("firma")) {
                            kunde.setTax_deduction(true
                            );
                        }
                        participantsList.add(kunde);
                    }
                    if (((Kontakt) kontakts[i]).getPersonType() == PersonType.VERSICHERUNG) {
                        Participant versicherung = new Participant();
                        Address versicherungsAddress = new Address();
                        Contacts versicherungContacts = new Contacts();
                        List<Contact> contactList = new LinkedList<>();
                        Contact tel = new Contact();
                        Contact mail = new Contact();
                        tel.setValue(((Kontakt) kontakts[i]).getTel());
                        tel.setName(ContactType.MOBILE.toString());
                        mail.setValue(((Kontakt) kontakts[i]).getMail());
                        mail.setName(ContactType.MAIL.toString());
                        contactList.add(tel);
                        contactList.add(mail);
                        versicherungContacts.setContact(contactList);
                        versicherungsAddress.setStreet(((Kontakt) kontakts[i]).getAdresse());
                        versicherungsAddress.setNumber(parseNumber(((Kontakt) kontakts[i]).getAdresse()));
                        versicherungsAddress.setPostalcode(((Kontakt) kontakts[i]).getPlz());
                        versicherungsAddress.setCity(((Kontakt) kontakts[i]).getStadt());
                        versicherungsAddress.setCountry(new Country(CountryCode.DE));
                        versicherungsAddress.setContacts(versicherungContacts);
                        versicherung.setAddress(versicherungsAddress);
                        String type = ParticipantType.IS.name();
                        versicherung.setType(type);
                        versicherung.setGender(((Kontakt) kontakts[i]).getAnrede());
                        versicherung.setName1(((Kontakt) kontakts[i]).getvName() + " " + ((Kontakt) kontakts[i]).getnName());
                        if (((Kontakt) kontakts[i]).getAnrede().equalsIgnoreCase("firma")) {
                            versicherung.setTax_deduction(true
                            );
                        }
                        participantsList.add(versicherung);
                    }
                    if (((Kontakt) kontakts[i]).getPersonType() == PersonType.RECHTSANWALT) {
                        Participant rechtsanwalt = new Participant();
                        Address rechtsanwaltAddress = new Address();
                        Contacts rechtsanwaltContacts = new Contacts();
                        List<Contact> contactList = new LinkedList<>();
                        Contact tel = new Contact();
                        Contact mail = new Contact();
                        tel.setValue(((Kontakt) kontakts[i]).getTel());
                        tel.setName(ContactType.MOBILE.toString());
                        mail.setValue(((Kontakt) kontakts[i]).getMail());
                        mail.setName(ContactType.MAIL.toString());
                        contactList.add(tel);
                        contactList.add(mail);
                        rechtsanwaltContacts.setContact(contactList);
                        rechtsanwaltAddress.setStreet(((Kontakt) kontakts[i]).getAdresse());
                        rechtsanwaltAddress.setNumber(parseNumber(((Kontakt) kontakts[i]).getAdresse()));
                        rechtsanwaltAddress.setPostalcode(((Kontakt) kontakts[i]).getPlz());
                        rechtsanwaltAddress.setCity(((Kontakt) kontakts[i]).getStadt());
                        rechtsanwaltAddress.setCountry(new Country(CountryCode.DE));
                        rechtsanwaltAddress.setContacts(rechtsanwaltContacts);
                        rechtsanwalt.setAddress(rechtsanwaltAddress);
                        java.lang.String type = ParticipantType.LA.name();
                        rechtsanwalt.setType(type);
                        rechtsanwalt.setGender(((Kontakt) kontakts[i]).getAnrede());
                        rechtsanwalt.setName1(((Kontakt) kontakts[i]).getvName() + " " + ((Kontakt) kontakts[i]).getnName());
                        if (((Kontakt) kontakts[i]).getAnrede().equalsIgnoreCase("firma")) {
                            rechtsanwalt.setTax_deduction(true
                            );
                        }
                        participantsList.add(rechtsanwalt);
                    }
                }
            }
        } catch (Exception e) {

        }
        participants.setParticipant(participantsList);

        return participants;
    }

    public ClaimnetInfo auftragToClaimnetInfo(Auftrag auftrag) {
        ClaimnetInfo claimnetInfo = new ClaimnetInfo();
        try {
            claimnetInfo.setOrder_type("Gutachten");
            claimnetInfo.setDamage_circumstances_gdv(auftrag.getSchadenhergang());
            claimnetInfo.setDamage_location(auftrag.getSchadenOrt());
            claimnetInfo.setDamage_reporting_date(new LocalDateTimeAdapter().unmarshal(auftrag.getSchadenDatum()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return claimnetInfo;
    }

    public ClaimnetDistribution buildClaimentDistribution(String orderID) {
        ClaimnetDistribution claimnetDistribution = new ClaimnetDistribution();
        claimnetDistribution.setReceiver_id("DYCb081c6ac-37a6-4368-bbf0-016eda40d8a3");
        claimnetDistribution.setSender_id("TestSystem-Gollenstede");
        claimnetDistribution.setOrder_id(orderID);
        return claimnetDistribution;
    }

    public void writeXmlFile(Auftrag auftrag, File file) {
        Document document = new Document();
        Case fall = new Case();
        fall.setAdmin_data(auftragToAdminData(auftrag));
        fall.setVehicle(auftragToVehicle(auftrag));
        fall.setParticipants(auftragToParticipants(auftrag));
        fall.setClaimnetInfo(auftragToClaimnetInfo(auftrag));
        document.setClaimnetDistribution(buildClaimentDistribution("TEERETS"));
        document.setFall(fall);
        LocalDateTime localDateTime = LocalDateTime.now();
        document.setDate(localDateTime);
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
                fall.setClaimnetInfo(auftragToClaimnetInfo(auftrag1));
                document.setClaimnetDistribution(buildClaimentDistribution("TEERETS"));
                document.setFall(fall);
                LocalDateTime localDateTime = LocalDateTime.now();
                document.setDate(localDateTime);
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

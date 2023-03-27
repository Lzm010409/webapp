package de.lukegoll.application.xml.xmlTranslator;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.Fahrzeug;
import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.data.enums.AuftragStatus;
import de.lukegoll.application.xml.xmlEntities.Case;
import de.lukegoll.application.xml.xmlEntities.ClaimnetDistribution;
import de.lukegoll.application.xml.xmlEntities.Document;
import de.lukegoll.application.xml.xmlEntities.adapter.LocalDateTimeAdapter;
import de.lukegoll.application.xml.xmlEntities.caseData.*;
import de.lukegoll.application.xml.xmlEntities.caseData.participantData.*;
import de.lukegoll.application.xml.xmlEntities.caseData.participantData.Country;
import de.lukegoll.application.xml.xmlEntities.constants.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class XMLTranslator {

    public XMLTranslator() {

    }

    public Vehicle auftragToVehicle(Auftrag auftrag) {
        Vehicle vehicle = new Vehicle();
        Fahrzeug fahrzeug = auftrag.getFahrzeug();
        vehicle.setManufacturer(fahrzeug.getHersteller());
        vehicle.setModel(fahrzeug.getTyp());
        vehicle.setPlate_number(fahrzeug.getAmtlKennzeichen());
        vehicle.setPlate_number_opponent(auftrag.getKennzeichenUG());
        vehicle.setVin(fahrzeug.getFin());
        vehicle.setKba_numbers(fahrzeug.getHsntsn());
        vehicle.setFirst_registration(fahrzeug.getErstZulassung());
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

    public Attachments auftragToAttachments(Auftrag auftrag) {
        List<Attachment> attachments = new LinkedList<>();
        String encodedString = Base64.getEncoder().encodeToString(auftrag.getData());
        Attachment attachment = new Attachment();
        attachment.setFilename("Abtretungserklärung");
        attachment.setFile_extension("PDF");
        attachment.setBase64(encodedString);
        attachments.add(attachment);
        return new Attachments(attachments);

    }

    public Participants auftragToParticipants(Auftrag auftrag) {
        Participants participants = new Participants();
        List<Participant> participantsList = new LinkedList<>();
        try {
            Iterator<Kontakt> kontaktIterator = auftrag.getKontakte().iterator();
            while (kontaktIterator.hasNext()) {
                Kontakt kontakt = kontaktIterator.next();
                Participant participant = new Participant();
                Address address = new Address();
                Contacts contacts = new Contacts();
                List<Contact> contactList = new LinkedList<>();
                participant.setGender(kontakt.getAnrede());
                participant.setName1(kontakt.getvName() + " " + kontakt.getnName());
                Contact tel = new Contact();
                Contact mail = new Contact();
                tel.setValue(kontakt.getTel());
                tel.setName(ContactType.MOBILE.toString());
                mail.setValue(kontakt.getMail());
                mail.setName(ContactType.MAIL.toString());
                contactList.add(tel);
                contactList.add(mail);
                contacts.setContact(contactList);
                address.setStreet(kontakt.getAdresse());
                if (parseNumber(kontakt.getAdresse()) != null) {
                    address.setNumber(parseNumber(kontakt.getAdresse()));
                }
                address.setPostalcode(kontakt.getPlz());
                address.setCity(kontakt.getStadt());
                address.setCountry(new Country(CountryCode.DE));
                address.setContacts(contacts);
                participant.setAddress(address);
                if (kontakt.getPersonType() == PersonType.KUNDE) {
                    String type = ParticipantType.CU.toString() + "," + ParticipantType.CL.toString() + "," + ParticipantType.VO.toString();
                    participant.setType(type);
                }
                if (kontakt.getPersonType() == PersonType.RECHTSANWALT) {
                    String type = ParticipantType.LA.name();
                    participant.setType(type);
                }
                if (kontakt.getPersonType() == PersonType.VERSICHERUNG) {
                    String type = ParticipantType.IS.name();
                    participant.setType(type);
                }
                participantsList.add(participant);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public String parseNumber(String kunde) {
        try {
            char[] chars = kunde.toCharArray();
            StringBuilder builder = new StringBuilder();
            boolean number = false;
            for (int i = 0; i < chars.length; i++) {
                if (Character.isDigit(chars[i])) {
                    number = true;
                    builder.append(chars[i]);
                    continue;
                }
                if (number == true) {
                    builder.append(chars[i]);
                }
            }
            return builder.toString();
        } catch (Exception e) {
            return null;
        }
    }


    public String writeXmlRequest(Auftrag auftrag) {
        try {
            String xmlString;
            String filePath = "src/main/resources/xmlFiles/" + generateRandomString() + ".xml";
            Document document = new Document();
            Case fall = new Case();
            fall.setAdmin_data(auftragToAdminData(auftrag));
            fall.setVehicle(auftragToVehicle(auftrag));
            fall.setParticipants(auftragToParticipants(auftrag));
            fall.setClaimnetInfo(auftragToClaimnetInfo(auftrag));
            fall.setAttachments(auftragToAttachments(auftrag));
            document.setClaimnetDistribution(buildClaimentDistribution("TEERETS"));
            document.setFall(fall);
            LocalDateTime localDateTime = LocalDateTime.now();
            document.setDate(localDateTime);
            try {
                JAXBContext jc = JAXBContext.newInstance(Document.class);
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                //marshaller.marshal(document, new FileOutputStream(filePath));
                StringWriter sw = new StringWriter();
                marshaller.marshal(document, sw);
                xmlString = sw.toString();
                return xmlString;

                //xmlList.add(filePath);
            } catch (JAXBException e) {
                auftrag.setAuftragStatus(AuftragStatus.XMLFEHLER);
                throw new RuntimeException();
            }
        } catch (Exception e) {
            auftrag.setAuftragStatus(AuftragStatus.XMLFEHLER);
            throw new RuntimeException();
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

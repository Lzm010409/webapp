package de.lukegoll.application.data.service;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.xml.xmlEntities.caseData.participantData.Contact;
import de.lukegoll.application.xml.xmlEntities.constants.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KontaktRepository extends JpaRepository<Kontakt, Long>, JpaSpecificationExecutor<Kontakt> {


    @Query("select c from Kontakt c where lower(c.vName) like lower(concat('%',:searchTermVName,'%')) and lower(c.nName) like lower(concat('%',:searchTermNName,'%'))")
    List<Kontakt> searchForKunde(@Param("searchTermVName") String searchTerm, @Param(("searchTermNName")) String searchTerm2);

    @Query("SELECT u FROM Kontakt u WHERE (lower (u.vName) = :searchTermVName) ")
    List<Kontakt> searchForRechtsanwalt(@Param("searchTermVName") String searchTerm);

    @Query("SELECT u FROM Kontakt u WHERE (lower (u.vName) = :searchTermVName) ")
    List<Kontakt> searchForVersicherung(@Param("searchTermVName") String searchTerm);

}

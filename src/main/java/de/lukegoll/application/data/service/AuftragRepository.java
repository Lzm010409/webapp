package de.lukegoll.application.data.service;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.xml.xmlEntities.caseData.participantData.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuftragRepository extends JpaRepository<Auftrag, Long>, JpaSpecificationExecutor<Auftrag> {

    @Query("select c from Auftrag c " +
            "where lower(c.gutachtenNummer) like lower(concat('%', :searchTerm, '%')) ")
    List<Auftrag> search(@Param("searchTerm") String searchTerm);

}

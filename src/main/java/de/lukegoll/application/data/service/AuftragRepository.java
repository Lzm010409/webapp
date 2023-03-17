package de.lukegoll.application.data.service;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.Fahrzeug;
import de.lukegoll.application.data.entity.persons.Kunde;
import de.lukegoll.application.data.entity.persons.Rechtsanwalt;
import de.lukegoll.application.data.entity.persons.Versicherung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuftragRepository extends JpaRepository<Auftrag, Long>, JpaSpecificationExecutor<Auftrag> {


}

package de.lukegoll.application.data.service;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.persons.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KundeRepository extends JpaRepository<Kunde, Long>, JpaSpecificationExecutor<Kunde> {

}

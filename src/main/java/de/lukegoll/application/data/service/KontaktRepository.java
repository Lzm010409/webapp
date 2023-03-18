package de.lukegoll.application.data.service;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.persons.Kontakt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KontaktRepository extends JpaRepository<Kontakt, Long>, JpaSpecificationExecutor<Kontakt> {


}

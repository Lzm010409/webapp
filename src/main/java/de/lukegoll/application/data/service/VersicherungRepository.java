package de.lukegoll.application.data.service;

import de.lukegoll.application.data.entity.persons.Kunde;
import de.lukegoll.application.data.entity.persons.Versicherung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VersicherungRepository extends JpaRepository<Versicherung, Long>, JpaSpecificationExecutor<Versicherung> {

}

package de.lukegoll.application.data.service;

import de.lukegoll.application.data.entity.persons.Kunde;
import de.lukegoll.application.data.entity.persons.Rechtsanwalt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RechtsanwaltRepository extends JpaRepository<Rechtsanwalt, Long>, JpaSpecificationExecutor<Rechtsanwalt> {

}

package de.lukegoll.application.data.service;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.Fahrzeug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FahrzeugRepository extends JpaRepository<Fahrzeug, Long>, JpaSpecificationExecutor<Fahrzeug> {

}

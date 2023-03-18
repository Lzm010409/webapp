package de.lukegoll.application.data.service;

import de.lukegoll.application.data.entity.Auftrag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuftragRepository extends JpaRepository<Auftrag, Long>, JpaSpecificationExecutor<Auftrag> {


}

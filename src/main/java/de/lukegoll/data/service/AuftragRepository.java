package de.lukegoll.data.service;

import de.lukegoll.data.entity.Auftrag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuftragRepository extends JpaRepository<Auftrag, Long>, JpaSpecificationExecutor<Auftrag> {

}

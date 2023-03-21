package de.lukegoll.application.data.service;

import java.util.List;
import java.util.Optional;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.persons.Kontakt;
import de.lukegoll.application.xml.xmlEntities.caseData.participantData.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AuftragService {

    private final AuftragRepository auftragRepository;

    public AuftragService(AuftragRepository repository) {
        this.auftragRepository = repository;

    }

    public Optional<Auftrag> get(Long id) {
        return auftragRepository.findById(id);
    }

    public Auftrag update(Auftrag entity) {
        return auftragRepository.save(entity);
    }

    public void delete(Long id) {
        auftragRepository.deleteById(id);
    }

    public void saveAuftrag(Auftrag auftrag) {
        if (auftrag == null
        ) {
            System.err.println("Fehler");
            return;
        }
        auftragRepository.save(auftrag);
    }

    public List<Auftrag> findAllAufträge() {

        return auftragRepository.findAll();

    }

    public List<Auftrag> findAllAufträge(String filterValue) {
        if (filterValue == null) {
            return auftragRepository.findAll();
        } else {
            return auftragRepository.search(filterValue);
        }


    }


    public Page<Auftrag> list(Pageable pageable) {
        return auftragRepository.findAll(pageable);
    }

    public Page<Auftrag> list(Pageable pageable, Specification<Auftrag> filter) {
        return auftragRepository.findAll(filter, pageable);
    }

    public int count() {
        return (int) auftragRepository.count();
    }

}

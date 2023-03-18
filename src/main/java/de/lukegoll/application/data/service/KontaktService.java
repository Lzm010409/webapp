package de.lukegoll.application.data.service;

import de.lukegoll.application.data.entity.Fahrzeug;
import de.lukegoll.application.data.entity.persons.Kontakt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KontaktService {

    private final KontaktRepository kontaktRepository;

    public KontaktService(KontaktRepository repository) {
        this.kontaktRepository = repository;

    }

    public Optional<Kontakt> get(Long id) {
        return kontaktRepository.findById(id);
    }

    public Kontakt update(Kontakt entity) {
        return kontaktRepository.save(entity);
    }

    public void delete(Long id) {
        kontaktRepository.deleteById(id);
    }

    public void saveKontakt(Kontakt kontakt) {
        if (kontakt == null
            ) {
            System.err.println("Fehler");
            return;
        }
        kontaktRepository.save(kontakt);
    }

    public Page<Kontakt> list(Pageable pageable) {
        return kontaktRepository.findAll(pageable);
    }

    public Page<Kontakt> list(Pageable pageable, Specification<Kontakt> filter) {
        return kontaktRepository.findAll(filter, pageable);
    }

    public int count() {
        return (int) kontaktRepository.count();
    }

}

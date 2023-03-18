package de.lukegoll.application.data.service;

import de.lukegoll.application.data.entity.Auftrag;
import de.lukegoll.application.data.entity.Fahrzeug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FahrzeugService {

    private final FahrzeugRepository fahrzeugRepository;

    public FahrzeugService(FahrzeugRepository repository) {
        this.fahrzeugRepository = repository;

    }

    public Optional<Fahrzeug> get(Long id) {
        return fahrzeugRepository.findById(id);
    }

    public Fahrzeug update(Fahrzeug entity) {
        return fahrzeugRepository.save(entity);
    }

    public void delete(Long id) {
        fahrzeugRepository.deleteById(id);
    }

    public void saveFahrzeug(Fahrzeug fahrzeug) {
        if (fahrzeug == null
            ) {
            System.err.println("Fehler");
            return;
        }
        fahrzeugRepository.save(fahrzeug);
    }

    public Page<Fahrzeug> list(Pageable pageable) {
        return fahrzeugRepository.findAll(pageable);
    }

    public Page<Fahrzeug> list(Pageable pageable, Specification<Fahrzeug> filter) {
        return fahrzeugRepository.findAll(filter, pageable);
    }

    public int count() {
        return (int) fahrzeugRepository.count();
    }

}

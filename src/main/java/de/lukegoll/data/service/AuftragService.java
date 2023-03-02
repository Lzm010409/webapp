package de.lukegoll.data.service;

import de.lukegoll.data.entity.Auftrag;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AuftragService {

    private final AuftragRepository repository;

    public AuftragService(AuftragRepository repository) {
        this.repository = repository;
    }

    public Optional<Auftrag> get(Long id) {
        return repository.findById(id);
    }

    public Auftrag update(Auftrag entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Auftrag> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Auftrag> list(Pageable pageable, Specification<Auftrag> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}

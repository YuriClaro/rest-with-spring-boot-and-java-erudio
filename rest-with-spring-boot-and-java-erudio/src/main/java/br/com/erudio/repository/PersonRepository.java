package br.com.erudio.repository;

import br.com.erudio.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Page<Person> findAll(Pageable pageable);
    Optional<Person> findById(UUID id);
    void deleteById(UUID id);
}

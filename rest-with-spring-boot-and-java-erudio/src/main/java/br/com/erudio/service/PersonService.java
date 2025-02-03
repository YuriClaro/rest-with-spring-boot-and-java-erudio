package br.com.erudio.service;

import br.com.erudio.dto.PersonDTO;
import br.com.erudio.dto.v2.PersonDTOV2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PersonService {
    PersonDTO create(PersonDTO person);
    PersonDTOV2 createV2(PersonDTOV2 personV2);
    PersonDTO getById(UUID id);
    Page<PersonDTO> getAll(Pageable pageable);
    PersonDTO update(UUID id, PersonDTO personDTO);
    void delete(UUID id);
}

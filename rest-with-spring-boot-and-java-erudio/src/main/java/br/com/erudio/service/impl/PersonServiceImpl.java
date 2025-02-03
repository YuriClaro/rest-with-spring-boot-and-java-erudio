package br.com.erudio.service.impl;

import br.com.erudio.controller.PersonController;
import br.com.erudio.dto.PersonDTO;
import br.com.erudio.dto.v2.PersonDTOV2;
import br.com.erudio.mapper.PersonMapper;
import br.com.erudio.mapper.custom.PersonMapperCustom;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import br.com.erudio.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static br.com.erudio.util.PersonUtil.findById;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PersonMapperCustom personMapperCustom;
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper, PersonMapperCustom personMapperCustom) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.personMapperCustom = personMapperCustom;
    }

    @Override
    @Transactional
    public PersonDTO create(PersonDTO personDTO) {
        logger.info("Creating person.");
        Person person = personMapper.toEntity(personDTO);
        Person savedPerson = personRepository.save(person);
        logger.info("Person was created with ID: " + savedPerson.getId());
        return personMapper.toDTO(savedPerson);
    }

    @Override
    @Transactional
    public PersonDTOV2 createV2(PersonDTOV2 personDTOV2) {
        logger.info("Creating person.");
        Person person = personMapperCustom.toEntityV2(personDTOV2);
        Person savedPerson = personRepository.save(person);
        logger.info("Person was created with ID: " + savedPerson.getId());
        return personMapperCustom.toDTOV2(savedPerson);
    }

    @Override
    public PersonDTO getById(UUID id) {
        logger.info("Fetching person with ID: " + id);
        Person person = findById(id, personRepository);
        PersonDTO personDTO = personMapper.toDTO(person);
        personDTO.add(linkTo(methodOn(PersonController.class).getPersonById(id)).withSelfRel());
        logger.info("Person with ID: " + id + " returned successfully");
        return personDTO;
    }

    @Override
    public Page<PersonDTO> getAll(Pageable pageable) {
        logger.info("Fetching all people with pagination - Page: " + pageable.getPageNumber() + ", Size: " +
                pageable.getPageSize());
        Page<Person> personPage = personRepository.findAll(pageable);
        logger.info("Total person found: " + personPage.getTotalElements() + ", Total pages: " +
                personPage.getTotalPages());
        Page<PersonDTO> personDTOPage = personPage.map(personMapper::toDTO);
        personDTOPage.stream()
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).getPersonById(p.getId())).withSelfRel()));
        return personDTOPage;
    }

    @Override
    @Transactional
    public PersonDTO update(UUID id, PersonDTO personDTO) {
        logger.info("Attempting to update person ID: " + id);
        Person existingPerson = findById(id, personRepository);
        personMapper.updateEntityFromDTO(personDTO, existingPerson);
        Person updatedPerson = personRepository.save(existingPerson);
        logger.info("Person with ID: " + id + " has been updated.");
        return personMapper.toDTO(updatedPerson);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        logger.info("Attempting to delete person ID: " + id);
        findById(id, personRepository);
        personRepository.deleteById(id);
        logger.info("Salary ID: " + id + " deleted successfully");
    }
}

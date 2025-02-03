package br.com.erudio.controller;

import br.com.erudio.dto.v2.PersonDTOV2;
import br.com.erudio.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/person")
public class PersonControllerV2 {
    private final PersonService personService;

    public PersonControllerV2(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonDTOV2> createPersonV2(@Valid @RequestBody PersonDTOV2 personDTOV2) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createV2(personDTOV2));
    }

}

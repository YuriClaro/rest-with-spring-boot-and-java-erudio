package br.com.erudio.util;

import br.com.erudio.exception.custom.PersonNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PersonUtil {
    public static Person findById(UUID id, PersonRepository repository)  {
        return repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}

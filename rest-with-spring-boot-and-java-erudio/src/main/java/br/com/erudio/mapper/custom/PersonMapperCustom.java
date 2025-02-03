package br.com.erudio.mapper.custom;

import br.com.erudio.dto.v2.PersonDTOV2;
import br.com.erudio.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapperCustom {

    default PersonDTOV2 toDTOV2(Person person) {
        PersonDTOV2 personDTOV2 = new PersonDTOV2();

        personDTOV2.setId( person.getId() );
        personDTOV2.setFirstName( person.getFirstName() );
        personDTOV2.setLastName( person.getLastName() );
        personDTOV2.setAddress( person.getAddress() );
        personDTOV2.setGender( person.getGender() );
        personDTOV2.setBirthdate( person.getBirthdate());

        return personDTOV2;
    }

    default Person toEntityV2(PersonDTOV2 personDTOV2) {
        Person person = new Person();

        person.setId( personDTOV2.getId() );
        person.setFirstName( personDTOV2.getFirstName() );
        person.setLastName( personDTOV2.getLastName() );
        person.setAddress( personDTOV2.getAddress() );
        person.setGender( personDTOV2.getGender() );
        person.setBirthdate( personDTOV2.getBirthdate());

        return person;
    }
}

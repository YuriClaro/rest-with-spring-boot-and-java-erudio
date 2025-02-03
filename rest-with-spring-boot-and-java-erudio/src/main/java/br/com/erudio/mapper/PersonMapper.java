package br.com.erudio.mapper;

import br.com.erudio.dto.PersonDTO;
import br.com.erudio.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDTO toDTO(Person person);
    Person toEntity(PersonDTO personDTO);
    void updateEntityFromDTO(PersonDTO personDTO, @MappingTarget Person person);
}

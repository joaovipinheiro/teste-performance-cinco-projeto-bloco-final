package br.com.infnet.dto;

import br.com.infnet.model.Person;

public class PersonMapper {

    public static PersonDTO toDTO(Person person) {
        if (person == null) return null;
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getEmail(),
                person.getPhone()
        );
    }

    public static Person toEntity(PersonDTO dto) {
        if (dto == null) return null;
        return new Person(
                dto.id(),      // Mudou de getId() para id()
                dto.name(),    // Mudou de getName() para name()
                dto.email(),   // Mudou de getEmail() para email()
                dto.phone()    // Mudou de getPhone() para phone()
        );
    }
}
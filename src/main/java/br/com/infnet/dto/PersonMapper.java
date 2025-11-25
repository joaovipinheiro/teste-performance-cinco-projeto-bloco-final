package br.com.infnet.dto;

import br.com.infnet.model.Person;

/**
 * Mapper para conversão entre Person (entidade) e PersonDTO (DTO).
 * Aplicando separação de responsabilidades.
 */
public final class PersonMapper {

    private PersonMapper() {
        // Classe utilitária
    }

    public static PersonDTO toDTO(Person person) {
        if (person == null) {
            return null;
        }
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getEmail(),
                person.getPhone()
        );
    }

    public static Person toEntity(PersonDTO dto) {
        if (dto == null) {
            return null;
        }
        if (dto.getId() == null) {
            return Person.create(dto.getName(), dto.getEmail(), dto.getPhone());
        }
        return Person.withId(dto.getId(), dto.getName(), dto.getEmail(), dto.getPhone());
    }
}
package br.com.infnet.jqwik.repository;


import br.com.infnet.model.Person;
import br.com.infnet.exception.PersonNotFoundException;  // Exceção a ser criada
import br.com.infnet.repository.PersonRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest {

    @Test
    void shouldCreateAndReadPerson() {
        PersonRepository repo = new PersonRepository();
        Person person = new Person(null, "João Silva", "joao@email.com", "99999-0000");
        repo.save(person); // O método save já lida com a criação

        assertEquals(person, repo.findById(person.getId()).orElse(null));
    }

    @Test
    void shouldThrowWhenPersonNotFound() {
        PersonRepository repo = new PersonRepository();
        assertThrows(PersonNotFoundException.class, () -> {
            repo.findById(99L).orElseThrow(() -> new PersonNotFoundException("Pessoa não encontrada"));
        });
    }
}

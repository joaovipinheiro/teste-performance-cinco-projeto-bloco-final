package br.com.infnet.jqwik.repository;

import br.com.infnet.model.Person;
import br.com.infnet.repository.PersonRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest {

    @Test
    void shouldCreateAndReadPerson() {
        PersonRepository repo = new PersonRepository();
        Person person = Person.create("João Silva", "joao@email.com", "99999-0000");
        Person saved = repo.save(person); // O método save já lida com a criação

        Person found = repo.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(saved.getName(), found.getName());
        assertEquals(saved.getEmail(), found.getEmail());
        assertEquals(saved.getPhone(), found.getPhone());
    }

    @Test
    void shouldReturnEmptyWhenPersonNotFound() {
        PersonRepository repo = new PersonRepository();
        assertTrue(repo.findById(99L).isEmpty());
    }
}

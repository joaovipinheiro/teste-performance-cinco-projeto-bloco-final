package br.com.infnet.jqwik.service;

import br.com.infnet.model.Person;
import br.com.infnet.repository.PersonRepository;
import br.com.infnet.service.PersonService;
import net.jqwik.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonServicePropertyTest {

    @Property
    void shouldRegisterPersonsWithValidNameAndEmail(
            @ForAll("validNames") String name,
            @ForAll("validEmails") String email) {

        PersonRepository repo = new PersonRepository();
        PersonService service = new PersonService(repo);
        Person person = new Person(null, name, email, "99999-0000");

        service.registerPerson(person);

        assertEquals(person, service.getPerson(person.getId()));
    }

    @Provide
    Arbitrary<String> validNames() {
        return Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(3).ofMaxLength(10);
    }

    @Provide
    Arbitrary<String> validEmails() {
        return Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(3).ofMaxLength(5)
                .map(s -> s + "@mail.com");
    }
}

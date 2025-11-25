package br.com.infnet.jqwik.service;

import br.com.infnet.integration.IntegrationEventPublisher;
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
        IntegrationEventPublisher eventPublisher = new IntegrationEventPublisher();
        PersonService service = new PersonService(repo, eventPublisher);
        Person created = service.createPerson(name, email, "99999-0000");

        Person found = service.getPersonById(created.getId());
        assertEquals(created.getName(), found.getName());
        assertEquals(created.getEmail(), found.getEmail());
        assertEquals(created.getPhone(), found.getPhone());
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

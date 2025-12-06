package br.com.infnet.integration;

import br.com.infnet.dto.PersonDTO;
import br.com.infnet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SystemIntegrationTest {

    private SystemIntegration integration;
    private PersonRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PersonRepository();
        integration = new SystemIntegration(repository);
    }

    @Test
    void shouldIntegratePersonCreation() {
        PersonDTO dto = new PersonDTO(null, "João", "joao@mail.com", "99999");
        PersonDTO saved = integration.savePerson(dto);

        assertNotNull(saved.getId());
        assertEquals("João", saved.getName());
    }

    @Test
    void shouldRetrieveAllPersons() {
        integration.savePerson(new PersonDTO(null, "Ana", "ana@mail.com", "88888"));
        integration.savePerson(new PersonDTO(null, "Pedro", "pedro@mail.com", "77777"));

        assertEquals(3, integration.getAllPersons().size()); // +1 inicial
    }

    @Test
    void shouldSyncData() {
        DataSyncService sync = new DataSyncService(integration);

        sync.addToSync(new PersonDTO(null, "Maria", "maria@mail.com", "66666"));
        assertEquals(1, sync.getSyncCount());

        sync.flushSync();
        assertEquals(0, sync.getSyncCount());
        assertEquals(2, integration.getAllPersons().size()); // +1 inicial
    }
}
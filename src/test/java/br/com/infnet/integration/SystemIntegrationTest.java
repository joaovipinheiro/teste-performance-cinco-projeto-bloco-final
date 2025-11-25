package br.com.infnet.integration;

import br.com.infnet.dto.PersonDTO;
import br.com.infnet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de integração entre o sistema principal e o sistema de integração.
 */
class SystemIntegrationTest {

    private SystemIntegration integration;
    private PersonRepository repository;
    private IntegrationEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        repository = new PersonRepository();
        eventPublisher = new IntegrationEventPublisher();
        integration = new SystemIntegration(repository, eventPublisher);
    }

    @Test
    void shouldIntegratePersonCreation() {
        PersonDTO dto = new PersonDTO(null, "João", "joao@mail.com", "99999");
        PersonDTO saved = integration.savePerson(dto);

        assertNotNull(saved.getId());
        assertEquals("João", saved.getName());
        assertEquals("joao@mail.com", saved.getEmail());
    }

    @Test
    void shouldRetrieveAllPersons() {
        integration.savePerson(new PersonDTO(null, "Ana", "ana@mail.com", "88888"));
        integration.savePerson(new PersonDTO(null, "Pedro", "pedro@mail.com", "77777"));

        assertEquals(3, integration.getAllPersons().size()); // +1 inicial (demo data)
    }

    @Test
    void shouldFindPersonById() {
        PersonDTO created = integration.savePerson(new PersonDTO(null, "Maria", "maria@mail.com", "66666"));
        
        assertTrue(integration.findPersonById(created.getId()).isPresent());
        assertEquals("Maria", integration.findPersonById(created.getId()).get().getName());
    }

    @Test
    void shouldReturnEmptyWhenPersonNotFound() {
        assertTrue(integration.findPersonById(999L).isEmpty());
    }

    @Test
    void shouldUpdatePerson() {
        PersonDTO created = integration.savePerson(new PersonDTO(null, "Carlos", "carlos@mail.com", "55555"));
        Long id = created.getId();
        
        PersonDTO updated = new PersonDTO(id, "Carlos Silva", "carlos.silva@mail.com", "44444");
        PersonDTO result = integration.savePerson(updated);
        
        assertEquals(id, result.getId());
        assertEquals("Carlos Silva", result.getName());
        assertEquals("carlos.silva@mail.com", result.getEmail());
    }

    @Test
    void shouldDeletePerson() {
        PersonDTO created = integration.savePerson(new PersonDTO(null, "Teste", "teste@mail.com", "33333"));
        Long id = created.getId();
        
        boolean deleted = integration.deletePerson(id);
        assertTrue(deleted);
        assertTrue(integration.findPersonById(id).isEmpty());
    }

    @Test
    void shouldReturnFalseWhenDeletingNonExistentPerson() {
        boolean deleted = integration.deletePerson(999L);
        assertFalse(deleted);
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

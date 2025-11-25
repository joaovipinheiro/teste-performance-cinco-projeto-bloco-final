package br.com.infnet.integration;

import br.com.infnet.dto.PersonDTO;
import br.com.infnet.dto.PersonMapper;
import br.com.infnet.model.Person;
import br.com.infnet.repository.PersonRepository;
import br.com.infnet.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de integração entre PersonService e SystemIntegration.
 * Verifica que as alterações em um sistema refletem no outro.
 */
class PersonServiceIntegrationTest {

    private PersonRepository repository;
    private IntegrationEventPublisher eventPublisher;
    private PersonService personService;
    private SystemIntegration systemIntegration;

    @BeforeEach
    void setUp() {
        repository = new PersonRepository();
        eventPublisher = new IntegrationEventPublisher();
        personService = new PersonService(repository, eventPublisher);
        systemIntegration = new SystemIntegration(repository, eventPublisher);
    }

    @Test
    void shouldSyncWhenPersonCreatedViaService() {
        // Cria pessoa via PersonService
        Person created = personService.createPerson("João Silva", "joao@example.com", "99999-0000");
        
        // Verifica que está disponível via SystemIntegration
        assertTrue(systemIntegration.findPersonById(created.getId()).isPresent());
        PersonDTO dto = systemIntegration.findPersonById(created.getId()).get();
        assertEquals("João Silva", dto.getName());
        assertEquals("joao@example.com", dto.getEmail());
    }

    @Test
    void shouldSyncWhenPersonUpdatedViaService() {
        // Cria pessoa via PersonService
        Person created = personService.createPerson("Maria", "maria@example.com", "88888-0000");
        Long id = created.getId();
        
        // Atualiza via PersonService
        Person updated = personService.updatePerson(id, "Maria Silva", "maria.silva@example.com", "77777-0000");
        
        // Verifica que está atualizado via SystemIntegration
        PersonDTO dto = systemIntegration.findPersonById(id).get();
        assertEquals("Maria Silva", dto.getName());
        assertEquals("maria.silva@example.com", dto.getEmail());
    }

    @Test
    void shouldSyncWhenPersonDeletedViaService() {
        // Cria pessoa via PersonService
        Person created = personService.createPerson("Pedro", "pedro@example.com", "66666-0000");
        Long id = created.getId();
        
        // Deleta via PersonService
        personService.deletePerson(id);
        
        // Verifica que foi deletado via SystemIntegration
        assertTrue(systemIntegration.findPersonById(id).isEmpty());
    }

    @Test
    void shouldSyncWhenPersonCreatedViaIntegration() {
        // Cria pessoa via SystemIntegration
        PersonDTO dto = new PersonDTO(null, "Ana", "ana@example.com", "55555-0000");
        PersonDTO created = systemIntegration.savePerson(dto);
        
        // Verifica que está disponível via PersonService
        Person person = personService.getPersonById(created.getId());
        assertEquals("Ana", person.getName());
        assertEquals("ana@example.com", person.getEmail());
    }

    @Test
    void shouldMaintainDataConsistency() {
        // Cria via PersonService
        Person p1 = personService.createPerson("Sistema 1", "sistema1@example.com", "11111-0000");
        
        // Cria via SystemIntegration
        PersonDTO p2 = systemIntegration.savePerson(new PersonDTO(null, "Sistema 2", "sistema2@example.com", "22222-0000"));
        
        // Lista via PersonService
        var allPersons = personService.findAllPersons();
        assertEquals(3, allPersons.size()); // +1 demo data
        
        // Lista via SystemIntegration
        var allDTOs = systemIntegration.getAllPersons();
        assertEquals(3, allDTOs.size());
        
        // Verifica que ambos têm os mesmos dados
        assertTrue(allPersons.stream().anyMatch(p -> p.getId().equals(p1.getId())));
        assertTrue(allDTOs.stream().anyMatch(dto -> dto.getId().equals(p2.getId())));
    }
}


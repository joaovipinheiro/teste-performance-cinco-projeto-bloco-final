package br.com.infnet.service;

import br.com.infnet.dto.PersonDTO;
import br.com.infnet.dto.PersonMapper;
import br.com.infnet.exception.InvalidPersonException;
import br.com.infnet.exception.PersonNotFoundException;
import br.com.infnet.integration.IntegrationEventPublisher;
import br.com.infnet.model.Person;
import br.com.infnet.repository.PersonRepository;
import br.com.infnet.validation.PersonValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço de negócio para gerenciamento de pessoas.
 * Aplica separação entre consultas (queries) e modificadores (commands).
 * Publica eventos para integração com outros sistemas.
 */
@Service
public class PersonService {
    private final PersonRepository repository;
    private final IntegrationEventPublisher eventPublisher;

    public PersonService(PersonRepository repository, IntegrationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    // ========== CONSULTAS (Queries) - não modificam estado ==========
    
    public Optional<Person> findPersonById(Long id) {
        return repository.findById(id);
    }

    public Person getPersonById(Long id) {
        return findPersonById(id)
                .orElseThrow(() -> new PersonNotFoundException(String.valueOf(id)));
    }

    public List<Person> findAllPersons() {
        return repository.findAll();
    }

    public boolean personExists(Long id) {
        return repository.existsById(id);
    }

    // ========== MODIFICADORES (Commands) - alteram estado ==========

    public Person createPerson(String name, String email, String phone) {
        Person person = Person.create(name, email, phone);
        validatePerson(person);
        Person saved = repository.save(person);
        
        // Publica evento para integração
        PersonDTO dto = PersonMapper.toDTO(saved);
        eventPublisher.publishPersonCreated(dto);
        
        return saved;
    }

    public Person updatePerson(Long id, String name, String email, String phone) {
        Person existing = getPersonById(id);
        Person updated = existing.update(name, email, phone);
        validatePerson(updated);
        Person saved = repository.save(updated);
        
        // Publica evento para integração
        PersonDTO dto = PersonMapper.toDTO(saved);
        eventPublisher.publishPersonUpdated(dto);
        
        return saved;
    }

    public void deletePerson(Long id) {
        if (!repository.delete(id)) {
            throw new PersonNotFoundException(String.valueOf(id));
        }
        
        // Publica evento para integração
        eventPublisher.publishPersonDeleted(id);
    }

    // ========== MÉTODOS PRIVADOS ==========

    private void validatePerson(Person person) {
        try {
            PersonValidator.validate(person);
        } catch (IllegalArgumentException e) {
            throw new InvalidPersonException(e.getMessage());
        }
    }
}

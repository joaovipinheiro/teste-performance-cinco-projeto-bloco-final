package br.com.infnet.integration;

import br.com.infnet.dto.PersonDTO;
import br.com.infnet.dto.PersonMapper;
import br.com.infnet.model.Person;
import br.com.infnet.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Camada de integração para comunicação com sistemas externos.
 * Aplicando separação entre consultas e modificadores.
 * Integrado com o sistema principal para garantir sincronização de dados.
 */
@Component
public class SystemIntegration {

    private final PersonRepository repository;
    private final IntegrationEventPublisher eventPublisher;

    public SystemIntegration(PersonRepository repository, IntegrationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    // ========== CONSULTAS ==========
    
    public List<PersonDTO> getAllPersons() {
        return repository.findAll().stream()
                .map(PersonMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PersonDTO> findPersonById(Long id) {
        return repository.findById(id)
                .map(PersonMapper::toDTO);
    }

    // ========== MODIFICADORES ==========

    public PersonDTO savePerson(PersonDTO dto) {
        Person person = PersonMapper.toEntity(dto);
        Person saved = repository.save(person);
        PersonDTO savedDTO = PersonMapper.toDTO(saved);
        
        // Publica evento de integração
        if (dto.getId() == null) {
            eventPublisher.publishPersonCreated(savedDTO);
        } else {
            eventPublisher.publishPersonUpdated(savedDTO);
        }
        
        return savedDTO;
    }

    public boolean deletePerson(Long id) {
        boolean deleted = repository.delete(id);
        if (deleted) {
            eventPublisher.publishPersonDeleted(id);
        }
        return deleted;
    }
}
package br.com.infnet.integration;

import br.com.infnet.dto.PersonDTO;
import br.com.infnet.dto.PersonMapper;
import br.com.infnet.model.Person;
import br.com.infnet.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SystemIntegration {

    private final PersonRepository repository;

    public SystemIntegration(PersonRepository repository) {
        this.repository = repository;
    }

    public List<PersonDTO> getAllPersons() {
        return repository.findAll().stream()
                .map(PersonMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO getPersonById(Long id) {
        return repository.findById(id)
                .map(PersonMapper::toDTO)
                .orElse(null);
    }

    public PersonDTO savePerson(PersonDTO dto) {
        Person person = PersonMapper.toEntity(dto);
        Person saved = repository.save(person);
        return PersonMapper.toDTO(saved);
    }

    public boolean deletePerson(Long id) {
        return repository.delete(id);
    }
}
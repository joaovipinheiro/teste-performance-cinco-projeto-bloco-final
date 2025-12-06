package br.com.infnet.service;

import br.com.infnet.model.Person;
import br.com.infnet.repository.PersonRepository;
import br.com.infnet.exception.InvalidPersonException;  // Altere ou crie uma exceção específica, se necessário

import java.util.List;

public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public void registerPerson(Person person) {
        // Validação exemplo (nome deve ter pelo menos 3 caracteres)
        if (person.getName().length() < 3) {
            throw new InvalidPersonException("Nome deve ter pelo menos 3 caracteres.");
        }
        repository.save(person);
    }

    public Person getPerson(Long id) {
        return repository.findById(id).orElse(null); // Retorna null se não encontrar
    }

    public List<Person> listPersons() {
        return repository.findAll();
    }

    public void updatePerson(Person person) {
        repository.save(person);  // Método save já lida com criação e atualização
    }

    public void removePerson(Long id) {
        repository.delete(id);
    }
}

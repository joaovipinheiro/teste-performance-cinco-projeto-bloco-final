package br.com.infnet.repository;

import br.com.infnet.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositório simples em memória gerenciado pelo Spring.
 * Aplicando separação entre consultas e modificadores.
 */
@Repository
public class PersonRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    
    private final Map<Long, Person> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public PersonRepository() {
        initializeDemoData();
    }

    private void initializeDemoData() {
        Long id = idGenerator.getAndIncrement();
        Person demoPerson = Person.withId(id, "João Silva", "joao@example.com", "99999-0000");
        storage.put(demoPerson.getId(), demoPerson);
        logger.debug("Pessoa inicial adicionada: {}", demoPerson);
    }

    // Consultas (queries) - não modificam estado
    public List<Person> findAll() {
        List<Person> list = new ArrayList<>(storage.values());
        list.sort(Comparator.comparing(Person::getId));
        return Collections.unmodifiableList(list);
    }

    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }

    // Modificadores (commands) - alteram estado
    public Person save(Person person) {
        if (person.getId() == null) {
            Long newId = idGenerator.getAndIncrement();
            Person newPerson = Person.withId(newId, person.getName(), person.getEmail(), person.getPhone());
            storage.put(newPerson.getId(), newPerson);
            logger.debug("Nova pessoa criada com ID: {}", newPerson.getId());
            return newPerson;
        }
        storage.put(person.getId(), person);
        logger.debug("Pessoa atualizada: {}", person);
        return person;
    }

    public boolean delete(Long id) {
        Person removed = storage.remove(id);
        boolean success = removed != null;
        if (success) {
            logger.debug("Pessoa removida: ID={}", id);
        }
        return success;
    }
}

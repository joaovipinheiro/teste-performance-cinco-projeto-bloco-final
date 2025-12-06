package br.com.infnet.repository;

import br.com.infnet.model.Person;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositório simples em memória gerenciado pelo Spring.
 * Anotado com @Repository para ser detectado pelo Spring Boot.
 */
@Repository
public class PersonRepository {

    private final Map<Long, Person> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Construtor para inicialização do repositório
    public PersonRepository() {
        init();  // Chama a inicialização manualmente
    }

    // Método de inicialização manual
    private void init() {
        // seed demo - dados iniciais
        Person p = new Person(idGenerator.getAndIncrement(), "João Silva", "joao@example.com", "99999-0000");
        storage.put(p.getId(), p);
        System.out.println("Pessoa inicial adicionada: " + p);  // Log para depuração
    }

    public List<Person> findAll() {
        ArrayList<Person> list = new ArrayList<>(storage.values());
        list.sort(Comparator.comparing(Person::getId));
        return list;
    }

    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Person save(Person person) {
        if (person.getId() == null) {
            person.setId(idGenerator.getAndIncrement());
            System.out.println("Novo ID gerado: " + person.getId());
        }
        storage.put(person.getId(), person);
        System.out.println("Pessoa salva/atualizada: " + person);
        return person;
    }

    public boolean delete(Long id) {
        boolean removed = storage.remove(id) != null;
        System.out.println("Pessoa removida: " + id + " - sucesso: " + removed);
        return removed;
    }
}

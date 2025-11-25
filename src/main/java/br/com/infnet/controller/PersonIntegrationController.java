package br.com.infnet.controller;

import br.com.infnet.dto.PersonDTO;
import br.com.infnet.exception.PersonNotFoundException;
import br.com.infnet.integration.SystemIntegration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para integração entre sistemas.
 * Permite comunicação entre o sistema principal e sistemas externos via API REST.
 */
@RestController
@RequestMapping("/api/persons")
public class PersonIntegrationController {

    private final SystemIntegration systemIntegration;

    public PersonIntegrationController(SystemIntegration systemIntegration) {
        this.systemIntegration = systemIntegration;
    }

    /**
     * Lista todas as pessoas (integração).
     */
    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> persons = systemIntegration.getAllPersons();
        return ResponseEntity.ok(persons);
    }

    /**
     * Busca uma pessoa por ID (integração).
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        Optional<PersonDTO> person = systemIntegration.findPersonById(id);
        return person
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria uma nova pessoa (integração).
     */
    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        try {
            PersonDTO created = systemIntegration.savePerson(personDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Atualiza uma pessoa existente (integração).
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        personDTO.setId(id);
        try {
            PersonDTO updated = systemIntegration.savePerson(personDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deleta uma pessoa (integração).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        boolean deleted = systemIntegration.deletePerson(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}


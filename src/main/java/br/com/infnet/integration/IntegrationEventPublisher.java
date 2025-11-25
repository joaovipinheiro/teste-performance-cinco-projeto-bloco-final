package br.com.infnet.integration;

import br.com.infnet.dto.PersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Publicador de eventos de integração.
 * Notifica outros sistemas sobre mudanças nos dados.
 */
@Component
public class IntegrationEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationEventPublisher.class);

    /**
     * Publica evento de criação de pessoa.
     */
    public void publishPersonCreated(PersonDTO person) {
        logger.info("Evento: Pessoa criada - ID: {}, Nome: {}", person.getId(), person.getName());
        // Aqui poderia enviar para uma fila, webhook, etc.
    }

    /**
     * Publica evento de atualização de pessoa.
     */
    public void publishPersonUpdated(PersonDTO person) {
        logger.info("Evento: Pessoa atualizada - ID: {}, Nome: {}", person.getId(), person.getName());
        // Aqui poderia enviar para uma fila, webhook, etc.
    }

    /**
     * Publica evento de exclusão de pessoa.
     */
    public void publishPersonDeleted(Long id) {
        logger.info("Evento: Pessoa deletada - ID: {}", id);
        // Aqui poderia enviar para uma fila, webhook, etc.
    }
}


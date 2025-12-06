package br.com.infnet.dto;

/**
 * Refatoração TP5: Uso de RECORD para garantir Imutabilidade.
 * Records não possuem setters, atendendo ao requisito de eliminar configuradores.
 */
public record PersonDTO(
        Long id,
        String name,
        String email,
        String phone
) {}
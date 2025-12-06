package br.com.infnet.validation;

import br.com.infnet.model.Person;

/**
 * Classe utilitária para validação de dados de Person.
 */
public final class PersonValidator {
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MIN_EMAIL_LENGTH = 5;

    private PersonValidator() {
        // Construtor privado para classe utilitária
    }

    /**
     * Valida todos os campos obrigatórios de uma pessoa.
     *
     * @param person pessoa a ser validada
     * @throws IllegalArgumentException se algum campo for inválido
     */
    public static void validate(Person person) {
        validateName(person.getName());
        validateEmail(person.getEmail());
    }

    /**
     * Valida o nome de uma pessoa.
     *
     * @param name nome a ser validado
     * @throws IllegalArgumentException se o nome for inválido
     */
    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (name.trim().length() < MIN_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "Nome deve ter ao menos " + MIN_NAME_LENGTH + " caracteres"
            );
        }
    }

    /**
     * Valida o email de uma pessoa.
     *
     * @param email email a ser validado
     * @throws IllegalArgumentException se o email for inválido
     */
    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (email.length() < MIN_EMAIL_LENGTH || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    /**
     * Verifica se um telefone é válido.
     *
     * @param phone telefone a ser verificado
     * @return true se válido, false caso contrário
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && !phone.trim().isEmpty();
    }
}
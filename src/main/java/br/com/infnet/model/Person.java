package br.com.infnet.model;

import java.util.Objects;

/**
 * Classe imutável representando uma Pessoa.
 * Aplicando o Princípio da Imutabilidade para garantir integridade dos dados.
 */
public final class Person {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;

    private Person(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Factory method para criar uma nova Person (criação).
     * O ID será gerado pelo repositório.
     */
    public static Person create(String name, String email, String phone) {
        return new Person(null, normalize(name), normalize(email), normalize(phone));
    }

    /**
     * Factory method para criar uma Person com ID (recuperação do repositório).
     */
    public static Person withId(Long id, String name, String email, String phone) {
        return new Person(id, normalize(name), normalize(email), normalize(phone));
    }

    /**
     * Método de cópia para atualização, criando nova instância imutável.
     * Aplica o princípio de separação entre consultas e modificadores.
     */
    public Person withName(String name) {
        return new Person(this.id, normalize(name), this.email, this.phone);
    }

    public Person withEmail(String email) {
        return new Person(this.id, this.name, normalize(email), this.phone);
    }

    public Person withPhone(String phone) {
        return new Person(this.id, this.name, this.email, normalize(phone));
    }

    /**
     * Método para atualizar múltiplos campos de uma vez.
     */
    public Person update(String name, String email, String phone) {
        return new Person(this.id, normalize(name), normalize(email), normalize(phone));
    }

    // Getters (apenas consultas, sem modificadores)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * Normaliza strings removendo espaços em branco.
     * Método privado para garantir consistência.
     */
    private static String normalize(String value) {
        return value != null ? value.trim() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) &&
                Objects.equals(email, person.email) &&
                Objects.equals(phone, person.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

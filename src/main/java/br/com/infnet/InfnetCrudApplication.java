package br.com.infnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application.
 * Execute esta classe para subir a aplicação (porta padrão 8080).
 */
@SpringBootApplication
public class InfnetCrudApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfnetCrudApplication.class, args);
    }
}

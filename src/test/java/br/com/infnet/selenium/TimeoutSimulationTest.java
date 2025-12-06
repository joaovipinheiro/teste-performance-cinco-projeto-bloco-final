package br.com.infnet.selenium;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

// [CORREÇÃO] Agora estende BaseTest para usar o Chrome configurado (headless)
public class TimeoutSimulationTest extends BaseTest {

    // [REMOVIDO] Não criamos mais o driver aqui, usamos o 'driver' do BaseTest
    // private WebDriver driver = new ChromeDriver(); 
    // private String baseUrl = "http://localhost:8080"; // Já existe no BaseTest

    @Test
    public void simulateTimeout() {
        long t1 = System.currentTimeMillis();

        // Usa o 'driver' e 'baseUrl' que vêm do BaseTest
        driver.get(baseUrl + "/simulate/timeout");

        long t2 = System.currentTimeMillis();

        assertTrue(t2 - t1 >= 5000);
    }
}

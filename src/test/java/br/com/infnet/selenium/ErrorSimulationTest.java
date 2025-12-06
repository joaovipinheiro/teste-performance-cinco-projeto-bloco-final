package br.com.infnet.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ErrorSimulationTest extends BaseTest {

    @Test
    public void testErrorSimulation() {
        driver.get(baseUrl + "/simulate/error");

        String body = driver.findElement(By.tagName("body")).getText();

        assertTrue(body.contains("Erro inesperado"));
    }
}

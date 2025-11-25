package br.com.infnet.selenium;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TimeoutSimulationTest {

    private WebDriver driver = new ChromeDriver();
    private String baseUrl = "http://localhost:8080";

    @Test
    public void simulateTimeout() {
        long t1 = System.currentTimeMillis();

        driver.get(baseUrl + "/simulate/timeout");

        long t2 = System.currentTimeMillis();

        assertTrue(t2 - t1 >= 5000);
    }
}

// ==================== BaseTest.java ====================
package br.com.infnet.selenium;

import br.com.infnet.InfnetCrudApplication; // [NOVO] Import da sua classe Main
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest; // [NOVO] Import do Spring Test

import java.time.Duration;

/**
 * Classe base configurada para CI/CD (GitHub Actions).
 * A anotação @SpringBootTest garante que o servidor suba na porta 8080.
 */
// [IMPORTANTE] Esta linha faz o servidor rodar durante os testes no GitHub
@SpringBootTest(classes = InfnetCrudApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final String baseUrl = "http://localhost:8080";

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        // --- CONFIGURAÇÃO OBRIGATÓRIA PARA GITHUB ACTIONS ---
        options.addArguments("--headless");              // Sem interface gráfica
        options.addArguments("--no-sandbox");            // Segurança do Linux
        options.addArguments("--disable-dev-shm-usage"); // Memória compartilhada
        options.addArguments("--remote-allow-origins=*"); // Permite conexão remota
        // ---------------------------------------------------

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

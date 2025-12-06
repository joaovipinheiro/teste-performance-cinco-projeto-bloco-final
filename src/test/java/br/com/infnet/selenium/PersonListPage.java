package br.com.infnet.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object para a lista de pessoas (melhorado).
 */
public class PersonListPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By tableRowsLocator = By.cssSelector("table tbody tr");
    private final By newButtonLocator = By.cssSelector("a.btn.btn-primary");
    private final By editButtonLocator = By.cssSelector("a.btn-success");
    private final By deleteButtonLocator = By.cssSelector("button.btn-danger");
    private final By alertLocator = By.cssSelector(".alert");

    public PersonListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/persons");
        waitForPageLoad();
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
    }

    public List<WebElement> getRows() {
        return driver.findElements(tableRowsLocator);
    }

    public WebElement findRowByEmail(String email) {
        List<WebElement> rows = getRows();
        return rows.stream()
                .filter(r -> r.getText().contains(email))
                .findFirst()
                .orElse(null);
    }

    public WebElement findRowByName(String name) {
        List<WebElement> rows = getRows();
        return rows.stream()
                .filter(r -> r.getText().contains(name))
                .findFirst()
                .orElse(null);
    }

    public boolean rowExists(String text) {
        return getRows().stream().anyMatch(r -> r.getText().contains(text));
    }

    public void clickNew() {
        wait.until(ExpectedConditions.elementToBeClickable(newButtonLocator)).click();
    }

    public void clickEditOnRow(WebElement row) {
        row.findElement(editButtonLocator).click();
    }

    public void clickDeleteOnRow(WebElement row) {
        row.findElement(deleteButtonLocator).click();
    }

    public String getAlertMessage() {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(alertLocator)).getText();
        } catch (Exception e) {
            return null;
        }
    }
}

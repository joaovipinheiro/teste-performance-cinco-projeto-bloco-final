package br.com.infnet.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object para o formulário de criação/edição (melhorado).
 */
public class PersonFormPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By nameInputLocator = By.name("name");
    private final By emailInputLocator = By.name("email");
    private final By phoneInputLocator = By.name("phone");
    private final By submitButtonLocator = By.cssSelector("button[type='submit']");
    private final By backButtonLocator = By.cssSelector("a.btn-secondary");
    private final By errorAlertLocator = By.cssSelector(".alert-danger");

    public PersonFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForFormLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(nameInputLocator));
    }

    public void setName(String value) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(nameInputLocator));
        el.clear();
        el.sendKeys(value);
    }

    public void setEmail(String value) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(emailInputLocator));
        el.clear();
        el.sendKeys(value);
    }

    public void setPhone(String value) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(phoneInputLocator));
        el.clear();
        el.sendKeys(value);
    }

    public void submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator)).click();
    }

    public void clickBack() {
        wait.until(ExpectedConditions.elementToBeClickable(backButtonLocator)).click();
    }

    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(errorAlertLocator)).getText();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean hasError() {
        try {
            driver.findElement(errorAlertLocator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

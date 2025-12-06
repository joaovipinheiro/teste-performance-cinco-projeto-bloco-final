package br.com.infnet.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Teste de fluxo CRUD completo em um único teste (alternativa ao PersonCrudTest).
 * Use este se preferir ter todo o fluxo em um único método.
 */
public class PersonCrudFlowTest extends BaseTest {

    private PersonListPage listPage;
    private PersonFormPage formPage;

    @BeforeEach
    @Override
    public void setup() {
        super.setup();
        listPage = new PersonListPage(driver);
        formPage = new PersonFormPage(driver);
    }

    @Test
    @DisplayName("Fluxo completo CRUD: Create -> Read -> Update -> Delete")
    public void testCompleteCrudFlow() {
        String testEmail = "flow" + System.currentTimeMillis() + "@test.com";
        String testName = "Fluxo Teste";
        String updatedName = "Fluxo Teste Atualizado";

        // === CREATE ===
        listPage.open(baseUrl);
        listPage.clickNew();

        formPage.waitForFormLoad();
        formPage.setName(testName);
        formPage.setEmail(testEmail);
        formPage.setPhone("11111-2222");
        formPage.submit();

        // === READ (verificar criação) ===
        listPage.waitForPageLoad();
        Assertions.assertTrue(
                listPage.rowExists(testEmail),
                "Registro criado não encontrado"
        );

        // === UPDATE ===
        WebElement rowToEdit = listPage.findRowByEmail(testEmail);
        Assertions.assertNotNull(rowToEdit, "Linha para editar não encontrada");

        listPage.clickEditOnRow(rowToEdit);
        formPage.waitForFormLoad();
        formPage.setName(updatedName);
        formPage.setPhone("33333-4444");
        formPage.submit();

        // === READ (verificar edição) ===
        listPage.waitForPageLoad();
        Assertions.assertTrue(
                listPage.rowExists(updatedName),
                "Registro editado não encontrado"
        );

        // === DELETE ===
        WebElement rowToDelete = listPage.findRowByEmail(testEmail);
        Assertions.assertNotNull(rowToDelete, "Linha para excluir não encontrada");

        listPage.clickDeleteOnRow(rowToDelete);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        // === READ (verificar exclusão) ===
        listPage.waitForPageLoad();
        Assertions.assertFalse(
                listPage.rowExists(testEmail),
                "Registro não foi excluído"
        );
    }
}
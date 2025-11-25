package br.com.infnet.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Teste de fluxo CRUD completo para Pessoa (melhorado).
 * Usa @TestInstance(Lifecycle.PER_CLASS) para manter estado entre testes.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonCrudTest extends BaseTest {

    private PersonListPage listPage;
    private PersonFormPage formPage;

    // Email compartilhado entre todos os testes da classe
    private static final String TEST_EMAIL = "selenium" + System.currentTimeMillis() + "@test.com";
    private static final String TEST_NAME = "Teste Selenium";
    private static final String TEST_PHONE = "11111-2222";
    private static final String UPDATED_NAME = "Teste Selenium Editado";

    @BeforeEach
    @Override
    public void setup() {
        super.setup();
        listPage = new PersonListPage(driver);
        formPage = new PersonFormPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("1. Deve criar uma nova pessoa com sucesso")
    public void testCreatePerson() {
        // Arrange & Act
        listPage.open(baseUrl);
        listPage.clickNew();

        formPage.waitForFormLoad();
        formPage.setName(TEST_NAME);
        formPage.setEmail(TEST_EMAIL);
        formPage.setPhone(TEST_PHONE);
        formPage.submit();

        // Assert
        listPage.waitForPageLoad();
        Assertions.assertTrue(
                listPage.rowExists(TEST_EMAIL),
                "Pessoa criada deve aparecer na listagem com email: " + TEST_EMAIL
        );
    }

    @Test
    @Order(2)
    @DisplayName("2. Deve editar uma pessoa existente")
    public void testEditPerson() {
        // Arrange
        String updatedPhone = "33333-4444";

        listPage.open(baseUrl);
        WebElement row = listPage.findRowByEmail(TEST_EMAIL);
        Assertions.assertNotNull(row, "Deve encontrar a pessoa para editar com email: " + TEST_EMAIL);

        // Act
        listPage.clickEditOnRow(row);
        formPage.waitForFormLoad();
        formPage.setName(UPDATED_NAME);
        formPage.setPhone(updatedPhone);
        formPage.submit();

        // Assert
        listPage.waitForPageLoad();
        Assertions.assertTrue(
                listPage.rowExists(UPDATED_NAME),
                "Nome atualizado deve aparecer na listagem: " + UPDATED_NAME
        );
    }

    @Test
    @Order(3)
    @DisplayName("3. Deve excluir uma pessoa")
    public void testDeletePerson() {
        // Arrange
        listPage.open(baseUrl);
        WebElement row = listPage.findRowByEmail(TEST_EMAIL);
        Assertions.assertNotNull(row, "Deve encontrar a pessoa para excluir com email: " + TEST_EMAIL);

        // Act
        listPage.clickDeleteOnRow(row);

        // Aceita o alert de confirmação
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        // Assert
        listPage.waitForPageLoad();
        Assertions.assertFalse(
                listPage.rowExists(TEST_EMAIL),
                "Pessoa excluída não deve aparecer na listagem com email: " + TEST_EMAIL
        );
    }

    @Test
    @Order(4)
    @DisplayName("4. Deve validar campos obrigatórios")
    public void testValidationErrors() {
        // Arrange & Act
        listPage.open(baseUrl);
        listPage.clickNew();

        formPage.waitForFormLoad();
        formPage.setName("AB"); // Nome muito curto
        formPage.setEmail("email-invalido"); // Email sem @
        formPage.submit();

        // Assert
        Assertions.assertTrue(
                formPage.hasError(),
                "Deve mostrar erro de validação"
        );
    }

    @Test
    @Order(5)
    @DisplayName("5. Deve cancelar criação de pessoa")
    public void testCancelCreation() {
        // Arrange & Act
        listPage.open(baseUrl);
        int initialRowCount = listPage.getRows().size();

        listPage.clickNew();
        formPage.waitForFormLoad();
        formPage.setName("Teste Cancelamento");
        formPage.setEmail("cancel" + System.currentTimeMillis() + "@test.com");
        formPage.clickBack();

        // Assert
        listPage.waitForPageLoad();
        int finalRowCount = listPage.getRows().size();
        Assertions.assertEquals(
                initialRowCount,
                finalRowCount,
                "Número de registros não deve mudar ao cancelar"
        );
    }
}
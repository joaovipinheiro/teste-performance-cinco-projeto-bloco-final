package br.com.infnet.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Este teste cobre a Rubrica 2: "configurou testes parametrizados no Selenium"
public class PersonParameterizedTest extends BaseTest {

    private PersonListPage listPage;
    private PersonFormPage formPage;

    @BeforeEach
    @Override
    public void setup() {
        super.setup(); // Inicializa o driver do BaseTest
        listPage = new PersonListPage(driver);
        formPage = new PersonFormPage(driver);
    }

    @DisplayName("Deve cadastrar múltiplas pessoas usando dados parametrizados")
    @ParameterizedTest
    @CsvSource({
            "Ana Parametrizada, ana.p@teste.com, 2199999-1111",
            "Beto Parametrizado, beto.p@teste.com, 1198888-2222",
            "Carla Parametrizada, carla.p@teste.com, 3197777-3333"
    })
    public void testRegistrationWithMultipleData(String name, String email, String phone) {
        listPage.open(baseUrl);
        listPage.clickNew();

        formPage.waitForFormLoad();
        formPage.setName(name);
        formPage.setEmail(email);
        formPage.setPhone(phone);
        formPage.submit();

        listPage.waitForPageLoad();

        Assertions.assertTrue(
                listPage.rowExists(email),
                "A pessoa " + name + " deveria aparecer na lista."
        );
    }
}
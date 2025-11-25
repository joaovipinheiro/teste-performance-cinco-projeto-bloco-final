package br.com.infnet.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvalidInputTest extends BaseTest {

    private PersonListPage list;
    private PersonFormPage form;

    @BeforeEach
    public void initPages() {
        list = new PersonListPage(driver);
        form = new PersonFormPage(driver);
    }

    @Test
    public void testInvalidInput() {
        list.open(baseUrl);
        list.clickNew();

        form.setName("A");
        form.setEmail("teste@teste.com");
        form.submit();

        assertTrue(form.hasError());
    }
}

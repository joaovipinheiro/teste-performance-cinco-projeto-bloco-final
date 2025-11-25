package br.com.infnet.jqwik.controller;

import br.com.infnet.controller.PersonController;
import br.com.infnet.service.PersonService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonControllerTest {

    @Test
    void testFailFastThrowsException() {
        PersonService personService = mock(PersonService.class);
        PersonController controller = new PersonController(personService);

        assertThrows(IllegalArgumentException.class, () -> {
            controller.simulateFailFast("<script>alert(1)</script>");
        });
    }

    @Test
    void testSimulateTimeout() throws Exception {
        PersonService personService = mock(PersonService.class);
        PersonController controller = new PersonController(personService);

        long start = System.currentTimeMillis();
        controller.simulateTimeout();
        long end = System.currentTimeMillis();

        assertTrue(end - start >= 5000);
    }
}

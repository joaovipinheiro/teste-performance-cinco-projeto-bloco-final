package br.com.infnet.jqwik.controller;

import br.com.infnet.controller.PersonController;
import br.com.infnet.repository.PersonRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonControllerTest {

    @Test
    void testFailFastThrowsException() {
        PersonRepository repo = mock(PersonRepository.class);
        PersonController controller = new PersonController(repo);

        assertThrows(IllegalArgumentException.class, () -> {
            controller.simulateFailFast("<script>alert(1)</script>");
        });
    }

    @Test
    void testSimulateTimeout() throws Exception {
        PersonRepository repo = mock(PersonRepository.class);
        PersonController controller = new PersonController(repo);

        long start = System.currentTimeMillis();
        controller.simulateTimeout();
        long end = System.currentTimeMillis();

        assertTrue(end - start >= 5000);
    }
}

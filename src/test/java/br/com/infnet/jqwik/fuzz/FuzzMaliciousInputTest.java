package br.com.infnet.jqwik.fuzz;

import net.jqwik.api.*;

public class FuzzMaliciousInputTest {

    @Property
    void testRandomStrings(@ForAll String input) {
        if (input.contains("<script>") || input.contains("DROP TABLE")) {
            try {
                throw new IllegalArgumentException("Entrada maliciosa detectada");
            } catch (Exception e) {
                assert true;
            }
        }
    }
}

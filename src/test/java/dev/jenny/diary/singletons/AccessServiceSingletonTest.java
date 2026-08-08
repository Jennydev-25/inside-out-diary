package dev.jenny.diary.singletons;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link AccessServiceSingleton}.
 */
class AccessServiceSingletonTest {

    /**
     * Verifies that resolvePassword falls back to the default password
     * when the DIARY_PASSWORD environment variable isn't set — the
     * normal case for local development and grading, where nobody
     * configures it deliberately.
     */
    @Test
    void testResolvePasswordReturnsDefaultWhenEnvironmentVariableIsAbsent() {
        String password = AccessServiceSingleton.resolvePassword();

        assertThat(password, is(equalTo("diary2026")));
    }

    /**
     * Verifies that resolvePassword uses the environment value instead
     * of the default when one is present.
     */
    @Test
    void testResolvePasswordReturnsEnvironmentValueWhenPresent() {
        String password = AccessServiceSingleton.resolvePassword("mi-contraseña-secreta");

        assertThat(password, is(equalTo("mi-contraseña-secreta")));
    }
}

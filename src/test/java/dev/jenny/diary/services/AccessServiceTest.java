package dev.jenny.diary.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link AccessService}.
 */
class AccessServiceTest {

    private static final String CORRECT_PASSWORD = "diary2026";

    private AccessService accessService;

    @BeforeEach
    void setUp() {
        accessService = new AccessService(CORRECT_PASSWORD);
    }

    /**
     * Verifies that attemptAccess grants access when the password
     * matches the correct one.
     */
    @Test
    void testAttemptAccessGrantsAccessWithCorrectPassword() {
        boolean granted = accessService.attemptAccess(CORRECT_PASSWORD);

        assertThat(granted, is(true));
    }

    /**
     * Verifies that attemptAccess denies access when the password does
     * not match the correct one.
     */
    @Test
    void testAttemptAccessDeniesAccessWithIncorrectPassword() {
        boolean granted = accessService.attemptAccess("contraseña-incorrecta");

        assertThat(granted, is(false));
    }

    /**
     * Verifies that hasAttemptsRemaining becomes false after exhausting
     * every allowed attempt with an incorrect password.
     */
    @Test
    void testHasAttemptsRemainingIsFalseAfterMaxFailedAttempts() {
        accessService.attemptAccess("intento-fallido-1");
        accessService.attemptAccess("intento-fallido-2");
        accessService.attemptAccess("intento-fallido-3");

        assertThat(accessService.hasAttemptsRemaining(), is(false));
    }

    /**
     * Verifies that attemptAccess denies access, even with the correct
     * password, once every attempt has been exhausted.
     */
    @Test
    void testAttemptAccessDeniesAccessOnceAttemptsAreExhausted() {
        accessService.attemptAccess("intento-fallido-1");
        accessService.attemptAccess("intento-fallido-2");
        accessService.attemptAccess("intento-fallido-3");

        boolean granted = accessService.attemptAccess(CORRECT_PASSWORD);

        assertThat(granted, is(false));
    }

    /**
     * Verifies that getMaxAttempts returns the configured maximum number
     * of attempts allowed before access is permanently denied.
     */
    @Test
    void testGetMaxAttemptsReturnsConfiguredMaximum() {
        int maxAttempts = accessService.getMaxAttempts();

        assertThat(maxAttempts, is(equalTo(3)));
    }
}

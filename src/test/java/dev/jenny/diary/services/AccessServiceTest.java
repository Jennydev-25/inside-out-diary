package dev.jenny.diary.services;

import static org.hamcrest.MatcherAssert.assertThat;
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
}

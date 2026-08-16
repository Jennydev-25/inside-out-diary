package dev.jenny.diary.singletons;

import dev.jenny.diary.services.AccessService;

/**
 * Eager singleton providing the single {@link AccessService} instance
 * used by the console app. Initialized eagerly (unlike the lazy
 * {@link DiaryControllerSingleton}) because it's needed from the very
 * first screen of every run: the password gate.
 */
public class AccessServiceSingleton {

    private static final String DEFAULT_PASSWORD = "diary2026";

    private static final AccessService INSTANCE = new AccessService(resolvePassword());

    private AccessServiceSingleton() {
    }

    /**
     * Returns the single {@link AccessService} instance.
     *
     * @return the shared AccessService instance
     */
    public static AccessService getInstance() {
        return INSTANCE;
    }

    /**
     * Resolves the password to use, reading it from the DIARY_PASSWORD
     * environment variable.
     *
     * @return the password to build the AccessService with
     */
    static String resolvePassword() {
        return resolvePassword(System.getenv("DIARY_PASSWORD"));
    }

    /**
     * Resolves the password to use: the given environment value when
     * present, or a documented default otherwise. Package-private and
     * parameterized so both branches can be tested directly, independently
     * of when the eager INSTANCE field gets evaluated.
     *
     * @param environmentPassword the value read from DIARY_PASSWORD, or null if
     *                            unset
     * @return the password to build the AccessService with
     */
    static String resolvePassword(String environmentPassword) {
        return environmentPassword != null ? environmentPassword : DEFAULT_PASSWORD;
    }

}

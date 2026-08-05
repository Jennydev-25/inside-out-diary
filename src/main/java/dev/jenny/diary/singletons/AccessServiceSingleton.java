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
     * Resolves the password to use: the DIARY_PASSWORD environment
     * variable when it's set, or a documented default otherwise.
     * Package-private so it can be tested directly, independently of
     * when the eager INSTANCE field gets evaluated.
     *
     * @return the password to build the AccessService with
     */
    static String resolvePassword() {
        String password = System.getenv("DIARY_PASSWORD");
        return password != null ? password : DEFAULT_PASSWORD;
    }

}

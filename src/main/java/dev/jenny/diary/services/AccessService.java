package dev.jenny.diary.services;

/**
 * Validates access to the diary through a password, limiting the
 * number of allowed attempts.
 */
public class AccessService {

    private static final int MAX_ATTEMPTS = 3;

    private final String correctPassword;
    private int remainingAttempts;

    public AccessService(String correctPassword) {
        this.correctPassword = correctPassword;
        this.remainingAttempts = MAX_ATTEMPTS;
    }

    /**
     * Checks a password attempt against the correct one, denying
     * access outright once every attempt has been exhausted. Every
     * incorrect attempt reduces the remaining attempts by one.
     *
     * @param passwordAttempt the password entered by the user
     * @return true if the password matches and attempts remain, false otherwise
     */
    public boolean attemptAccess(String passwordAttempt) {
        if (remainingAttempts <= 0) {
            return false;
        }
        if (correctPassword.equals(passwordAttempt)) {
            return true;
        }
        remainingAttempts--;
        return false;
    }

    /**
     * Checks whether there are still attempts left before access is
     * permanently denied.
     *
     * @return true if at least one attempt remains, false otherwise
     */
    public boolean hasAttemptsRemaining() {
        return remainingAttempts > 0;
    }

    /**
     * Returns the current number of attempts left before access is
     * permanently denied.
     *
     * @return the number of attempts remaining
     */
    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    /**
     * Returns the maximum number of attempts allowed before access is
     * permanently denied, so the View can display it without
     * hardcoding the number in more than one place.
     *
     * @return the configured maximum number of attempts
     */
    public int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }
}

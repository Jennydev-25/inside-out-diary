package dev.jenny.diary.services;

/**
 * Validates access to the diary through a password.
 */
public class AccessService {

    private final String correctPassword;

    public AccessService(String correctPassword) {
        this.correctPassword = correctPassword;
    }

    /**
     * Checks a password attempt against the correct one.
     *
     * @param passwordAttempt the password entered by the user
     * @return true if the password matches, false otherwise
     */
    public boolean attemptAccess(String passwordAttempt) {
        return correctPassword.equals(passwordAttempt);
    }
}

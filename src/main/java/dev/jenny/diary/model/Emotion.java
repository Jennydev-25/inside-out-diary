package dev.jenny.diary.model;

/**
 * Emotions available to tag a diary moment, in the same order
 * listed in the exercise statement and shown in the console menu.
 */
public enum Emotion {

    ALEGRIA("Alegría"),
    TRISTEZA("Tristeza"),
    IRA("Ira"),
    ASCO("Asco"),
    MIEDO("Miedo"),
    ANSIEDAD("Ansiedad"),
    ENVIDIA("Envidia"),
    VERGUENZA("Vergüenza"),
    ABURRIMIENTO("Aburrimiento"),
    NOSTALGIA("Nostalgia");

    private final String displayName;

    Emotion(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the accented Spanish name shown to the user in the console.
     *
     * @return the display name of this emotion
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Resolves the emotion selected from the console menu, using the same
     * 1-based order (1 = Alegría ... 10 = Nostalgia) as the exercise statement.
     *
     * @param option the menu option chosen by the user (1-based)
     * @return the emotion matching that option
     * @throws IllegalArgumentException if the option is not between 1 and 10
     */
    public static Emotion fromOption(int option) {
        if (option < 1 || option > values().length) {
            throw new IllegalArgumentException("Invalid emotion option: " + option);
        }
        return values()[option - 1];
    }
}

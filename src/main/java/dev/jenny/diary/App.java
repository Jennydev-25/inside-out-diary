package dev.jenny.diary;

import dev.jenny.diary.views.AccessView;

/**
 * Entry point of the console app.
 */
public final class App {
    private App() {
    }

    /**
     * Launches the diary, starting with the password gate.
     *
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        AccessView.printAccessMenu();
    }
}

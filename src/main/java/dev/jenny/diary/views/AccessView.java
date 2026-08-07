package dev.jenny.diary.views;

import dev.jenny.diary.singletons.AccessServiceSingleton;

/** Screen that gates access to the diary behind a password. */
public class AccessView extends View {

    /** Reads the diary password and, if correct, opens the diary menu. */
    public static void printAccessMenu() {
        System.out.println("Introduzca la contraseña para acceder a su diario:");
        String passwordAttempt = SCANNER.nextLine();

        if (AccessServiceSingleton.getInstance().attemptAccess(passwordAttempt)) {
            System.out.println("Acceso concedido.");
            DiaryView.printMenu();
        }
    }
}

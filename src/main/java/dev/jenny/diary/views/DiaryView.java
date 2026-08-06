package dev.jenny.diary.views;

import java.util.InputMismatchException;

/**
 * Main menu of the diary, shown after the password gate grants access.
 */
public class DiaryView extends View {

    /** Prints the main menu and dispatches to the chosen option. */
    public static void printMenu() {
        String text = """
                Mi Diario:
                1. Añadir momento
                2. Ver todos los momentos disponibles
                3. Eliminar un momento
                4. Filtrar los momentos
                5. Modificar un momento
                6. Exportar a CSV
                7. Salir
                Seleccione una opción: """;

        System.out.print(text);
        String input = SCANNER.nextLine();

        try {
            int option = Integer.parseInt(input);

            if (option < 1 || option > 7) {
                throw new InputMismatchException("Número introducido fuera de rango");
            }

            if (option == 1) {
                MomentAddView.printAddMenu();
            }
            if (option == 2) {
                MomentListView.printListMenu();
            }
            if (option == 3) {
                MomentDeleteView.printDeleteMenu();
            }
            if (option == 7) {
                out();
            }

        } catch (Exception e) {
            System.out.println("\nDebe introducir un valor válido (1-7). " + e.getMessage() + "\n");
            printMenu();
        }
    }

    /** Closes the shared Scanner when the user chooses to exit. */
    private static void out() {
        SCANNER.close();
    }

}

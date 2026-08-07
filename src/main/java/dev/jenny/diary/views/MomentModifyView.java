package dev.jenny.diary.views;

import java.util.InputMismatchException;

import dev.jenny.diary.controllers.DiaryController;
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/**
 * View responsible for modifying a moment's title, description, emotion, or
 * date.
 */
public class MomentModifyView extends View {

    private static final DiaryController CONTROLLER = DiaryControllerSingleton.getInstance();

    /** Requests a moment's id and which field to modify, then delegates to it. */
    public static void printModifyMenu() {
        try {
            System.out.println();
            System.out.print("Ingresa el identificador del momento: ");
            Long id = Long.parseLong(SCANNER.nextLine());

            String text = """

                    Modificar...:
                    1. Título
                    2. Descripción
                    3. Emoción
                    4. Fecha
                    Ingrese una opción: """;
            System.out.print(text);
            int fieldOption = Integer.parseInt(SCANNER.nextLine());

            if (fieldOption < 1 || fieldOption > 4) {
                throw new InputMismatchException("Número introducido fuera de rango");
            }

            if (fieldOption == 1) {
                modifyTitle(id);
            }
            if (fieldOption == 2) {
                modifyDescription(id);
            }

        } catch (Exception e) {
            System.out.println("\nDatos introducidos no válidos. " + e.getMessage() + "\n");
            printModifyMenu();
        }
    }

    /** Requests a new title and updates the moment through the Controller. */
    private static void modifyTitle(Long id) {
        System.out.print("Ingresa el nuevo título: ");
        String title = SCANNER.nextLine();

        CONTROLLER.updateMomentTitle(id, title);

        System.out.println("\nMomento modificado correctamente.\n");
        DiaryView.printMenu();
    }

    /** Requests a new description and updates the moment through the Controller. */
    private static void modifyDescription(Long id) {
        System.out.print("Ingresa la nueva descripción: ");
        String description = SCANNER.nextLine();

        CONTROLLER.updateMomentDescription(id, description);

        System.out.println("\nMomento modificado correctamente.\n");
        DiaryView.printMenu();
    }

}

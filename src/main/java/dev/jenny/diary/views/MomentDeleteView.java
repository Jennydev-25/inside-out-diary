package dev.jenny.diary.views;

import dev.jenny.diary.controllers.DiaryController;
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/**
 * View responsible for requesting a moment's id and deleting it.
 */
public class MomentDeleteView extends View {

    private static final DiaryController CONTROLLER = DiaryControllerSingleton.getInstance();

    /** Requests a moment's id and deletes it through the Controller. */
    public static void printDeleteMenu() {
        try {
            System.out.println();
            System.out.print("Ingresa el identificador del momento: ");
            Long id = Long.parseLong(SCANNER.nextLine());

            CONTROLLER.deleteMoment(id);

            System.out.println("\nMomento eliminado correctamente.\n");
            DiaryView.printMenu();

        } catch (Exception e) {
            System.out.println("\nDatos introducidos no válidos. " + e.getMessage() + "\n");
            printDeleteMenu();
        }
    }

}

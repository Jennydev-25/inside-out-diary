package dev.jenny.diary.views;

import dev.jenny.diary.controllers.DiaryController;
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/**
 * View responsible for exporting every stored moment to a CSV file.
 */
public class MomentExportView extends View {

    private static final DiaryController CONTROLLER = DiaryControllerSingleton.getInstance();

    /** Exports every stored moment to CSV through the Controller. */
    public static void printExportMenu() {
        CONTROLLER.exportMoments();

        System.out.println("\nDiario exportado correctamente.\n");
        DiaryView.printMenu();
    }

}

package dev.jenny.diary.views;

import java.util.List;

import dev.jenny.diary.controllers.DiaryController;
import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/**
 * View responsible for listing stored moments, either all of them or
 * a subset already filtered by {@link MomentFilterView}.
 */
public class MomentListView extends View {

    private static final DiaryController CONTROLLER = DiaryControllerSingleton.getInstance();

    /** Lists every stored moment through the Controller. */
    public static void printListMenu() {
        printMomentsList(CONTROLLER.getAllMoments());
        DiaryView.printMenu();
    }

    /**
     * Prints a numbered list of moments, in the same format used by
     * every filter option. Package-private so {@link MomentFilterView}
     * can reuse it without duplicating the printing logic.
     */
    static void printMomentsList(List<MomentDto> moments) {
        System.out.println();
        System.out.println("Lista de momentos vividos:");
        int position = 1;
        for (MomentDto moment : moments) {
            System.out.println(position + ". Ocurrio el: " + moment.momentDate().format(DATE_FORMATTER)
                    + ". Título: " + moment.title()
                    + ". Descripción: " + moment.description()
                    + ". Emoción: " + moment.emotion().getDisplayName());
            position++;
        }
        System.out.println();
    }

}

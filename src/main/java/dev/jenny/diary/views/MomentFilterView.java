package dev.jenny.diary.views;

import java.util.InputMismatchException;

import dev.jenny.diary.controllers.DiaryController;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/**
 * View responsible for filtering moments by emotion, month, or date.
 */
public class MomentFilterView extends View {

    private static final DiaryController CONTROLLER = DiaryControllerSingleton.getInstance();

    /** Requests which field to filter by, then delegates to that filter. */
    public static void printFilterMenu() {
        try {
            String text = """

                    Filtrar por...:
                    1. Emoción
                    2. Mes
                    3. Fecha
                    Ingrese una opción: """;
            System.out.print(text);
            int filterOption = Integer.parseInt(SCANNER.nextLine());

            if (filterOption < 1 || filterOption > 3) {
                throw new InputMismatchException("Número introducido fuera de rango");
            }

            if (filterOption == 1) {
                filterByEmotion();
            }

        } catch (Exception e) {
            System.out.println("\nDatos introducidos no válidos. " + e.getMessage() + "\n");
            printFilterMenu();
        }
    }

    /** Requests an emotion and lists the moments tagged with it. */
    private static void filterByEmotion() {
        System.out.println();
        System.out.println("Selecciona una emoción:");
        printEmotionOptions();
        int emotionOption = Integer.parseInt(SCANNER.nextLine());
        Emotion emotion = Emotion.fromOption(emotionOption);

        MomentListView.printMomentsList(CONTROLLER.getMomentsByEmotion(emotion));
        DiaryView.printMenu();
    }

}

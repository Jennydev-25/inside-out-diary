package dev.jenny.diary.views;

import java.time.LocalDate;

import dev.jenny.diary.controllers.DiaryController;
import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/**
 * View responsible for requesting a new moment's data and adding it
 * through the Controller.
 */
public class MomentAddView extends View {

    private static final DiaryController CONTROLLER = DiaryControllerSingleton.getInstance();

    /** Requests a new moment's data and adds it through the Controller. */
    public static void printAddMenu() {
        try {
            System.out.println();
            System.out.print("Ingrese el título: ");
            String title = SCANNER.nextLine();

            System.out.print("Ingresa la fecha (dd/mm/aaaa): ");
            LocalDate momentDate = LocalDate.parse(SCANNER.nextLine(), DATE_FORMATTER);

            System.out.print("Ingrese la descripción: ");
            String description = SCANNER.nextLine();

            System.out.println();
            System.out.println("Selecciona una emoción:");
            printEmotionOptions();
            int emotionOption = Integer.parseInt(SCANNER.nextLine());
            Emotion emotion = Emotion.fromOption(emotionOption);

            MomentDto momentDto = new MomentDto(null, title, description, emotion, momentDate);
            CONTROLLER.addMoment(momentDto);

            System.out.println("\nMomento añadido correctamente.\n");
            DiaryView.printMenu();

        } catch (Exception e) {
            System.out.println("\nDatos introducidos no válidos. " + e.getMessage() + "\n");
            printAddMenu();
        }
    }

}

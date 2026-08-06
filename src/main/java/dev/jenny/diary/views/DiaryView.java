package dev.jenny.diary.views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;

import dev.jenny.diary.controllers.DiaryController;
import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/**
 * Main menu of the diary, shown after the password gate grants access.
 */
public class DiaryView extends View {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DiaryController CONTROLLER = DiaryControllerSingleton.getInstance();

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
                addMoment();
            }
            if (option == 2) {
                printAllMoments();
            }
            if (option == 3) {
                deleteMoment();
            }
            if (option == 7) {
                out();
            }

        } catch (Exception e) {
            System.out.println("\nDebe introducir un valor válido (1-7). " + e.getMessage() + "\n");
            printMenu();
        }
    }

    /** Requests a new moment's data and adds it through the Controller. */
    private static void addMoment() {
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
            printMenu();

        } catch (Exception e) {
            System.out.println("\nDatos introducidos no válidos. " + e.getMessage() + "\n");
            addMoment();
        }
    }

    /** Lists every stored moment through the Controller. */
    private static void printAllMoments() {
        printMomentsList(CONTROLLER.getAllMoments());
        printMenu();
    }

    /** Requests a moment's id and deletes it through the Controller. */
    private static void deleteMoment() {
        try {
            System.out.println();
            System.out.print("Ingresa el identificador del momento: ");
            Long id = Long.parseLong(SCANNER.nextLine());

            CONTROLLER.deleteMoment(id);

            System.out.println("\nMomento eliminado correctamente.\n");
            printMenu();

        } catch (Exception e) {
            System.out.println("\nDatos introducidos no válidos. " + e.getMessage() + "\n");
            deleteMoment();
        }
    }

    /**
     * Prints a numbered list of moments, in the same format used by
     * the "list all" option and every filter option.
     */
    private static void printMomentsList(List<MomentDto> moments) {
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

    /** Prints the numbered list of every available emotion. */
    private static void printEmotionOptions() {
        int option = 1;
        for (Emotion emotion : Emotion.values()) {
            System.out.println(option + ". " + emotion.getDisplayName());
            option++;
        }
    }

    /** Closes the shared Scanner when the user chooses to exit. */
    private static void out() {
        SCANNER.close();
    }

}

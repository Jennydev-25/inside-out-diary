package dev.jenny.diary.views;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/**
 * Unit tests for {@link DiaryView}.
 */
class DiaryViewTest {

    private final InputStream inputStream = System.in;
    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * Verifies that choosing option 1 and completing every prompt adds
     * a new moment, prints the confirmation message, and stores the
     * moment through the Controller.
     */
    @Test
    void testPrintMenuSelectOption1AddsMomentAndPrintsConfirmation() {
        simulateInput("1",
                "Un día en el parque de atracciones",
                "01/05/2024",
                "Fui con mi familia y me monté en la montaña rusa tres veces seguidas",
                "1",
                "7");

        DiaryView.printMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Momento añadido correctamente."));

        List<MomentDto> moments = DiaryControllerSingleton.getInstance().getAllMoments();
        boolean momentWasAdded = moments.stream()
                .anyMatch(moment -> moment.title().equals("Un día en el parque de atracciones"));
        assertThat(momentWasAdded, is(true));
    }

    /**
     * Verifies that choosing option 2 lists every stored moment,
     * including one seeded directly through the Controller beforehand.
     */
    @Test
    void testPrintMenuSelectOption2ListsAllMoments() {
        MomentDto seedMoment = new MomentDto(null,
                "Una tarde de otoño en el parque",
                "Recogí hojas caídas con mi sobrina y merendamos en un banco",
                Emotion.NOSTALGIA,
                LocalDate.of(2024, 10, 12));
        DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("2", "7");

        DiaryView.printMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Lista de momentos vividos:"));
        assertThat(outputStreamCaptor.toString(), containsString("Una tarde de otoño en el parque"));
        assertThat(outputStreamCaptor.toString(), containsString("Nostalgia"));
    }

    /**
     * Verifies that choosing option 3 deletes the moment matching the
     * given id and prints the confirmation message.
     */
    @Test
    void testPrintMenuSelectOption3DeletesMomentAndPrintsConfirmation() {
        MomentDto seedMoment = new MomentDto(null,
                "Una cena con amigas del instituto",
                "Nos reímos recordando anécdotas de hace diez años",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 3, 20));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("3", savedMoment.id().toString(), "7");

        DiaryView.printMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Momento eliminado correctamente."));
    }

    /**
     * Verifies that choosing option 4 and then filtering by emotion
     * lists only the moments tagged with that emotion.
     */
    @Test
    void testPrintMenuSelectOption4FilterByEmotionListsMatchingMoments() {
        MomentDto seedMoment = new MomentDto(null,
                "Una tormenta durante la acampada",
                "Se me caló la tienda de campaña en mitad de la noche",
                Emotion.MIEDO,
                LocalDate.of(2024, 7, 2));
        DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("4", "1", "5", "7");

        DiaryView.printMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Lista de momentos vividos:"));
        assertThat(outputStreamCaptor.toString(), containsString("Una tormenta durante la acampada"));
        assertThat(outputStreamCaptor.toString(), containsString("Miedo"));
    }

    /**
     * Verifies that choosing option 4 and then filtering by month lists
     * only the moments that occurred in that month.
     */
    @Test
    void testPrintMenuSelectOption4FilterByMonthListsMatchingMoments() {
        MomentDto seedMoment = new MomentDto(null,
                "Una excursión a la montaña con el equipo de trabajo",
                "Subimos hasta el mirador y comimos allí porque hacía un día espléndido",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 6, 15));
        DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("4", "2", "06/2024", "7");

        DiaryView.printMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Lista de momentos vividos:"));
        assertThat(outputStreamCaptor.toString(),
                containsString("Una excursión a la montaña con el equipo de trabajo"));
    }

    /**
     * Verifies that choosing option 4 and then filtering by date lists
     * only the moments that occurred on that exact date.
     */
    @Test
    void testPrintMenuSelectOption4FilterByDateListsMatchingMoments() {
        MomentDto seedMoment = new MomentDto(null,
                "Una comida familiar el día de mi cumpleaños",
                "Vinieron mis padres y mi hermana, cociné yo la tarta",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 9, 8));
        DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("4", "3", "08/09/2024", "7");

        DiaryView.printMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Lista de momentos vividos:"));
        assertThat(outputStreamCaptor.toString(), containsString("Una comida familiar el día de mi cumpleaños"));
    }

    /**
     * Restores the original System.in and System.out after each test,
     * so later tests aren't affected by this test's redirection.
     */
    @AfterEach
    void tearDown() {
        System.setIn(inputStream);
        System.setOut(printStream);
    }

    /**
     * Feeds the given lines as simulated console input, reinitializing
     * the shared Scanner so it reads from the new System.in.
     *
     * @param lines the lines to feed as input, in order
     */
    private void simulateInput(String... lines) {
        String input = String.join("\n", lines) + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.SCANNER = new Scanner(System.in);
    }
}

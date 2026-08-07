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
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/** Unit tests for {@link DiaryView}. */
class DiaryViewTest {

    private final InputStream inputStream = System.in;
    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /** Verifies that an invalid main menu option shows an error and retries. */
    @ParameterizedTest(name = "selecting option \"{0}\" shows an error and retries")
    @MethodSource("invalidMenuOptions")
    void testPrintMenuWithInvalidOptionShowsErrorAndRetries(String invalidOption) {
        simulateInput(invalidOption, "7");

        DiaryView.printMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Debe introducir un valor válido (1-7)."));
    }

    private static Stream<Arguments> invalidMenuOptions() {
        return Stream.of(
                Arguments.of("abc"),
                Arguments.of("99"),
                Arguments.of("0"));
    }

    /**
     * Verifies that choosing option 1 adds a moment and prints the confirmation.
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

    /** Verifies that choosing option 2 lists every stored moment. */
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
     * Verifies that choosing option 3 deletes a moment and prints the confirmation.
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

    /** Verifies that filtering by emotion lists only matching moments. */
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
     * Verifies that modifying a moment's title updates it and prints the
     * confirmation.
     */
    @Test
    void testPrintMenuSelectOption5ModifyTitleUpdatesMomentAndPrintsConfirmation() {
        MomentDto seedMoment = new MomentDto(null,
                "Una tarde viendo películas antiguas",
                "Redescubrí una peli que no veía desde niña",
                Emotion.NOSTALGIA,
                LocalDate.of(2024, 11, 3));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("5", savedMoment.id().toString(), "1", "Una tarde de domingo viendo películas antiguas", "7");

        DiaryView.printMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Momento modificado correctamente."));

        List<MomentDto> moments = DiaryControllerSingleton.getInstance().getAllMoments();
        boolean titleWasUpdated = moments.stream()
                .anyMatch(moment -> moment.title().equals("Una tarde de domingo viendo películas antiguas"));
        assertThat(titleWasUpdated, is(true));
    }

    /**
     * Verifies that choosing option 6 exports the moments and prints the
     * confirmation.
     */
    @Test
    void testPrintMenuSelectOption6ExportsMomentsAndPrintsConfirmation() {
        simulateInput("6", "7");

        DiaryView.printMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Diario exportado correctamente."));
    }

    /** Restores System.in and System.out after each test. */
    @AfterEach
    void tearDown() {
        System.setIn(inputStream);
        System.setOut(printStream);
    }

    /**
     * Feeds the given lines as simulated console input, refreshing the Scanner.
     *
     * @param lines the lines to feed as input, in order
     */
    private void simulateInput(String... lines) {
        String input = String.join("\n", lines) + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.SCANNER = new Scanner(System.in);
    }
}

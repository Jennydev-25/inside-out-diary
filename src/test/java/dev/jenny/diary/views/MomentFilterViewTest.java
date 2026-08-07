package dev.jenny.diary.views;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
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

/** Unit tests for {@link MomentFilterView}. */
class MomentFilterViewTest {

    private final InputStream inputStream = System.in;
    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /** Verifies that an invalid filter option shows an error and retries. */
    @ParameterizedTest(name = "selecting filter option \"{0}\" shows an error and retries")
    @MethodSource("invalidFilterOptions")
    void testPrintFilterMenuWithInvalidOptionShowsErrorAndRetries(String invalidOption) {
        simulateInput(invalidOption, "1", "1", "7");

        MomentFilterView.printFilterMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Datos introducidos no válidos."));
        assertThat(outputStreamCaptor.toString(), containsString("Lista de momentos vividos:"));
    }

    private static Stream<Arguments> invalidFilterOptions() {
        return Stream.of(
                Arguments.of("abc"),
                Arguments.of("99"),
                Arguments.of("0"));
    }

    /**
     * Verifies that an invalid emotion when filtering shows an error and retries.
     */
    @ParameterizedTest(name = "selecting emotion \"{0}\" when filtering shows an error and retries")
    @MethodSource("invalidFilterEmotionOptions")
    void testPrintFilterMenuByEmotionWithInvalidOptionShowsErrorAndRetries(String invalidEmotionOption) {
        simulateInput("1", invalidEmotionOption, "1", "1", "7");

        MomentFilterView.printFilterMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Datos introducidos no válidos."));
        assertThat(outputStreamCaptor.toString(), containsString("Lista de momentos vividos:"));
    }

    private static Stream<Arguments> invalidFilterEmotionOptions() {
        return Stream.of(
                Arguments.of("abc"),
                Arguments.of("99"));
    }

    /** Verifies that filtering by emotion lists only matching moments. */
    @Test
    void testPrintFilterMenuByEmotionListsMatchingMoments() {
        MomentDto seedMoment = new MomentDto(null,
                "Una tormenta durante la acampada",
                "Se me caló la tienda de campaña en mitad de la noche",
                Emotion.MIEDO,
                LocalDate.of(2024, 7, 2));
        DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("1", "5", "7");

        MomentFilterView.printFilterMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Lista de momentos vividos:"));
        assertThat(outputStreamCaptor.toString(), containsString("Una tormenta durante la acampada"));
        assertThat(outputStreamCaptor.toString(), containsString("Miedo"));
    }

    /**
     * Verifies that an invalid month format when filtering shows an error and
     * retries.
     */
    @Test
    void testPrintFilterMenuByMonthWithInvalidFormatShowsErrorAndRetries() {
        MomentDto seedMoment = new MomentDto(null,
                "Una excursión a la montaña con el equipo de trabajo",
                "Subimos hasta el mirador y comimos allí porque hacía un día espléndido",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 6, 15));
        DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("2", "13/2024", "2", "06/2024", "7");

        MomentFilterView.printFilterMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Datos introducidos no válidos."));
        assertThat(outputStreamCaptor.toString(),
                containsString("Una excursión a la montaña con el equipo de trabajo"));
    }

    /** Verifies that filtering by month lists only matching moments. */
    @Test
    void testPrintFilterMenuByMonthListsMatchingMoments() {
        MomentDto seedMoment = new MomentDto(null,
                "Una excursión a la montaña con el equipo de trabajo",
                "Subimos hasta el mirador y comimos allí porque hacía un día espléndido",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 6, 15));
        DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("2", "06/2024", "7");

        MomentFilterView.printFilterMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Lista de momentos vividos:"));
        assertThat(outputStreamCaptor.toString(),
                containsString("Una excursión a la montaña con el equipo de trabajo"));
    }

    /**
     * Verifies that an invalid date format when filtering shows an error and
     * retries.
     */
    @Test
    void testPrintFilterMenuByDateWithInvalidFormatShowsErrorAndRetries() {
        MomentDto seedMoment = new MomentDto(null,
                "Una comida familiar el día de mi cumpleaños",
                "Vinieron mis padres y mi hermana, cociné yo la tarta",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 9, 8));
        DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("3", "32/13/2024", "3", "08/09/2024", "7");

        MomentFilterView.printFilterMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Datos introducidos no válidos."));
        assertThat(outputStreamCaptor.toString(), containsString("Una comida familiar el día de mi cumpleaños"));
    }

    /** Verifies that filtering by date lists only matching moments. */
    @Test
    void testPrintFilterMenuByDateListsMatchingMoments() {
        MomentDto seedMoment = new MomentDto(null,
                "Una comida familiar el día de mi cumpleaños",
                "Vinieron mis padres y mi hermana, cociné yo la tarta",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 9, 8));
        DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("3", "08/09/2024", "7");

        MomentFilterView.printFilterMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Lista de momentos vividos:"));
        assertThat(outputStreamCaptor.toString(), containsString("Una comida familiar el día de mi cumpleaños"));
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

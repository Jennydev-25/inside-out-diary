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

/** Unit tests for {@link MomentDeleteView}. */
class MomentDeleteViewTest {

    private final InputStream inputStream = System.in;
    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /** Verifies that deleting a moment prints the confirmation. */
    @Test
    void testPrintDeleteMenuDeletesMomentAndPrintsConfirmation() {
        MomentDto seedMoment = new MomentDto(null,
                "Una tarde jugando al ajedrez con mi padre",
                "Me ganó en diez movimientos, como siempre",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 3, 3));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput(savedMoment.id().toString(), "7");

        MomentDeleteView.printDeleteMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Momento eliminado correctamente."));
    }

    /** Verifies that an invalid id shows an error and retries. */
    @ParameterizedTest(name = "deleting a moment with id \"{0}\" shows an error and retries")
    @MethodSource("invalidDeleteIds")
    void testPrintDeleteMenuWithInvalidIdShowsErrorAndRetries(String invalidId) {
        MomentDto seedMoment = new MomentDto(null,
                "Una tarde jugando a las cartas con mi abuela",
                "Me enseñó un truco de magia que hacía siempre con la baraja española",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 4, 6));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput(invalidId, savedMoment.id().toString(), "7");

        MomentDeleteView.printDeleteMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Datos introducidos no válidos."));
        assertThat(outputStreamCaptor.toString(), containsString("Momento eliminado correctamente."));
    }

    private static Stream<Arguments> invalidDeleteIds() {
        return Stream.of(
                Arguments.of("abc"),
                Arguments.of("999999"));
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

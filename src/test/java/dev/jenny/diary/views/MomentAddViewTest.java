package dev.jenny.diary.views;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
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
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/** Unit tests for {@link MomentAddView}. */
class MomentAddViewTest {

    private final InputStream inputStream = System.in;
    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /** Verifies that adding a moment prints the confirmation. */
    @Test
    void testPrintAddMenuAddsMomentAndPrintsConfirmation() {
        simulateInput(
                "Un día en el parque de atracciones",
                "01/05/2024",
                "Fui con mi familia y me monté en la montaña rusa tres veces seguidas",
                "1",
                "7");

        MomentAddView.printAddMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Momento añadido correctamente."));

        List<MomentDto> moments = DiaryControllerSingleton.getInstance().getAllMoments();
        boolean momentWasAdded = moments.stream()
                .anyMatch(moment -> moment.title().equals("Un día en el parque de atracciones"));
        assertThat(momentWasAdded, is(true));
    }

    /** Verifies that invalid input shows an error and retries. */
    @ParameterizedTest(name = "adding a moment with invalid {0} shows an error and retries")
    @MethodSource("invalidAddScenarios")
    void testPrintAddMenuWithInvalidInputShowsErrorAndRetries(String field, List<String> failingAttempt) {
        List<String> input = new ArrayList<>();
        input.addAll(failingAttempt);
        input.add("Una tarde soleada en el parque");
        input.add("12/04/2024");
        input.add("Dimos un paseo largo y comimos un helado");
        input.add("1");
        input.add("7");

        simulateInput(input.toArray(new String[0]));

        MomentAddView.printAddMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Datos introducidos no válidos."));
        assertThat(outputStreamCaptor.toString(), containsString("Momento añadido correctamente."));
    }

    private static Stream<Arguments> invalidAddScenarios() {
        return Stream.of(
                Arguments.of("fecha", List.of(
                        "Una tarde soleada en el parque",
                        "32/13/2024")),
                Arguments.of("emoción no numérica", List.of(
                        "Una tarde soleada en el parque",
                        "12/04/2024",
                        "Dimos un paseo largo y comimos un helado",
                        "abc")),
                Arguments.of("emoción fuera de rango", List.of(
                        "Una tarde soleada en el parque",
                        "12/04/2024",
                        "Dimos un paseo largo y comimos un helado",
                        "99")));
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

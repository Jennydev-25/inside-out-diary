package dev.jenny.diary.views;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
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
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/** Unit tests for {@link MomentModifyView}. */
class MomentModifyViewTest {

    private final InputStream inputStream = System.in;
    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /** Verifies that an invalid id shows an error and retries. */
    @ParameterizedTest(name = "modifying a moment with invalid {0} shows an error and retries")
    @MethodSource("invalidModifyIdScenarios")
    void testPrintModifyMenuWithInvalidIdShowsErrorAndRetries(String scenario, List<String> failingAttempt) {
        MomentDto seedMoment = new MomentDto(null,
                "Una tarde pintando con acuarelas",
                "Probé una técnica nueva que vi en un vídeo",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 2, 10));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        List<String> input = new ArrayList<>();
        input.addAll(failingAttempt);
        input.add(savedMoment.id().toString());
        input.add("1");
        input.add("Una tarde pintando con acuarelas y café");
        input.add("7");

        simulateInput(input.toArray(new String[0]));

        MomentModifyView.printModifyMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Datos introducidos no válidos."));
        assertThat(outputStreamCaptor.toString(), containsString("Momento modificado correctamente."));
    }

    private static Stream<Arguments> invalidModifyIdScenarios() {
        return Stream.of(
                Arguments.of("id no numérico", List.of("abc")),
                Arguments.of("id inexistente", List.of("999999", "1", "Un título cualquiera")));
    }

    /** Verifies that an invalid field option shows an error and retries. */
    @ParameterizedTest(name = "selecting field option \"{0}\" shows an error and retries")
    @MethodSource("invalidFieldOptions")
    void testPrintModifyMenuWithInvalidFieldOptionShowsErrorAndRetries(String invalidFieldOption) {
        MomentDto seedMoment = new MomentDto(null,
                "Una tarde arreglando la bicicleta",
                "Se me pinchó la rueda trasera y aproveché para engrasar la cadena",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 3, 15));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput(savedMoment.id().toString(), invalidFieldOption,
                savedMoment.id().toString(), "1", "Una tarde arreglando la bicicleta con mi hermano", "7");

        MomentModifyView.printModifyMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Datos introducidos no válidos."));
        assertThat(outputStreamCaptor.toString(), containsString("Momento modificado correctamente."));
    }

    private static Stream<Arguments> invalidFieldOptions() {
        return Stream.of(
                Arguments.of("abc"),
                Arguments.of("99"));
    }

    /**
     * Verifies that modifying a moment's title updates it and prints the
     * confirmation.
     */
    @Test
    void testPrintModifyMenuTitleUpdatesMomentAndPrintsConfirmation() {
        MomentDto seedMoment = new MomentDto(null,
                "Una tarde viendo películas antiguas",
                "Redescubrí una peli que no veía desde niña",
                Emotion.NOSTALGIA,
                LocalDate.of(2024, 11, 3));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput(savedMoment.id().toString(), "1", "Una tarde de domingo viendo películas antiguas", "7");

        MomentModifyView.printModifyMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Momento modificado correctamente."));

        List<MomentDto> moments = DiaryControllerSingleton.getInstance().getAllMoments();
        boolean titleWasUpdated = moments.stream()
                .anyMatch(moment -> moment.title().equals("Una tarde de domingo viendo películas antiguas"));
        assertThat(titleWasUpdated, is(true));
    }

    /**
     * Verifies that modifying a moment's description updates it and prints the
     * confirmation.
     */
    @Test
    void testPrintModifyMenuDescriptionUpdatesMomentAndPrintsConfirmation() {
        MomentDto seedMoment = new MomentDto(null,
                "Un paseo por la playa al atardecer",
                "Caminé descalza por la orilla mientras se ponía el sol",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 8, 14));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput(savedMoment.id().toString(), "2",
                "Caminé descalza por la orilla y recogí conchas con mi hermano", "7");

        MomentModifyView.printModifyMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Momento modificado correctamente."));

        List<MomentDto> moments = DiaryControllerSingleton.getInstance().getAllMoments();
        boolean descriptionWasUpdated = moments.stream()
                .anyMatch(moment -> moment.description()
                        .equals("Caminé descalza por la orilla y recogí conchas con mi hermano"));
        assertThat(descriptionWasUpdated, is(true));
    }

    /**
     * Verifies that modifying a moment's emotion updates it and prints the
     * confirmation.
     */
    @Test
    void testPrintModifyMenuEmotionUpdatesMomentAndPrintsConfirmation() {
        MomentDto seedMoment = new MomentDto(null,
                "Me robaron una rueda del coche",
                "Salí por la mañana y me encontré el coche apoyado en un ladrillo, sin rueda",
                Emotion.IRA,
                LocalDate.of(2024, 5, 22));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput(savedMoment.id().toString(), "3", "2", "7");

        MomentModifyView.printModifyMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Momento modificado correctamente."));

        List<MomentDto> moments = DiaryControllerSingleton.getInstance().getAllMoments();
        boolean emotionWasUpdated = moments.stream()
                .anyMatch(moment -> moment.id().equals(savedMoment.id()) && moment.emotion() == Emotion.TRISTEZA);
        assertThat(emotionWasUpdated, is(true));
    }

    /**
     * Verifies that modifying a moment's date updates it and prints the
     * confirmation.
     */
    @Test
    void testPrintModifyMenuDateUpdatesMomentAndPrintsConfirmation() {
        MomentDto seedMoment = new MomentDto(null,
                "Un concierto al aire libre con amigas",
                "Cantamos todas las canciones de memoria hasta quedarnos afónicas",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 7, 19));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput(savedMoment.id().toString(), "4", "20/07/2024", "7");

        MomentModifyView.printModifyMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Momento modificado correctamente."));

        List<MomentDto> moments = DiaryControllerSingleton.getInstance().getAllMoments();
        boolean dateWasUpdated = moments.stream()
                .anyMatch(moment -> moment.id().equals(savedMoment.id())
                        && moment.momentDate().equals(LocalDate.of(2024, 7, 20)));
        assertThat(dateWasUpdated, is(true));
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

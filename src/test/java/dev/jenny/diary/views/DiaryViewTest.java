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

/** Unit tests for {@link DiaryView}. */
class DiaryViewTest {

    private final InputStream inputStream = System.in;
    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
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

    /** Verifies that filtering by month lists only matching moments. */
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

    /** Verifies that filtering by date lists only matching moments. */
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
     * Verifies that modifying a moment's description updates it and prints the
     * confirmation.
     */
    @Test
    void testPrintMenuSelectOption5ModifyDescriptionUpdatesMomentAndPrintsConfirmation() {
        MomentDto seedMoment = new MomentDto(null,
                "Un paseo por la playa al atardecer",
                "Caminé descalza por la orilla mientras se ponía el sol",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 8, 14));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("5", savedMoment.id().toString(), "2",
                "Caminé descalza por la orilla y recogí conchas con mi hermano", "7");

        DiaryView.printMenu();

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
    void testPrintMenuSelectOption5ModifyEmotionUpdatesMomentAndPrintsConfirmation() {
        MomentDto seedMoment = new MomentDto(null,
                "Me robaron una rueda del coche",
                "Salí por la mañana y me encontré el coche apoyado en un ladrillo, sin rueda",
                Emotion.IRA,
                LocalDate.of(2024, 5, 22));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("5", savedMoment.id().toString(), "3", "2", "7");

        DiaryView.printMenu();

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
    void testPrintMenuSelectOption5ModifyDateUpdatesMomentAndPrintsConfirmation() {
        MomentDto seedMoment = new MomentDto(null,
                "Un concierto al aire libre con amigas",
                "Cantamos todas las canciones de memoria hasta quedarnos afónicas",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 7, 19));
        MomentDto savedMoment = DiaryControllerSingleton.getInstance().addMoment(seedMoment);

        simulateInput("5", savedMoment.id().toString(), "4", "20/07/2024", "7");

        DiaryView.printMenu();

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

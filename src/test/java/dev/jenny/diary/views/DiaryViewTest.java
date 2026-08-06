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
        String input = String.format("%s\n%s\n%s\n%s\n%s\n%s\n",
                "1",
                "Un día en el parque de atracciones",
                "01/05/2024",
                "Fui con mi familia y me monté en la montaña rusa tres veces seguidas",
                "1",
                "7");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.SCANNER = new Scanner(System.in);

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

        String input = String.format("%s\n%s\n", "2", "7");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.SCANNER = new Scanner(System.in);

        DiaryView.printMenu();

        assertThat(outputStreamCaptor.toString(), containsString("Lista de momentos vividos:"));
        assertThat(outputStreamCaptor.toString(), containsString("Una tarde de otoño en el parque"));
        assertThat(outputStreamCaptor.toString(), containsString("Nostalgia"));
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
}

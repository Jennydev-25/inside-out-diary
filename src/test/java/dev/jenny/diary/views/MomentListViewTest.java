package dev.jenny.diary.views;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.singletons.DiaryControllerSingleton;

/** Unit tests for {@link MomentListView}. */
class MomentListViewTest {

    private final InputStream inputStream = System.in;
    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /** Verifies that every stored moment is listed and numbered correctly. */
    @Test
    void testPrintListMenuListsAndNumbersEveryMoment() {
        MomentDto firstMoment = new MomentDto(null,
                "Una tarde estudiando en la biblioteca",
                "Encontré un rincón tranquilo junto a la ventana",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 1, 15));
        MomentDto secondMoment = new MomentDto(null,
                "Una mañana lloviendo sin parar",
                "Me quedé en casa leyendo con una manta",
                Emotion.NOSTALGIA,
                LocalDate.of(2024, 1, 20));
        DiaryControllerSingleton.getInstance().addMoment(firstMoment);
        DiaryControllerSingleton.getInstance().addMoment(secondMoment);

        simulateInput("7");

        MomentListView.printListMenu();

        assertThat(outputStreamCaptor.toString(), containsString("1. Ocurrio el"));
        assertThat(outputStreamCaptor.toString(), containsString("Una tarde estudiando en la biblioteca"));
        assertThat(outputStreamCaptor.toString(), containsString("Una mañana lloviendo sin parar"));
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

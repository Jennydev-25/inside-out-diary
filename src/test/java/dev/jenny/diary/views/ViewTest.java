package dev.jenny.diary.views;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jenny.diary.models.Emotion;

/** Unit tests for {@link View}. */
class ViewTest {

    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /** Verifies that every emotion is listed with its correct number. */
    @Test
    void testPrintEmotionOptionsListsEveryEmotionNumbered() {
        View.printEmotionOptions();

        int option = 1;
        for (Emotion emotion : Emotion.values()) {
            assertThat(outputStreamCaptor.toString(), containsString(option + ". " + emotion.getDisplayName()));
            option++;
        }
    }

    /** Restores System.out after each test. */
    @AfterEach
    void tearDown() {
        System.setOut(printStream);
    }
}

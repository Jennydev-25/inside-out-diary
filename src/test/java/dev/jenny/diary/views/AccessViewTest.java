package dev.jenny.diary.views;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mockStatic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import dev.jenny.diary.services.AccessService;
import dev.jenny.diary.singletons.AccessServiceSingleton;

/** Unit tests for {@link AccessView}. */
class AccessViewTest {

    private final InputStream inputStream = System.in;
    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /** Verifies that a correct password grants access and opens the diary menu. */
    @Test
    void testPrintAccessMenuWithCorrectPasswordGrantsAccess() {
        try (MockedStatic<AccessServiceSingleton> mockedSingleton = mockStatic(AccessServiceSingleton.class)) {
            AccessService testAccessService = new AccessService("test-password");
            mockedSingleton.when(AccessServiceSingleton::getInstance).thenReturn(testAccessService);

            simulateInput("test-password");

            try (MockedStatic<DiaryView> mockedDiaryView = mockStatic(DiaryView.class)) {
                AccessView.printAccessMenu();

                mockedDiaryView.verify(DiaryView::printMenu);
            }

            assertThat(outputStreamCaptor.toString(), containsString("Acceso concedido."));
        }
    }

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

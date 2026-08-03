package dev.jenny.diary.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit tests for the {@link Emotion} enum.
 */
class EmotionTest {

    /**
     * Verifies that fromOption maps the menu option 1 to ALEGRIA,
     * and that its display name is the accented Spanish word "Alegría".
     */
    @Test
    void testFromOptionReturnsMatchingEmotion() {
        int option = 1;

        Emotion emotion = Emotion.fromOption(option);

        assertThat(emotion, is(equalTo(Emotion.ALEGRIA)));
        assertThat(emotion.getDisplayName(), is(equalTo("Alegría")));
    }

    /**
     * Verifies that an option outside the 1-10 range is rejected
     * with a clear error instead of a raw array index exception.
     */
    @Test
    void testFromOptionWithOutOfRangeOptionThrowsIllegalArgumentException() {
        int invalidOption = 15;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Emotion.fromOption(invalidOption));

        assertThat(exception.getMessage(), is(equalTo("Invalid emotion option: 15")));
    }

    /**
     * Verifies that every menu option (1 to 10) resolves to the correct
     * emotion and its correct accented display name, following the same
     * order as the exercise statement.
     */
    @ParameterizedTest(name = "option {0} is expected to resolve to {1} ({2})")
    @MethodSource("emotionOptions")
    void testFromOptionReturnsCorrectEmotionForEachOption(
            int option, Emotion expectedEmotion, String expectedDisplayName) {
        Emotion emotion = Emotion.fromOption(option);

        assertThat(emotion, is(equalTo(expectedEmotion)));
        assertThat(emotion.getDisplayName(), is(equalTo(expectedDisplayName)));
    }

    private static Stream<Arguments> emotionOptions() {
        return Stream.of(
                Arguments.of(1, Emotion.ALEGRIA, "Alegría"),
                Arguments.of(2, Emotion.TRISTEZA, "Tristeza"),
                Arguments.of(3, Emotion.IRA, "Ira"),
                Arguments.of(4, Emotion.ASCO, "Asco"),
                Arguments.of(5, Emotion.MIEDO, "Miedo"),
                Arguments.of(6, Emotion.ANSIEDAD, "Ansiedad"),
                Arguments.of(7, Emotion.ENVIDIA, "Envidia"),
                Arguments.of(8, Emotion.VERGUENZA, "Vergüenza"),
                Arguments.of(9, Emotion.ABURRIMIENTO, "Aburrimiento"),
                Arguments.of(10, Emotion.NOSTALGIA, "Nostalgia"));
    }
}

package dev.jenny.diary.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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
}

package dev.jenny.diary.dtos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import dev.jenny.diary.models.Emotion;

/**
 * Unit tests for the {@link MomentDto} record.
 */
class MomentDtoTest {

    private static final long MOMENT_ID = 1L;
    private static final String MOMENT_TITLE = "Un día en el parque de atracciones";
    private static final String MOMENT_DESCRIPTION = "Un día genial con amigos";
    private static final Emotion MOMENT_EMOTION = Emotion.ALEGRIA;
    private static final LocalDate MOMENT_DATE = LocalDate.of(2024, 5, 1);

    /**
     * Verifies that a MomentDto exposes all the data it was given,
     * without the internal creation/modification timestamps.
     */
    @Test
    void testMomentDtoExposesAllItsFields() {
        MomentDto dto = new MomentDto(MOMENT_ID, MOMENT_TITLE, MOMENT_DESCRIPTION, MOMENT_EMOTION, MOMENT_DATE);

        assertThat(dto.id(), is(equalTo(MOMENT_ID)));
        assertThat(dto.title(), is(equalTo(MOMENT_TITLE)));
        assertThat(dto.description(), is(equalTo(MOMENT_DESCRIPTION)));
        assertThat(dto.emotion(), is(equalTo(MOMENT_EMOTION)));
        assertThat(dto.momentDate(), is(equalTo(MOMENT_DATE)));
    }
}

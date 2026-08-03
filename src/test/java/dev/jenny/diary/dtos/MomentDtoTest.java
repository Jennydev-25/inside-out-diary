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

    /**
     * Verifies that a MomentDto exposes all the data it was given,
     * without the internal creation/modification timestamps.
     */
    @Test
    void testMomentDtoExposesAllItsFields() {
        MomentDto dto = new MomentDto(
                1L,
                "Un día en el parque de atracciones",
                "Un día genial con amigos",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 5, 1));

        assertThat(dto.id(), is(equalTo(1L)));
        assertThat(dto.title(), is(equalTo("Un día en el parque de atracciones")));
        assertThat(dto.description(), is(equalTo("Un día genial con amigos")));
        assertThat(dto.emotion(), is(equalTo(Emotion.ALEGRIA)));
        assertThat(dto.momentDate(), is(equalTo(LocalDate.of(2024, 5, 1))));
    }
}

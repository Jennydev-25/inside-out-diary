package dev.jenny.diary.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.models.Moment;

/**
 * Unit tests for the {@link MomentMapper}.
 */
class MomentMapperTest {

    private final MomentMapper mapper = new MomentMapper();

    /**
     * Verifies that toDto converts a Moment into a MomentDto with the
     * same visible data, leaving the internal timestamps out.
     */
    @Test
    void testToDtoConvertsMomentIntoMomentDto() {
        Moment moment = new Moment(
                "Un día en el parque de atracciones",
                "Un día genial con amigos",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 5, 1));

        MomentDto dto = mapper.toDto(moment);

        assertThat(dto.id(), is(equalTo(moment.getId())));
        assertThat(dto.title(), is(equalTo(moment.getTitle())));
        assertThat(dto.description(), is(equalTo(moment.getDescription())));
        assertThat(dto.emotion(), is(equalTo(moment.getEmotion())));
        assertThat(dto.momentDate(), is(equalTo(moment.getMomentDate())));
    }

    /**
     * Verifies that toModel converts a MomentDto into a Moment with
     * the same visible data.
     */
    @Test
    void testToModelConvertsMomentDtoIntoMoment() {
        MomentDto dto = new MomentDto(
                1L,
                "Un día en el parque de atracciones",
                "Un día genial con amigos",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 5, 1));

        Moment moment = mapper.toModel(dto);

        assertThat(moment.getTitle(), is(equalTo(dto.title())));
        assertThat(moment.getDescription(), is(equalTo(dto.description())));
        assertThat(moment.getEmotion(), is(equalTo(dto.emotion())));
        assertThat(moment.getMomentDate(), is(equalTo(dto.momentDate())));
    }
}

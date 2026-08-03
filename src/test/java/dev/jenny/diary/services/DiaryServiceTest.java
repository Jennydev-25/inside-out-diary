package dev.jenny.diary.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.mocks.FakeMomentRepository;
import dev.jenny.diary.models.Emotion;

/**
 * Unit tests for the {@link DiaryService}.
 */
class DiaryServiceTest {

    private static final String MOMENT_TITLE = "Un día en el parque de atracciones";
    private static final String MOMENT_DESCRIPTION = "Un día genial con amigos";
    private static final Emotion MOMENT_EMOTION = Emotion.ALEGRIA;
    private static final LocalDate MOMENT_DATE = LocalDate.of(2024, 5, 1);

    private DiaryService diaryService;

    @BeforeEach
    void setUp() {
        diaryService = new DiaryService(new FakeMomentRepository());
    }

    /**
     * Verifies that addMoment saves the moment and returns it as a
     * MomentDto with the id assigned by the repository.
     */
    @Test
    void testAddMomentSavesMomentAndReturnsDtoWithId() {
        MomentDto momentDto = new MomentDto(null, MOMENT_TITLE, MOMENT_DESCRIPTION, MOMENT_EMOTION, MOMENT_DATE);

        MomentDto saved = diaryService.addMoment(momentDto);

        assertThat(saved.id(), is(notNullValue()));
        assertThat(saved.title(), is(equalTo(MOMENT_TITLE)));
        assertThat(saved.description(), is(equalTo(MOMENT_DESCRIPTION)));
        assertThat(saved.emotion(), is(equalTo(MOMENT_EMOTION)));
        assertThat(saved.momentDate(), is(equalTo(MOMENT_DATE)));
    }
}

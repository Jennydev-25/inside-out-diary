package dev.jenny.diary.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.mocks.FakeMomentRepository;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.services.DiaryService;

/**
 * Unit tests for the {@link DiaryController}.
 */
class DiaryControllerTest {

    private static final String MOMENT_TITLE = "Un día en el parque de atracciones";
    private static final String MOMENT_DESCRIPTION = "Un día genial con amigos";
    private static final Emotion MOMENT_EMOTION = Emotion.ALEGRIA;
    private static final LocalDate MOMENT_DATE = LocalDate.of(2024, 5, 1);

    private DiaryController diaryController;

    @BeforeEach
    void setUp() {
        DiaryService diaryService = new DiaryService(new FakeMomentRepository());
        diaryController = new DiaryController(diaryService);
    }

    /**
     * Verifies that addMoment delegates to the service and returns the
     * saved moment as a DTO with the assigned id.
     */
    @Test
    void testAddMomentReturnsSavedMomentDtoWithId() {
        MomentDto momentDto = new MomentDto(null, MOMENT_TITLE, MOMENT_DESCRIPTION, MOMENT_EMOTION, MOMENT_DATE);

        MomentDto saved = diaryController.addMoment(momentDto);

        assertThat(saved.id(), is(notNullValue()));
        assertThat(saved.title(), is(equalTo(MOMENT_TITLE)));
    }

    /**
     * Verifies that getAllMoments delegates to the service and returns
     * every saved moment as a DTO.
     */
    @Test
    void testGetAllMomentsReturnsAllSavedMomentsAsDtos() {
        MomentDto momentDto = new MomentDto(null, MOMENT_TITLE, MOMENT_DESCRIPTION, MOMENT_EMOTION, MOMENT_DATE);
        diaryController.addMoment(momentDto);

        List<MomentDto> moments = diaryController.getAllMoments();

        assertThat(moments, hasSize(1));
        assertThat(moments.get(0).title(), is(equalTo(MOMENT_TITLE)));
    }
}

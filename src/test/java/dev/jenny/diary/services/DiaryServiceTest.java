package dev.jenny.diary.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.mocks.FakeMomentRepository;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.models.Moment;

/**
 * Unit tests for the {@link DiaryService}.
 */
class DiaryServiceTest {

    private static final String MOMENT_TITLE = "Un día en el parque de atracciones";
    private static final String MOMENT_DESCRIPTION = "Un día genial con amigos";
    private static final Emotion MOMENT_EMOTION = Emotion.ALEGRIA;
    private static final LocalDate MOMENT_DATE = LocalDate.of(2024, 5, 1);

    private static final String OTHER_MOMENT_TITLE = "Un día lluvioso";
    private static final String OTHER_MOMENT_DESCRIPTION = "Me quedé en casa todo el día";
    private static final Emotion OTHER_MOMENT_EMOTION = Emotion.TRISTEZA;
    private static final LocalDate OTHER_MOMENT_DATE = LocalDate.of(2024, 6, 10);

    private FakeMomentRepository fakeMomentRepository;
    private DiaryService diaryService;

    @BeforeEach
    void setUp() {
        fakeMomentRepository = new FakeMomentRepository();
        diaryService = new DiaryService(fakeMomentRepository);
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

    /**
     * Verifies that getAllMoments returns every saved moment as a DTO.
     */
    @Test
    void testGetAllMomentsReturnsAllSavedMomentsAsDtos() {
        addStandardMoment();

        List<MomentDto> moments = diaryService.getAllMoments();

        assertThat(moments, hasSize(1));
        assertThat(moments.get(0).title(), is(equalTo(MOMENT_TITLE)));
    }

    /**
     * Verifies that deleteMoment removes an existing moment.
     */
    @Test
    void testDeleteMomentRemovesExistingMoment() {
        Moment moment = addStandardMoment();

        diaryService.deleteMoment(moment.getId());

        assertThat(diaryService.getAllMoments(), hasSize(0));
    }

    /**
     * Verifies that deleteMoment throws IllegalArgumentException when
     * no moment exists with the given id.
     */
    @Test
    void testDeleteMomentWithNonExistingIdThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> diaryService.deleteMoment(99L));
    }

    /**
     * Verifies that getMomentsByEmotion returns only the moments
     * tagged with the given emotion.
     */
    @Test
    void testGetMomentsByEmotionReturnsOnlyMatchingMoments() {
        saveTwoMoments();

        List<MomentDto> moments = diaryService.getMomentsByEmotion(Emotion.ALEGRIA);

        assertThat(moments, hasSize(1));
        assertThat(moments.get(0).emotion(), is(equalTo(Emotion.ALEGRIA)));
    }

    /**
     * Verifies that getMomentsByMonth returns only the moments that
     * occurred in the given year and month.
     */
    @Test
    void testGetMomentsByMonthReturnsOnlyMatchingMoments() {
        saveTwoMoments();

        List<MomentDto> moments = diaryService.getMomentsByMonth(YearMonth.of(2024, 5));

        assertThat(moments, hasSize(1));
        assertThat(moments.get(0).momentDate(), is(equalTo(MOMENT_DATE)));
    }

    /**
     * Verifies that getMomentsByDate returns only the moments that
     * occurred on the exact given date.
     */
    @Test
    void testGetMomentsByDateReturnsOnlyMatchingMoments() {
        saveTwoMoments();

        List<MomentDto> moments = diaryService.getMomentsByDate(MOMENT_DATE);

        assertThat(moments, hasSize(1));
        assertThat(moments.get(0).momentDate(), is(equalTo(MOMENT_DATE)));
    }

    /**
     * Verifies that updateMomentEmotion changes the emotion of an
     * existing moment and returns it as an updated DTO.
     */
    @Test
    void testUpdateMomentEmotionUpdatesExistingMoment() {
        Moment moment = addStandardMoment();

        MomentDto updated = diaryService.updateMomentEmotion(moment.getId(), Emotion.TRISTEZA);

        assertThat(updated.emotion(), is(equalTo(Emotion.TRISTEZA)));
    }

    /**
     * Verifies that updateMomentEmotion throws IllegalArgumentException
     * when no moment exists with the given id.
     */
    @Test
    void testUpdateMomentEmotionWithNonExistingIdThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> diaryService.updateMomentEmotion(99L, Emotion.TRISTEZA));
    }

    /**
     * Saves the standard test moment directly through the fake
     * repository. Shared by every test that needs a moment already
     * saved.
     */
    private Moment addStandardMoment() {
        Moment moment = new Moment(MOMENT_TITLE, MOMENT_DESCRIPTION, MOMENT_EMOTION, MOMENT_DATE);
        fakeMomentRepository.save(moment);
        return moment;
    }

    /**
     * Saves the standard test moment and a second, different one.
     * Shared by every filtering test.
     */
    private void saveTwoMoments() {
        addStandardMoment();
        fakeMomentRepository.save(
                new Moment(OTHER_MOMENT_TITLE, OTHER_MOMENT_DESCRIPTION, OTHER_MOMENT_EMOTION, OTHER_MOMENT_DATE));
    }
}

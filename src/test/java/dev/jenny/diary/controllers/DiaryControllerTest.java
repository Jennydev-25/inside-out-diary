package dev.jenny.diary.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.mocks.FakeMomentCsvDao;
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

    private static final String OTHER_MOMENT_TITLE = "Un día lluvioso";
    private static final String OTHER_MOMENT_DESCRIPTION = "Me quedé en casa todo el día";
    private static final Emotion OTHER_MOMENT_EMOTION = Emotion.TRISTEZA;
    private static final LocalDate OTHER_MOMENT_DATE = LocalDate.of(2024, 6, 10);

    private DiaryController diaryController;

    @BeforeEach
    void setUp() {
        DiaryService diaryService = new DiaryService(new FakeMomentRepository(), new FakeMomentCsvDao());
        diaryController = new DiaryController(diaryService);
    }

    /**
     * Verifies that addMoment delegates to the service and returns the
     * saved moment as a DTO with the assigned id.
     */
    @Test
    void testAddMomentReturnsSavedMomentDtoWithId() {
        MomentDto saved = addStandardMoment();

        assertThat(saved.id(), is(notNullValue()));
        assertThat(saved.title(), is(equalTo(MOMENT_TITLE)));
    }

    /**
     * Verifies that getAllMoments delegates to the service and returns
     * every saved moment as a DTO.
     */
    @Test
    void testGetAllMomentsReturnsAllSavedMomentsAsDtos() {
        addStandardMoment();

        List<MomentDto> moments = diaryController.getAllMoments();

        assertThat(moments, hasSize(1));
        assertThat(moments.get(0).title(), is(equalTo(MOMENT_TITLE)));
    }

    /**
     * Verifies that deleteMoment delegates to the service and removes
     * an existing moment.
     */
    @Test
    void testDeleteMomentRemovesExistingMoment() {
        MomentDto saved = addStandardMoment();

        diaryController.deleteMoment(saved.id());

        assertThat(diaryController.getAllMoments(), hasSize(0));
    }

    /**
     * Verifies that getMomentsByEmotion delegates to the service and
     * returns only the moments tagged with the given emotion.
     */
    @Test
    void testGetMomentsByEmotionReturnsOnlyMatchingMoments() {
        addTwoMoments();

        List<MomentDto> moments = diaryController.getMomentsByEmotion(Emotion.ALEGRIA);

        assertThat(moments, hasSize(1));
        assertThat(moments.get(0).emotion(), is(equalTo(Emotion.ALEGRIA)));
    }

    /**
     * Verifies that getMomentsByMonth delegates to the service and
     * returns only the moments that occurred in the given year and
     * month.
     */
    @Test
    void testGetMomentsByMonthReturnsOnlyMatchingMoments() {
        addTwoMoments();

        List<MomentDto> moments = diaryController.getMomentsByMonth(YearMonth.of(2024, 5));

        assertThat(moments, hasSize(1));
        assertThat(moments.get(0).momentDate(), is(equalTo(MOMENT_DATE)));
    }

    /**
     * Verifies that getMomentsByDate delegates to the service and
     * returns only the moments that occurred on the exact given date.
     */
    @Test
    void testGetMomentsByDateReturnsOnlyMatchingMoments() {
        addTwoMoments();

        List<MomentDto> moments = diaryController.getMomentsByDate(MOMENT_DATE);

        assertThat(moments, hasSize(1));
        assertThat(moments.get(0).momentDate(), is(equalTo(MOMENT_DATE)));
    }

    /**
     * Verifies that updateMomentEmotion delegates to the service and
     * returns the updated moment as a DTO.
     */
    @Test
    void testUpdateMomentEmotionUpdatesExistingMoment() {
        MomentDto saved = addStandardMoment();

        MomentDto updated = diaryController.updateMomentEmotion(saved.id(), Emotion.TRISTEZA);

        assertThat(updated.emotion(), is(equalTo(Emotion.TRISTEZA)));
    }

    /**
     * Verifies that updateMomentTitle delegates to the service and
     * returns the updated moment as a DTO.
     */
    @Test
    void testUpdateMomentTitleUpdatesExistingMoment() {
        MomentDto saved = addStandardMoment();

        MomentDto updated = diaryController.updateMomentTitle(saved.id(), "Un día en la playa");

        assertThat(updated.title(), is(equalTo("Un día en la playa")));
    }

    /**
     * Verifies that updateMomentDescription delegates to the service
     * and returns the updated moment as a DTO.
     */
    @Test
    void testUpdateMomentDescriptionUpdatesExistingMoment() {
        MomentDto saved = addStandardMoment();

        MomentDto updated = diaryController.updateMomentDescription(saved.id(),
                "Un día tranquilo en la playa, escuchando el mar.");

        assertThat(updated.description(), is(equalTo("Un día tranquilo en la playa, escuchando el mar.")));
    }

    /**
     * Verifies that updateMomentDate delegates to the service and
     * returns the updated moment as a DTO.
     */
    @Test
    void testUpdateMomentDateUpdatesExistingMoment() {
        MomentDto saved = addStandardMoment();

        MomentDto updated = diaryController.updateMomentDate(saved.id(), LocalDate.of(2024, 8, 15));

        assertThat(updated.momentDate(), is(equalTo(LocalDate.of(2024, 8, 15))));
    }

    /**
     * Adds the standard test moment through the controller. Shared by
     * every test that needs a moment already saved.
     */
    private MomentDto addStandardMoment() {
        MomentDto momentDto = new MomentDto(null, MOMENT_TITLE, MOMENT_DESCRIPTION, MOMENT_EMOTION, MOMENT_DATE);
        return diaryController.addMoment(momentDto);
    }

    /**
     * Adds the standard test moment and a second, different one.
     * Shared by every filtering test.
     */
    private void addTwoMoments() {
        addStandardMoment();
        diaryController.addMoment(
                new MomentDto(null, OTHER_MOMENT_TITLE, OTHER_MOMENT_DESCRIPTION, OTHER_MOMENT_EMOTION,
                        OTHER_MOMENT_DATE));
    }
}

package dev.jenny.diary.services;

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
import dev.jenny.diary.models.Moment;

/**
 * Unit tests for the {@link DiaryService}.
 */
class DiaryServiceTest {

    private static final String MOMENT_TITLE = "Un día en el parque de atracciones";
    private static final String MOMENT_DESCRIPTION = "Un día genial con amigos";
    private static final Emotion MOMENT_EMOTION = Emotion.ALEGRIA;
    private static final LocalDate MOMENT_DATE = LocalDate.of(2024, 5, 1);

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
        Moment moment = new Moment(MOMENT_TITLE, MOMENT_DESCRIPTION, MOMENT_EMOTION, MOMENT_DATE);
        fakeMomentRepository.save(moment);

        List<MomentDto> moments = diaryService.getAllMoments();

        assertThat(moments, hasSize(1));
        assertThat(moments.get(0).title(), is(equalTo(MOMENT_TITLE)));
    }

    /**
     * Verifies that getMomentsByEmotion returns only the moments
     * tagged with the given emotion.
     */
    @Test
    void testGetMomentsByEmotionReturnsOnlyMatchingMoments() {
        Moment alegriaMoment = new Moment(MOMENT_TITLE, MOMENT_DESCRIPTION, MOMENT_EMOTION, MOMENT_DATE);
        Moment tristezaMoment = new Moment(
                "Un día lluvioso",
                "Me quedé en casa todo el día",
                Emotion.TRISTEZA,
                LocalDate.of(2024, 6, 10));
        fakeMomentRepository.save(alegriaMoment);
        fakeMomentRepository.save(tristezaMoment);

        List<MomentDto> moments = diaryService.getMomentsByEmotion(Emotion.ALEGRIA);

        assertThat(moments, hasSize(1));
        assertThat(moments.get(0).emotion(), is(equalTo(Emotion.ALEGRIA)));
    }
}

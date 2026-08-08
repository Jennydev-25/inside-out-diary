package dev.jenny.diary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mockStatic;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import dev.jenny.diary.controllers.DiaryController;
import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.mocks.FakeMomentCsvDao;
import dev.jenny.diary.mocks.FakeMomentRepository;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.services.DiaryService;
import dev.jenny.diary.views.AccessView;

/**
 * Unit tests for {@link App}.
 */
class AppTest {

    private DiaryController diaryController;

    @BeforeEach
    void setUp() {
        DiaryService diaryService = new DiaryService(new FakeMomentRepository(), new FakeMomentCsvDao());
        diaryController = new DiaryController(diaryService);
    }

    @Test
    void testMainLaunchesAccessView() {
        try (MockedStatic<AccessView> mockedAccessView = mockStatic(AccessView.class)) {
            App.main(new String[0]);

            mockedAccessView.verify(AccessView::printAccessMenu);
        }
    }

    @Test
    void testAddExampleMomentsAddsOneMomentPerEmotion() {
        App.addExampleMoments(diaryController);

        List<MomentDto> moments = diaryController.getAllMoments();
        List<Emotion> emotions = moments.stream().map(MomentDto::emotion).toList();

        assertThat(moments, hasSize(Emotion.values().length));
        assertThat(emotions, containsInAnyOrder(Emotion.values()));
    }
}

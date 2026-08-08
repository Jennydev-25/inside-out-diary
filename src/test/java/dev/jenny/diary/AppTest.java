package dev.jenny.diary;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import dev.jenny.diary.views.AccessView;

/**
 * Unit tests for {@link App}.
 */
class AppTest {

    @Test
    void testMainLaunchesAccessView() {
        try (MockedStatic<AccessView> mockedAccessView = mockStatic(AccessView.class)) {
            App.main(new String[0]);

            mockedAccessView.verify(AccessView::printAccessMenu);
        }
    }
}

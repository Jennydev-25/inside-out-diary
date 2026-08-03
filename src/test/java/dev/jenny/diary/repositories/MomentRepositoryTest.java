package dev.jenny.diary.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jenny.diary.contracts.InterfaceMomentRepository;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.models.Moment;

/**
 * Unit tests for the {@link MomentRepository}.
 */
class MomentRepositoryTest {

    private InterfaceMomentRepository momentRepository;

    @BeforeEach
    void setUp() {
        momentRepository = new MomentRepository();
    }

    /**
     * Verifies that save assigns a sequential id to the moment and
     * stores it, so it becomes retrievable through findAll.
     */
    @Test
    void testSaveAssignsSequentialIdAndStoresMoment() {
        Moment moment = new Moment(
                "Un día en el parque de atracciones",
                "Un día genial con amigos",
                Emotion.ALEGRIA,
                LocalDate.of(2024, 5, 1));

        momentRepository.save(moment);

        assertThat(moment.getId(), is(equalTo(1L)));
        List<Moment> moments = momentRepository.findAll();
        assertThat(moments, hasItem(moment));
    }
}

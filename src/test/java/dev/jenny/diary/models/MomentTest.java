package dev.jenny.diary.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MomentTest {

    private static final String MOMENT_DESCRIPTION = "Fui con mis amigas al parque de atracciones y no paramos de reír en toda la tarde. "
            + "Nos subimos a la montaña rusa cinco veces seguidas, aunque a la tercera ya me temblaban "
            + "las piernas. Volví a casa agotada, pero con una sonrisa que no se me borraba.";

    private Moment moment;

    @BeforeEach
    void setUp() {
        moment = new Moment(
                "Un día en el parque de atracciones",
                MOMENT_DESCRIPTION,
                Emotion.ALEGRIA,
                LocalDate.of(2024, 5, 1));
    }

    @Test
    void testMomentIsCreatedWithAllItsAttributes() {
        assertThat(moment, is(notNullValue()));
        assertThat(moment.getId(), is(nullValue()));
        assertThat(moment.getTitle(), is(equalTo("Un día en el parque de atracciones")));
        assertThat(moment.getDescription(), is(equalTo(MOMENT_DESCRIPTION)));
        assertThat(moment.getEmotion(), is(equalTo(Emotion.ALEGRIA)));
        assertThat(moment.getMomentDate(), is(equalTo(LocalDate.of(2024, 5, 1))));
        assertThat(moment.getCreatedAt(), is(notNullValue()));
        assertThat(moment.getUpdatedAt(), is(equalTo(moment.getCreatedAt())));
    }

    /**
     * Verifies that setEmotion replaces the emotion, refreshes the
     * modification timestamp, and keeps the creation timestamp unchanged.
     * A 1ms sleep guarantees the two LocalDateTime.now() calls (constructor
     * and setEmotion) don't land on the exact same instant.
     */
    @Test
    void testSetEmotionUpdatesEmotionAndRefreshesUpdatedAt() throws InterruptedException {
        LocalDateTime originalCreatedAt = moment.getCreatedAt();
        Thread.sleep(1);

        moment.setEmotion(Emotion.TRISTEZA);

        assertThat(moment.getEmotion(), is(equalTo(Emotion.TRISTEZA)));
        assertThat(moment.getCreatedAt(), is(equalTo(originalCreatedAt)));
        assertTrue(moment.getUpdatedAt().isAfter(originalCreatedAt));
    }

    /**
     * Verifies that setTitle replaces the title, refreshes the
     * modification timestamp, and keeps the creation timestamp unchanged.
     */
    @Test
    void testSetTitleUpdatesTitleAndRefreshesUpdatedAt() throws InterruptedException {
        LocalDateTime originalCreatedAt = moment.getCreatedAt();
        Thread.sleep(1);

        moment.setTitle("Un día en la playa");

        assertThat(moment.getTitle(), is(equalTo("Un día en la playa")));
        assertThat(moment.getCreatedAt(), is(equalTo(originalCreatedAt)));
        assertTrue(moment.getUpdatedAt().isAfter(originalCreatedAt));
    }
}

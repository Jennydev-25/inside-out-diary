package dev.jenny.diary.dtos;

import java.time.LocalDate;

import dev.jenny.diary.models.Emotion;

/**
 * Data Transfer Object for a diary moment. Carries only the data the
 * Controller and View need to show or receive — the internal
 * creation and modification timestamps stay out of this DTO.
 *
 * @param id          the moment's id, null if not persisted yet
 * @param title       the moment's title
 * @param description the moment's description
 * @param emotion     the emotion tagged to this moment
 * @param momentDate  the date the moment occurred
 */
public record MomentDto(Long id, String title, String description, Emotion emotion, LocalDate momentDate) {
}

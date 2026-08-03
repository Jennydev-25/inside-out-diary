package dev.jenny.diary.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A diary moment: a life experience tagged with an emotion and a date.
 * The id stays null until the repository assigns one at persistence time.
 */
public class Moment {

    private Long id;
    private String title;
    private String description;
    private Emotion emotion;
    private LocalDate momentDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Creates a new, not-yet-persisted Moment. Its creation and
     * modification timestamps are set automatically to the current instant.
     *
     * @param title       the moment's title
     * @param description the moment's description
     * @param emotion     the emotion tagged to this moment
     * @param momentDate  the date the moment occurred
     */
    public Moment(String title, String description, Emotion emotion, LocalDate momentDate) {
        this.title = title;
        this.description = description;
        this.emotion = emotion;
        this.momentDate = momentDate;
        setTimestamps();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public LocalDate getMomentDate() {
        return momentDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private void setTimestamps() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }
}

package dev.jenny.diary.controllers;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.services.DiaryService;

/**
 * Receives requests, coordinates the flow with the Service and returns
 * DTOs. Contains no business logic.
 */
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    /**
     * Adds a new moment.
     *
     * @param momentDto the moment data received from the View
     * @return the saved moment as a DTO, including its assigned id
     */
    public MomentDto addMoment(MomentDto momentDto) {
        return diaryService.addMoment(momentDto);
    }

    /**
     * Returns every stored moment.
     *
     * @return a list with all the moments, as DTOs
     */
    public List<MomentDto> getAllMoments() {
        return diaryService.getAllMoments();
    }

    /**
     * Deletes the moment with the given id.
     *
     * @param id the id of the moment to delete
     */
    public void deleteMoment(Long id) {
        diaryService.deleteMoment(id);
    }

    /**
     * Returns only the moments tagged with the given emotion.
     *
     * @param emotion the emotion to filter by
     * @return a list with the matching moments, as DTOs
     */
    public List<MomentDto> getMomentsByEmotion(Emotion emotion) {
        return diaryService.getMomentsByEmotion(emotion);
    }

    /**
     * Returns only the moments that occurred in the given year and month.
     *
     * @param yearMonth the year and month to filter by
     * @return a list with the matching moments, as DTOs
     */
    public List<MomentDto> getMomentsByMonth(YearMonth yearMonth) {
        return diaryService.getMomentsByMonth(yearMonth);
    }

    /**
     * Returns only the moments that occurred on the exact given date.
     *
     * @param date the date to filter by
     * @return a list with the matching moments, as DTOs
     */
    public List<MomentDto> getMomentsByDate(LocalDate date) {
        return diaryService.getMomentsByDate(date);
    }

    /**
     * Updates the emotion of an existing moment.
     *
     * @param id      the id of the moment to update
     * @param emotion the new emotion
     * @return the updated moment as a DTO
     */
    public MomentDto updateMomentEmotion(Long id, Emotion emotion) {
        return diaryService.updateMomentEmotion(id, emotion);
    }

    /**
     * Updates the title of an existing moment.
     *
     * @param id    the id of the moment to update
     * @param title the new title
     * @return the updated moment as a DTO
     */
    public MomentDto updateMomentTitle(Long id, String title) {
        return diaryService.updateMomentTitle(id, title);
    }

    /**
     * Updates the description of an existing moment.
     *
     * @param id          the id of the moment to update
     * @param description the new description
     * @return the updated moment as a DTO
     */
    public MomentDto updateMomentDescription(Long id, String description) {
        return diaryService.updateMomentDescription(id, description);
    }

    /**
     * Updates the date of an existing moment.
     *
     * @param id         the id of the moment to update
     * @param momentDate the new date
     * @return the updated moment as a DTO
     */
    public MomentDto updateMomentDate(Long id, LocalDate momentDate) {
        return diaryService.updateMomentDate(id, momentDate);
    }

    /**
     * Exports every stored moment to a CSV file.
     */
    public void exportMoments() {
        diaryService.exportMoments();
    }
}

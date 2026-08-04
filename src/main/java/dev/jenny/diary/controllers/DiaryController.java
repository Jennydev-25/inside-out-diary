package dev.jenny.diary.controllers;

import java.util.List;

import dev.jenny.diary.dtos.MomentDto;
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
}

package dev.jenny.diary.services;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import dev.jenny.diary.contracts.InterfaceMomentRepository;
import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.mappers.MomentMapper;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.models.Moment;

/**
 * Applies the diary's business rules: adding, listing, filtering and
 * removing moments. Works with the domain Model internally, and only
 * exposes DTOs to the Controller.
 */
public class DiaryService {

    private final InterfaceMomentRepository momentRepository;
    private final MomentMapper momentMapper;

    public DiaryService(InterfaceMomentRepository momentRepository) {
        this.momentRepository = momentRepository;
        this.momentMapper = new MomentMapper();
    }

    /**
     * Adds a new moment: converts the DTO to a Model, saves it through
     * the repository, and returns it as a DTO with the assigned id.
     *
     * @param momentDto the moment data received from the Controller
     * @return the saved moment as a DTO, including its assigned id
     */
    public MomentDto addMoment(MomentDto momentDto) {
        Moment moment = momentMapper.toModel(momentDto);
        momentRepository.save(moment);
        return momentMapper.toDto(moment);
    }

    /**
     * Returns every stored moment as a DTO.
     *
     * @return a list with all the moments, converted to DTOs
     */
    public List<MomentDto> getAllMoments() {
        return toDtos(momentRepository.findAll());
    }

    /**
     * Deletes the moment with the given id.
     *
     * @param id the id of the moment to delete
     * @throws IllegalArgumentException if no moment exists with the given id
     */
    public void deleteMoment(Long id) {
        if (momentRepository.findById(id) == null) {
            throw new IllegalArgumentException("No existe ningún momento con id " + id);
        }
        momentRepository.deleteById(id);
    }

    /**
     * Returns only the moments tagged with the given emotion.
     *
     * @param emotion the emotion to filter by
     * @return a list with the matching moments, converted to DTOs
     */
    public List<MomentDto> getMomentsByEmotion(Emotion emotion) {
        List<Moment> matchingMoments = new ArrayList<>();
        for (Moment moment : momentRepository.findAll()) {
            if (moment.getEmotion() == emotion) {
                matchingMoments.add(moment);
            }
        }
        return toDtos(matchingMoments);
    }

    /**
     * Returns only the moments that occurred in the given year and month.
     *
     * @param yearMonth the year and month to filter by
     * @return a list with the matching moments, converted to DTOs
     */
    public List<MomentDto> getMomentsByMonth(YearMonth yearMonth) {
        List<Moment> matchingMoments = new ArrayList<>();
        for (Moment moment : momentRepository.findAll()) {
            if (YearMonth.from(moment.getMomentDate()).equals(yearMonth)) {
                matchingMoments.add(moment);
            }
        }
        return toDtos(matchingMoments);
    }

    /**
     * Returns only the moments that occurred on the exact given date.
     *
     * @param date the date to filter by
     * @return a list with the matching moments, converted to DTOs
     */
    public List<MomentDto> getMomentsByDate(LocalDate date) {
        List<Moment> matchingMoments = new ArrayList<>();
        for (Moment moment : momentRepository.findAll()) {
            if (moment.getMomentDate().equals(date)) {
                matchingMoments.add(moment);
            }
        }
        return toDtos(matchingMoments);
    }

    /**
     * Updates the emotion of an existing moment.
     *
     * @param id      the id of the moment to update
     * @param emotion the new emotion
     * @return the updated moment as a DTO
     * @throws IllegalArgumentException if no moment exists with the given id
     */
    public MomentDto updateMomentEmotion(Long id, Emotion emotion) {
        Moment moment = momentRepository.findById(id);
        if (moment == null) {
            throw new IllegalArgumentException("No existe ningún momento con id " + id);
        }
        moment.setEmotion(emotion);
        return momentMapper.toDto(moment);
    }

    /**
     * Updates the title of an existing moment.
     *
     * @param id    the id of the moment to update
     * @param title the new title
     * @return the updated moment as a DTO
     * @throws IllegalArgumentException if no moment exists with the given id
     */
    public MomentDto updateMomentTitle(Long id, String title) {
        Moment moment = momentRepository.findById(id);
        if (moment == null) {
            throw new IllegalArgumentException("No existe ningún momento con id " + id);
        }
        moment.setTitle(title);
        return momentMapper.toDto(moment);
    }

    /**
     * Converts a list of Moments into a list of MomentDtos. Shared by
     * every method that returns moments, to avoid repeating the same
     * conversion loop.
     *
     * @param moments the moments to convert
     * @return the same moments, converted to DTOs
     */
    private List<MomentDto> toDtos(List<Moment> moments) {
        List<MomentDto> momentDtos = new ArrayList<>();
        for (Moment moment : moments) {
            momentDtos.add(momentMapper.toDto(moment));
        }
        return momentDtos;
    }
}

package dev.jenny.diary.services;

import dev.jenny.diary.contracts.InterfaceMomentRepository;
import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.mappers.MomentMapper;
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
}

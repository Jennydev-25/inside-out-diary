package dev.jenny.diary.mappers;

import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.models.Moment;

/**
 * Converts between the {@link Moment} domain model and its
 * {@link MomentDto}. Holds no business logic, only the transformation.
 */
public class MomentMapper {

    /**
     * Converts a Moment into a MomentDto, leaving out the internal
     * creation and modification timestamps.
     *
     * @param moment the domain model to convert
     * @return a MomentDto with the moment's visible data
     */
    public MomentDto toDto(Moment moment) {
        return new MomentDto(
                moment.getId(),
                moment.getTitle(),
                moment.getDescription(),
                moment.getEmotion(),
                moment.getMomentDate());
    }

    /**
     * Converts a MomentDto into a new Moment. The resulting Moment gets
     * fresh creation and modification timestamps, since the DTO does not
     * carry the original ones.
     *
     * @param dto the DTO to convert
     * @return a new Moment built from the DTO's visible data
     */
    public Moment toModel(MomentDto dto) {
        return new Moment(dto.title(), dto.description(), dto.emotion(), dto.momentDate());
    }
}

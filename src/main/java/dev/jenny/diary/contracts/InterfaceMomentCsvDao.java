package dev.jenny.diary.contracts;

import java.util.List;

import dev.jenny.diary.dtos.MomentDto;

/**
 * Contract for exporting {@link MomentDto} instances to a CSV file,
 * hiding how they are actually written from the rest of the app.
 */
public interface InterfaceMomentCsvDao {

    /**
     * Writes the given moments to a CSV file.
     *
     * @param moments the moments to export
     */
    void write(List<MomentDto> moments);
}

package dev.jenny.diary.contracts;

import java.util.List;

import dev.jenny.diary.models.Moment;

/**
 * Contract for storing and retrieving {@link Moment} instances,
 * hiding how they are actually persisted from the rest of the app.
 */
public interface InterfaceMomentRepository {

    /**
     * Saves a moment, assigning it a sequential id.
     *
     * @param moment the moment to save
     */
    void save(Moment moment);

    /**
     * Returns every stored moment.
     *
     * @return a list with all the saved moments
     */
    List<Moment> findAll();
}

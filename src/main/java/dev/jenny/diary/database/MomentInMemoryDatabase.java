package dev.jenny.diary.database;

import java.util.HashMap;
import java.util.Map;

import dev.jenny.diary.models.Moment;

/**
 * In-memory storage for Moment instances. Holds the actual Map and
 * assigns each moment a sequential id, the same way a real database
 * would auto-increment a primary key.
 */
public class MomentInMemoryDatabase {

    private final Map<Long, Moment> moments = new HashMap<>();

    /**
     * Stores a moment, assigning it a sequential id.
     *
     * @param moment the moment to store
     */
    public void store(Moment moment) {
        long id = moments.size() + 1L;
        moment.setId(id);
        moments.put(id, moment);
    }

    /**
     * Returns every stored moment.
     *
     * @return a Map with all the stored moments, keyed by id
     */
    public Map<Long, Moment> findAll() {
        return moments;
    }
}

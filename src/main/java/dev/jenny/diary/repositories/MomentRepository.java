package dev.jenny.diary.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.jenny.diary.contracts.InterfaceMomentRepository;
import dev.jenny.diary.models.Moment;

/**
 * In-memory implementation of {@link InterfaceMomentRepository}.
 * Stores moments in a Map, assigning each one a sequential id.
 */
public class MomentRepository implements InterfaceMomentRepository {

    private final Map<Long, Moment> moments = new HashMap<>();

    @Override
    public void save(Moment moment) {
        long id = moments.size() + 1L;
        moment.setId(id);
        moments.put(id, moment);
    }

    @Override
    public List<Moment> findAll() {
        return new ArrayList<>(moments.values());
    }
}

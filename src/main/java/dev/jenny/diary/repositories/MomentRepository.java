package dev.jenny.diary.repositories;

import java.util.ArrayList;
import java.util.List;

import dev.jenny.diary.contracts.InterfaceMomentRepository;
import dev.jenny.diary.database.MomentInMemoryDatabase;
import dev.jenny.diary.models.Moment;

/**
 * In-memory implementation of {@link InterfaceMomentRepository}.
 * Delegates the actual storage to a {@link MomentInMemoryDatabase}.
 */
public class MomentRepository implements InterfaceMomentRepository {

    private final MomentInMemoryDatabase database;

    public MomentRepository() {
        this.database = new MomentInMemoryDatabase();
    }

    @Override
    public void save(Moment moment) {
        database.store(moment);
    }

    @Override
    public List<Moment> findAll() {
        return new ArrayList<>(database.findAll().values());
    }

    @Override
    public Moment findById(Long id) {
        return database.findAll().get(id);
    }
}

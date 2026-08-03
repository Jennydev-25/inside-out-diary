package dev.jenny.diary.mocks;

import java.util.ArrayList;
import java.util.List;

import dev.jenny.diary.contracts.InterfaceMomentRepository;
import dev.jenny.diary.models.Moment;

/**
 * Manual fake for {@link InterfaceMomentRepository}, used to unit test
 * DiaryService in isolation, without depending on the real in-memory
 * database or Mockito.
 */
public class FakeMomentRepository implements InterfaceMomentRepository {

    private final List<Moment> moments = new ArrayList<>();

    @Override
    public void save(Moment moment) {
        long id = moments.size() + 1L;
        moment.setId(id);
        moments.add(moment);
    }

    @Override
    public List<Moment> findAll() {
        return moments;
    }

    @Override
    public Moment findById(Long id) {
        return moments.stream()
                .filter(moment -> moment.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        moments.removeIf(moment -> moment.getId().equals(id));
    }
}

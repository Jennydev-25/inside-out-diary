package dev.jenny.diary.mocks;

import java.util.List;

import dev.jenny.diary.contracts.InterfaceMomentCsvDao;
import dev.jenny.diary.dtos.MomentDto;

/**
 * Manual fake for {@link InterfaceMomentCsvDao}, used to unit test
 * DiaryService in isolation, without writing to a real file.
 */
public class FakeMomentCsvDao implements InterfaceMomentCsvDao {

    private List<MomentDto> writtenMoments;

    @Override
    public void write(List<MomentDto> moments) {
        writtenMoments = moments;
    }

    public List<MomentDto> getWrittenMoments() {
        return writtenMoments;
    }
}

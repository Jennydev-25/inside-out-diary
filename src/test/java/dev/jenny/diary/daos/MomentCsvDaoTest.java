package dev.jenny.diary.daos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.models.Emotion;

/**
 * Unit tests for the {@link MomentCsvDao}.
 */
class MomentCsvDaoTest {

    private Path tempFile;
    private MomentCsvDao dao;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("momentos-test", ".csv");
        dao = new MomentCsvDao(tempFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    /**
     * Verifies that write produces a CSV file with a header row and
     * one data row matching the given moment's fields.
     */
    @Test
    void testWriteCreatesFileWithHeaderAndOneMomentRow() throws IOException {
        MomentDto moment = new MomentDto(1L, "Primer dia", "Empece el bootcamp", Emotion.ALEGRIA,
                LocalDate.of(2026, 7, 1));

        dao.write(List.of(moment));

        List<String> lines = Files.readAllLines(tempFile);
        assertThat(lines.get(0), is("id,title,description,emotion,momentDate"));
        assertThat(lines.get(1), is("1,Primer dia,Empece el bootcamp,Alegría,2026-07-01"));
    }

    /**
     * Verifies that a field containing a comma gets wrapped in double
     * quotes, so the comma isn't mistaken for the column separator
     * (RFC 4180 escaping rule).
     */
    @Test
    void testWriteWrapsFieldContainingCommaInQuotes() throws IOException {
        MomentDto moment = new MomentDto(1L, "Café, libro y manta", "Una tarde tranquila", Emotion.ALEGRIA,
                LocalDate.of(2026, 7, 1));

        dao.write(List.of(moment));

        List<String> lines = Files.readAllLines(tempFile);
        assertThat(lines.get(1), is("1,\"Café, libro y manta\",Una tarde tranquila,Alegría,2026-07-01"));
    }
}

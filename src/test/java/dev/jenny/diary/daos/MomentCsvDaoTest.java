package dev.jenny.diary.daos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
        MomentDto moment = new MomentDto(1L, "Un día en el parque de atracciones",
                "Fui con mis amigas al parque de atracciones y me lo pasé genial subiéndome a la montaña rusa",
                Emotion.ALEGRIA, LocalDate.of(2026, 7, 1));

        dao.write(List.of(moment));

        List<String> lines = Files.readAllLines(tempFile);
        assertThat(lines.get(0), is("id,title,description,emotion,momentDate"));
        assertThat(lines.get(1), is(
                "1,Un día en el parque de atracciones,Fui con mis amigas al parque de atracciones y me lo pasé genial subiéndome a la montaña rusa,Alegría,2026-07-01"));
    }

    /**
     * Verifies that a description containing a comma, a double quote,
     * a line break, or a combination of these gets wrapped in double
     * quotes (doubling any internal quote), following the CSV
     * escaping rule from RFC 4180.
     */
    @ParameterizedTest(name = "{2}: field gets escaped correctly")
    @MethodSource("fieldsNeedingEscaping")
    void testWriteEscapesFieldsThatNeedIt(String rawDescription, String expectedRow, String reason) throws IOException {
        MomentDto moment = new MomentDto(1L, "Un día especial", rawDescription, Emotion.ALEGRIA,
                LocalDate.of(2026, 7, 1));

        dao.write(List.of(moment));

        String content = Files.readString(tempFile);
        assertThat(content, containsString(expectedRow));
    }

    private static Stream<Arguments> fieldsNeedingEscaping() {
        return Stream.of(
                Arguments.of(
                        "Quedé con mis amigas en la cafetería, y no paramos de hablar en toda la tarde",
                        "1,Un día especial,\"Quedé con mis amigas en la cafetería, y no paramos de hablar en toda la tarde\",Alegría,2026-07-01",
                        "comma"),
                Arguments.of(
                        "Mi madre me dijo \"estoy muy orgullosa de ti\" y se me saltaron las lágrimas",
                        "1,Un día especial,\"Mi madre me dijo \"\"estoy muy orgullosa de ti\"\" y se me saltaron las lágrimas\",Alegría,2026-07-01",
                        "double quote"),
                Arguments.of(
                        "Por la mañana fui a correr al parque.\nPor la tarde quedé con mis amigas para tomar algo",
                        "1,Un día especial,\"Por la mañana fui a correr al parque.\nPor la tarde quedé con mis amigas para tomar algo\",Alegría,2026-07-01",
                        "line break"),
                Arguments.of(
                        "Cuando llegué a casa, mi hermana me dijo \"qué tarde vienes\" y nos reímos un rato",
                        "1,Un día especial,\"Cuando llegué a casa, mi hermana me dijo \"\"qué tarde vienes\"\" y nos reímos un rato\",Alegría,2026-07-01",
                        "comma and double quote combined"));
    }
}

package dev.jenny.diary.daos;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import dev.jenny.diary.contracts.InterfaceMomentCsvDao;
import dev.jenny.diary.dtos.MomentDto;

/**
 * Writes {@link MomentDto} instances to a CSV file. The only class
 * that touches {@link Files} directly — the rest of the app only
 * knows it can ask for moments to be exported.
 */
public class MomentCsvDao implements InterfaceMomentCsvDao {

    private static final String HEADER = "id,title,description,emotion,momentDate";

    private final Path path;

    public MomentCsvDao(Path path) {
        this.path = path;
    }

    @Override
    public void write(List<MomentDto> moments) {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (MomentDto moment : moments) {
            lines.add(toCsvLine(moment));
        }
        try {
            Files.write(path, lines);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Converts a single moment into its CSV row, escaping its text
     * fields where needed.
     *
     * @param moment the moment to convert
     * @return the moment's data as a comma-separated line
     */
    private String toCsvLine(MomentDto moment) {
        return String.format("%d,%s,%s,%s,%s",
                moment.id(),
                escapeField(moment.title()),
                escapeField(moment.description()),
                moment.emotion().getDisplayName(),
                moment.momentDate());
    }

    /**
     * Wraps a field in double quotes if it contains a comma or a
     * double quote, doubling any internal quote, following the CSV
     * escaping rule from RFC 4180.
     *
     * @param field the raw field value
     * @return the field, escaped if it needs quoting
     */
    private String escapeField(String field) {
        if (field.contains(",") || field.contains("\"")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}

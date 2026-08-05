package dev.jenny.diary.singletons;

import java.nio.file.Path;

import dev.jenny.diary.controllers.DiaryController;
import dev.jenny.diary.daos.MomentCsvDao;
import dev.jenny.diary.repositories.MomentRepository;
import dev.jenny.diary.services.DiaryService;

/**
 * Lazy singleton providing the single {@link DiaryController} instance
 * used by the console app. Unlike {@link AccessServiceSingleton}
 * (eager), this one is only needed once the user is past the password
 * gate and picks a menu option, so it's created on demand.
 */
public class DiaryControllerSingleton {

    private static final String CSV_PATH = "my-moments.csv";

    private static DiaryController INSTANCE;

    private DiaryControllerSingleton() {
    }

    /**
     * Returns the single {@link DiaryController} instance, creating it
     * the first time it's requested (lazy initialization).
     *
     * @return the shared DiaryController instance
     */
    public static DiaryController getInstance() {
        if (INSTANCE == null) {
            DiaryService diaryService = new DiaryService(
                    new MomentRepository(),
                    new MomentCsvDao(Path.of(CSV_PATH)));
            INSTANCE = new DiaryController(diaryService);
        }
        return INSTANCE;
    }

}

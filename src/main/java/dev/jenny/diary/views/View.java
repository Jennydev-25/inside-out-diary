package dev.jenny.diary.views;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import dev.jenny.diary.models.Emotion;

/**
 * Base class for every console View, holding the elements shared
 * across all of them: the single input Scanner, the date format used
 * throughout the diary, and the emotion menu printed wherever a
 * moment's emotion is requested.
 */
public abstract class View {

    protected static Scanner SCANNER = new Scanner(System.in);

    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Prints the numbered list of every available emotion. */
    protected static void printEmotionOptions() {
        int option = 1;
        for (Emotion emotion : Emotion.values()) {
            System.out.println(option + ". " + emotion.getDisplayName());
            option++;
        }
    }

}

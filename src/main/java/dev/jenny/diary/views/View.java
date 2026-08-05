package dev.jenny.diary.views;

import java.util.Scanner;

/**
 * Base class for every console View, holding the single {@link Scanner}
 * shared across all of them so user input is read from one place.
 */
public abstract class View {

    protected static Scanner SCANNER = new Scanner(System.in);

}

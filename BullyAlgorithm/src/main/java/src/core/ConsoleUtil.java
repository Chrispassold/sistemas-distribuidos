package src.core;

public class ConsoleUtil {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void printGreen(String format, Object... args) {
        print(ANSI_GREEN + format + ANSI_RESET, args);
    }

    public static void printBlue(String format, Object... args) {
        print(ANSI_BLUE + format + ANSI_RESET, args);
    }

    public static void printRed(String format, Object... args) {
        print(ANSI_RED + format + ANSI_RESET, args);
    }

    public static void printPurple(String format, Object... args) {
        print(ANSI_PURPLE + format + ANSI_RESET, args);
    }

    public static void print(String format, Object... args) {
        System.out.printf(format, args);
        System.out.println();
    }
}

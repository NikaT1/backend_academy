package backend.academy.algo.maze;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum MazeGeneratorType {
    PRIM, RECURSIVE_BACKTRACKER;

    private static final String LIST_TO_PRINT;

    static {
        LIST_TO_PRINT = IntStream.range(0, MazeGeneratorType.values().length)
            .mapToObj(i -> (i + 1) + " - " + MazeGeneratorType.values()[i])
            .collect(
                Collectors.joining(System.lineSeparator(), "", System.lineSeparator())
            );
    }

    public static String getListToPrint() {
        return LIST_TO_PRINT;
    }
}

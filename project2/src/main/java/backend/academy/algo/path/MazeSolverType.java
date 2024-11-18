package backend.academy.algo.path;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum MazeSolverType {
    DFS, A_STAR;

    private static final String LIST_TO_PRINT;

    static {
        LIST_TO_PRINT = IntStream.range(0, MazeSolverType.values().length)
            .mapToObj(i -> (i + 1) + " - " + MazeSolverType.values()[i])
            .collect(
                Collectors.joining(System.lineSeparator(), "", System.lineSeparator())
            );
    }

    public static String getListToPrint() {
        return LIST_TO_PRINT;
    }
}

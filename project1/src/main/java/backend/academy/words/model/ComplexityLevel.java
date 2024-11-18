package backend.academy.words.model;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ComplexityLevel {
    ELEMENTARY(10, 3, 6),
    MEDIUM(13, 5, 10),
    ADVANCED(16, 6, 14);

    private static final String LIST_TO_PRINT;
    private final int maxStepCount;
    private final int minLength;
    private final int maxLength;

    static {
        LIST_TO_PRINT = IntStream.range(0, ComplexityLevel.values().length)
            .mapToObj(i -> (i + 1) + " - " + ComplexityLevel.values()[i])
            .collect(
                Collectors.joining(System.lineSeparator(), "", System.lineSeparator())
            );
    }

    public static String getListToPrint() {
        return LIST_TO_PRINT;
    }
}

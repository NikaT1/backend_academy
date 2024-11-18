package backend.academy.maze;

import backend.academy.graph.model.Coordinate;
import java.util.EnumSet;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Cell {
    private final Coordinate coordinate;
    private final Set<Type> type = EnumSet.noneOf(Type.class);

    public enum Type { WALL, FLOOR }
}

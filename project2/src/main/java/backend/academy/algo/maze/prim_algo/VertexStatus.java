package backend.academy.algo.maze.prim_algo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VertexStatus {
    private Type type;
    private final int weight;

    public enum Type { NOT_STARTED, IN_PROGRESS, READY }
}

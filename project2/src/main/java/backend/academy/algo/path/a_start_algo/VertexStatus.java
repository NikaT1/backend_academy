package backend.academy.algo.path.a_start_algo;

import backend.academy.graph.model.Vertex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VertexStatus {
    private boolean isVisited;
    private int startDistance = Integer.MAX_VALUE;
    private int fullDistance = Integer.MAX_VALUE;
    private Vertex<VertexStatus> previousCoordinate;
}

package backend.academy.algo.path;

import backend.academy.algo.BaseAlgorithm;
import backend.academy.graph.model.Coordinate;
import backend.academy.graph.model.Vertex;

public abstract class BaseSolverAlgorithm<T> extends BaseAlgorithm<T> {
    protected Vertex<T> getVertexByCoordinate(Coordinate coordinate) {
        return vertices.get(coordinate);
    }
}

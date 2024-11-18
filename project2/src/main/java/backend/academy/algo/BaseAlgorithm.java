package backend.academy.algo;

import backend.academy.graph.model.Coordinate;
import backend.academy.graph.model.Graph;
import backend.academy.graph.model.Vertex;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressFBWarnings("CLI_CONSTANT_LIST_INDEX")
public abstract class BaseAlgorithm<T> {
    private final static int[][] COORDINATE_DIFFS = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    protected Map<Coordinate, Vertex<T>> vertices;

    protected void prepareVerticesMap(Graph graph, T initStatus) {
        prepareVerticesMap(graph, (c) -> new Vertex<>(initStatus, c));
    }

    protected void prepareVerticesMap(Graph graph, Function<Coordinate, Vertex<T>> initStatusFunc) {
        vertices = graph.getCoordinates()
            .stream()
            .collect(Collectors.toMap(Function.identity(), initStatusFunc));
    }

    protected Stream<Vertex<T>> getNearbyVerticesStream(Vertex<T> vertex) {
        return Arrays.stream(COORDINATE_DIFFS)
            .map(d -> new Coordinate(vertex.id().x() + d[0], vertex.id().y() + d[1]))
            .map(c -> vertices.get(c))
            .filter(Objects::nonNull);
    }

    protected List<Vertex<T>> getNearbyVertices(Vertex<T> vertex) {
        return getNearbyVerticesStream(vertex)
            .collect(Collectors.toList());
    }

    protected List<Vertex<T>> getNearbyVerticesByStatus(Vertex<T> vertex, Predicate<Vertex<T>> filterFunc) {
        return getNearbyVerticesStream(vertex)
            .filter(filterFunc)
            .collect(Collectors.toList());
    }

    protected List<Vertex<T>> getNearbyVerticesByStatus(Vertex<T> vertex, T status) {
        return getNearbyVerticesByStatus(vertex, v -> v.status() == status);
    }
}

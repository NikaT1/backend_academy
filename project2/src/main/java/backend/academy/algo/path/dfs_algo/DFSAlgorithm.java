package backend.academy.algo.path.dfs_algo;

import backend.academy.algo.path.BaseSolverAlgorithm;
import backend.academy.algo.path.MazeSolver;
import backend.academy.graph.model.Coordinate;
import backend.academy.graph.model.Graph;
import backend.academy.graph.model.Vertex;
import backend.academy.maze.Maze;
import java.util.ArrayList;
import java.util.List;

public class DFSAlgorithm extends BaseSolverAlgorithm<VertexStatus> implements MazeSolver {
    public List<Coordinate> solveMaze(Maze maze, Coordinate start, Coordinate finish) {
        prepareVerticesMap(maze.graph(), VertexStatus.NOT_VISITED);
        return findPathByRecursion(maze.graph(), getVertexByCoordinate(start), getVertexByCoordinate(finish));
    }

    private List<Coordinate> findPathByRecursion(
        Graph graph,
        Vertex<VertexStatus> current,
        Vertex<VertexStatus> finish
    ) {
        current.status(VertexStatus.VISITED);
        if (current.equals(finish)) {
            return new ArrayList<>(List.of(current.id()));
        }

        List<Vertex<VertexStatus>> nearbyNotVisitedVertices =
            getNearbyVerticesByStatus(current, VertexStatus.NOT_VISITED);

        for (Vertex<VertexStatus> vertex : nearbyNotVisitedVertices) {
            if (graph.hasEdge(current.id(), vertex.id())) {
                List<Coordinate> path = findPathByRecursion(graph, vertex, finish);
                if (!path.isEmpty()) {
                    path.add(current.id());
                    return path;
                }
            }
        }
        return new ArrayList<>();
    }
}

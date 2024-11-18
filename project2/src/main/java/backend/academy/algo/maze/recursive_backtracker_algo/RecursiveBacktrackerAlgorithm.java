package backend.academy.algo.maze.recursive_backtracker_algo;

import backend.academy.algo.maze.BaseGeneratorAlgorithm;
import backend.academy.algo.maze.MazeGenerator;
import backend.academy.graph.GraphGenerator;
import backend.academy.graph.model.Graph;
import backend.academy.graph.model.Vertex;
import backend.academy.maze.Maze;
import java.util.List;
import static backend.academy.random.RandomUtil.pullRandomFromList;

public class RecursiveBacktrackerAlgorithm extends BaseGeneratorAlgorithm<VertexStatus>
    implements MazeGenerator {
    public Maze createMaze(int width, int height) {
        Graph graph = GraphGenerator.getGraphWithoutEdges(width, height);
        prepareVerticesMap(graph, VertexStatus.NOT_STARTED);
        Vertex<VertexStatus> firstVertex = getStartVertex(width, height, VertexStatus.READY);
        createMazeByRecursion(graph, firstVertex);
        return new Maze(width, height).initMaze(graph);
    }

    private void createMazeByRecursion(Graph graph, Vertex<VertexStatus> vertex) {
        vertex.status(VertexStatus.READY);

        List<Vertex<VertexStatus>> nearbyNotStartedVertices =
            getNearbyVerticesByStatus(vertex, VertexStatus.NOT_STARTED);

        while (!nearbyNotStartedVertices.isEmpty()) {
            Vertex<VertexStatus> randomVertex = pullRandomFromList(nearbyNotStartedVertices).orElseThrow();
            if (randomVertex.status() == VertexStatus.NOT_STARTED) {
                graph.addEdge(vertex.id(), randomVertex.id());
                createMazeByRecursion(graph, randomVertex);
            }
        }
    }
}

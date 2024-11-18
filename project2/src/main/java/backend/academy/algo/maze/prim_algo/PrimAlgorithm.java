package backend.academy.algo.maze.prim_algo;

import backend.academy.algo.maze.BaseGeneratorAlgorithm;
import backend.academy.algo.maze.MazeGenerator;
import backend.academy.graph.GraphGenerator;
import backend.academy.graph.model.Graph;
import backend.academy.graph.model.Vertex;
import backend.academy.maze.Maze;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import static backend.academy.algo.maze.prim_algo.VertexStatus.Type.IN_PROGRESS;
import static backend.academy.algo.maze.prim_algo.VertexStatus.Type.NOT_STARTED;
import static backend.academy.algo.maze.prim_algo.VertexStatus.Type.READY;
import static backend.academy.random.RandomUtil.getRandomNumber;
import static backend.academy.random.RandomUtil.pullRandomFromList;

public class PrimAlgorithm extends BaseGeneratorAlgorithm<VertexStatus>
    implements MazeGenerator {

    public Maze createMaze(int width, int height) {
        Graph graph = GraphGenerator.getGraphWithoutEdges(width, height);
        prepareVerticesMap(graph,
            c -> new Vertex<>(new VertexStatus(NOT_STARTED, getRandomNumber()), c));
        Vertex<VertexStatus> startVertex = getStartVertex(width, height);
        startVertex.status().type(READY);

        Queue<Vertex<VertexStatus>> remainingVertices = prepareRemainingVertices(startVertex);

        while (!remainingVertices.isEmpty()) {
            Vertex<VertexStatus> randomVertex = remainingVertices.poll();
            randomVertex.status().type(READY);

            prepareNearbyVertices(randomVertex, remainingVertices);
            List<Vertex<VertexStatus>> nearbyReadyVertices =
                getNearbyVerticesByStatus(randomVertex, v -> v.status().type() == READY);

            pullRandomFromList(nearbyReadyVertices)
                .ifPresent(v -> graph.addEdge(randomVertex.id(), v.id()));
        }
        return new Maze(width, height).initMaze(graph);
    }

    private Queue<Vertex<VertexStatus>> prepareRemainingVertices(Vertex<VertexStatus> startVertex) {
        return getNearbyVerticesStream(startVertex)
            .peek(nearby -> nearby.status().type(IN_PROGRESS))
            .collect(
                Collectors.toCollection(() -> new PriorityQueue<>(Comparator.comparingInt(v -> v.status().weight()))));
    }

    private void prepareNearbyVertices(
        Vertex<VertexStatus> randomVertex,
        Queue<Vertex<VertexStatus>> remainingVertices
    ) {
        getNearbyVerticesStream(randomVertex)
            .filter(v -> v.status().type() == NOT_STARTED)
            .forEach(v -> {
                v.status().type(IN_PROGRESS);
                remainingVertices.add(v);
            });
    }
}

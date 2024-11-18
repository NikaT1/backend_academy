package backend.academy.algo.path.a_start_algo;

import backend.academy.algo.path.BaseSolverAlgorithm;
import backend.academy.algo.path.MazeSolver;
import backend.academy.graph.model.Coordinate;
import backend.academy.graph.model.Graph;
import backend.academy.graph.model.Vertex;
import backend.academy.maze.Maze;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class AStarAlgorithm extends BaseSolverAlgorithm<VertexStatus> implements MazeSolver {
    private PriorityQueue<Vertex<VertexStatus>> queue;

    public List<Coordinate> solveMaze(Maze maze, Coordinate start, Coordinate finish) {
        prepareVerticesMap(maze.graph(), (c) -> new Vertex<>(new VertexStatus(), c));
        prepareVerticesQueue();
        Vertex<VertexStatus> startVertex = getVertexByCoordinate(start);
        startVertex.status().startDistance(0);
        queue.add(startVertex);
        while (!queue.isEmpty()) {
            Vertex<VertexStatus> currentVertex = queue.poll();
            if (currentVertex.id().equals(finish)) {
                break;
            }
            currentVertex.status().isVisited(true);
            processNearbyVertices(currentVertex, finish, maze.graph());
        }
        Vertex<VertexStatus> finishVertex = getVertexByCoordinate(finish);
        return getResultPath(finishVertex);
    }

    private List<Coordinate> getResultPath(Vertex<VertexStatus> finishVertex) {
        List<Coordinate> resultPath = new LinkedList<>();
        if (finishVertex.status().previousCoordinate() == null) {
            return resultPath;
        }
        Vertex<VertexStatus> currentVertex = finishVertex;
        resultPath.add(finishVertex.id());
        while (currentVertex.status().previousCoordinate() != null) {
            currentVertex = currentVertex.status().previousCoordinate();
            resultPath.add(currentVertex.id());
        }
        return resultPath;
    }

    private void processNearbyVertices(Vertex<VertexStatus> currentVertex, Coordinate finish, Graph graph) {
        List<Vertex<VertexStatus>> nearbyVertices = getNearbyVertices(currentVertex);
        for (Vertex<VertexStatus> vertex : nearbyVertices) {
            if (vertex.status().isVisited() || !graph.hasEdge(currentVertex.id(), vertex.id())) {
                continue;
            }
            int newStartDistance = currentVertex.status().startDistance() + 1;
            queue.remove(vertex);
            if (newStartDistance < vertex.status().startDistance()) {
                vertex.status().startDistance(newStartDistance);
                vertex.status().fullDistance(calcManhattanDistance(vertex.id(), finish));
                vertex.status().previousCoordinate(currentVertex);
            }
            queue.add(vertex);
        }
    }

    private void prepareVerticesQueue() {
        queue = new PriorityQueue<>(Comparator.comparingInt((v) -> v.status().fullDistance()));
    }

    private int calcManhattanDistance(Coordinate c1, Coordinate c2) {
        return Math.abs(c1.x() - c2.x()) + Math.abs(c1.y() - c2.y());
    }
}

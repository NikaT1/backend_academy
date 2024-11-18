package backend.academy.graph;

import backend.academy.graph.model.Coordinate;
import backend.academy.graph.model.Graph;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GraphGenerator {
    public static Graph getGraphWithoutEdges(int width, int height) {
        Graph graph = new Graph();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                graph.addCoordinate(new Coordinate(i, j));
            }
        }
        return graph;
    }
}

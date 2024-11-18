package backend.academy;

import backend.academy.graph.GraphGenerator;
import backend.academy.graph.model.Graph;
import backend.academy.maze.Cell;
import backend.academy.maze.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class MazeTest {
    @Test
    void checkCorrectGraphGeneratorTest() {
        int testWidth = 4;
        int testHeight = 5;
        Graph graph = GraphGenerator.getGraphWithoutEdges(testWidth, testHeight);
        Assertions.assertEquals(testWidth * testHeight, graph.getCoordinates().size());
    }

    @Test
    public void checkCorrectMazeInitTest() {
        Graph graph = GraphGenerator.getGraphWithoutEdges(1, 1);
        Maze maze = new Maze(1, 1).initMaze(graph);
        Assertions.assertTrue(maze.getCell(0, 0)
            .type()
            .containsAll(List.of(Cell.Type.WALL, Cell.Type.FLOOR)));
    }
}

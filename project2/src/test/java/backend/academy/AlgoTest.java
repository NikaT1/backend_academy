package backend.academy;

import backend.academy.algo.maze.MazeGenerator;
import backend.academy.algo.maze.MazeGeneratorFactory;
import backend.academy.algo.maze.MazeGeneratorType;
import backend.academy.algo.path.MazeSolver;
import backend.academy.algo.path.MazeSolverFactory;
import backend.academy.algo.path.MazeSolverType;
import backend.academy.graph.GraphGenerator;
import backend.academy.graph.model.Coordinate;
import backend.academy.graph.model.Graph;
import backend.academy.maze.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import java.util.LinkedList;
import java.util.List;

public class AlgoTest {
    private static final int testWidth = 4;
    private static final int testHeight = 5;
    private static final Coordinate testStart = new Coordinate(0, 0);
    private static final Coordinate testFinish = new Coordinate(3, 4);

    @ParameterizedTest
    @EnumSource(MazeGeneratorType.class)
    void checkCorrectMazeGenerationTest(MazeGeneratorType type) {
        MazeGenerator mazeGenerator = MazeGeneratorFactory.createMazeGenerator(type);
        Maze maze = mazeGenerator.createMaze(testWidth, testHeight);
        Assertions.assertNotNull(maze.graph());
        Assertions.assertNotNull(maze.getCell(0, 0));
        Assertions.assertNotNull(maze.getCell(3, 4));
    }

    @ParameterizedTest
    @EnumSource(MazeSolverType.class)
    void checkCorrectRandomMazeSolvingTest(MazeSolverType type) {
        Maze maze = MazeGeneratorFactory.createMazeGenerator(MazeGeneratorType.values()[0])
            .createMaze(testWidth, testHeight);
        MazeSolver mazeSolver = MazeSolverFactory.createMazeSolver(type);
        List<Coordinate> path = mazeSolver.solveMaze(maze, testStart, testFinish);
        Assertions.assertNotNull(path);
        Assertions.assertTrue(path.contains(testStart));
        Assertions.assertTrue(path.contains(testFinish));
    }

    @Nested
    class CorrectPathTest {
        private Graph graph;

        @BeforeEach
        void beforeAll() {
            graph = GraphGenerator.getGraphWithoutEdges(2, 2);
            graph.addEdge(new Coordinate(0, 0), new Coordinate(0, 1));
        }

        @ParameterizedTest
        @EnumSource(MazeSolverType.class)
        void checkPathWithCorrectMazeTest(MazeSolverType type) {
            graph.addEdge(new Coordinate(0, 1), new Coordinate(1, 1));
            Maze maze = new Maze(2, 2).initMaze(graph);
            List<Coordinate> correctPath = new LinkedList<>(List.of(new Coordinate(1, 1),
                new Coordinate(0, 1),
                new Coordinate(0, 0)));

            MazeSolver mazeSolver = MazeSolverFactory.createMazeSolver(type);
            List<Coordinate> path = mazeSolver.solveMaze(maze, new Coordinate(0, 0), new Coordinate(1, 1));

            Assertions.assertNotNull(path);
            Assertions.assertIterableEquals(correctPath, path);
        }

        @ParameterizedTest
        @EnumSource(MazeSolverType.class)
        void checkPathWithIncorrectMazeTest(MazeSolverType type) {
            Maze maze = new Maze(2, 2).initMaze(graph);
            List<Coordinate> correctPath = new LinkedList<>();

            MazeSolver mazeSolver = MazeSolverFactory.createMazeSolver(type);
            List<Coordinate> path = mazeSolver.solveMaze(maze, new Coordinate(0, 0), new Coordinate(1, 1));

            Assertions.assertNotNull(path);
            Assertions.assertIterableEquals(correctPath, path);
        }
    }
}

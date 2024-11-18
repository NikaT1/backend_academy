package backend.academy.algo.path;

import backend.academy.graph.model.Coordinate;
import backend.academy.maze.Maze;
import java.util.List;

public interface MazeSolver {
    List<Coordinate> solveMaze(Maze maze, Coordinate start, Coordinate finish);
}

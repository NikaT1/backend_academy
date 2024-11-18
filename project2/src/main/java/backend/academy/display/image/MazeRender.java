package backend.academy.display.image;

import backend.academy.graph.model.Coordinate;
import backend.academy.maze.Maze;
import java.util.List;

public interface MazeRender {

    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);

}

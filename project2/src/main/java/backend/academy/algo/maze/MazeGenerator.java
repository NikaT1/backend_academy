package backend.academy.algo.maze;

import backend.academy.maze.Maze;

public interface MazeGenerator {
    Maze createMaze(int width, int height);
}

package backend.academy.algo.maze;

import backend.academy.algo.maze.prim_algo.PrimAlgorithm;
import backend.academy.algo.maze.recursive_backtracker_algo.RecursiveBacktrackerAlgorithm;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MazeGeneratorFactory {
    public static MazeGenerator createMazeGenerator(MazeGeneratorType type) {
        return switch (type) {
            case PRIM -> new PrimAlgorithm();
            case RECURSIVE_BACKTRACKER -> new RecursiveBacktrackerAlgorithm();
        };
    }
}

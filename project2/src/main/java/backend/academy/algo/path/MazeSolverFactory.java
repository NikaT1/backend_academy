package backend.academy.algo.path;

import backend.academy.algo.path.a_start_algo.AStarAlgorithm;
import backend.academy.algo.path.dfs_algo.DFSAlgorithm;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MazeSolverFactory {
    public static MazeSolver createMazeSolver(MazeSolverType type) {
        return switch (type) {
            case DFS -> new DFSAlgorithm();
            case A_STAR -> new AStarAlgorithm();
        };
    }
}

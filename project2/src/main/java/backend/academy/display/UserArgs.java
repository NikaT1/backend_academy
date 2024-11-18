package backend.academy.display;

import backend.academy.algo.maze.MazeGeneratorType;
import backend.academy.algo.path.MazeSolverType;
import backend.academy.graph.model.Coordinate;
import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserArgs {

    private MazeGeneratorType mazeGeneratorType;
    private MazeSolverType mazeSolverType;
    private int width;
    private int height;
    private Coordinate startCoordinate;
    private Coordinate endCoordinate;

    @Parameter(names = {"-m", "-maze"}, description = "Maze generator algorithm type")
    public void setMazeGeneratorType(String value) {
        this.mazeGeneratorType = MazeGeneratorType.valueOf(value);
    }

    @Parameter(names = {"-p", "-path"}, description = "Maze solver algorithm type")
    public void setMazeSolverType(String value) {
        this.mazeSolverType = MazeSolverType.valueOf(value);
    }

}

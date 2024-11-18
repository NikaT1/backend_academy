package backend.academy;

import backend.academy.algo.maze.MazeGenerator;
import backend.academy.algo.maze.MazeGeneratorFactory;
import backend.academy.algo.path.MazeSolver;
import backend.academy.algo.path.MazeSolverFactory;
import backend.academy.display.UserArgs;
import backend.academy.display.UserIOService;
import backend.academy.display.image.MazeRender;
import backend.academy.display.image.MazeRenderImpl;
import backend.academy.display.io.ConsoleInput;
import backend.academy.display.io.ConsoleOutput;
import backend.academy.display.io.Input;
import backend.academy.display.io.Output;
import backend.academy.graph.model.Coordinate;
import backend.academy.maze.Maze;
import java.io.IOException;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import static backend.academy.AppInitializer.loadUserArgs;

@UtilityClass
@Log4j2
public class Main {
    public static void main(String[] args) {
        try (Input consoleInput = new ConsoleInput(System.in);
             Output consoleOutput = new ConsoleOutput(System.out)) {
            init(consoleInput, consoleOutput, args);
        } catch (IOException e) {
            log.error("Error occurred during the input/output process: " + e);
        }
    }

    public static void init(Input input, Output output, String[] args) {
        UserIOService userIO = new UserIOService(input, output);
        userIO.sendUserStartInfo();
        UserArgs userArgs = loadUserArgs(args, userIO);
        userArgs = userIO.getMissingArgsFromUser(userArgs);

        MazeGenerator mazeGenerator = MazeGeneratorFactory.createMazeGenerator(userArgs.mazeGeneratorType());
        MazeSolver mazeSolver = MazeSolverFactory.createMazeSolver(userArgs.mazeSolverType());
        MazeRender mazeRender = new MazeRenderImpl();

        Maze maze = mazeGenerator.createMaze(userArgs.width(), userArgs.height());
        userIO.sendGeneratedMazeImage(mazeRender.render(maze));

        List<Coordinate> path = mazeSolver.solveMaze(maze, userArgs.startCoordinate(), userArgs.endCoordinate());
        if (path.isEmpty()) {
            userIO.sendNoPathFoundMessage();
        } else {
            userIO.sendPathFoundMessage(mazeRender.render(maze, path));
        }
    }
}

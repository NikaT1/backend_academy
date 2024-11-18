package backend.academy.display;

import backend.academy.algo.maze.MazeGeneratorType;
import backend.academy.algo.path.MazeSolverType;
import backend.academy.display.io.Input;
import backend.academy.display.io.Output;
import backend.academy.graph.model.Coordinate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import static backend.academy.random.RandomUtil.getRandomNumberInRange;

@RequiredArgsConstructor
public class UserIOService {

    private final Input input;
    private final Output output;

    public void sendUserStartInfo() {
        output.println(UserMessages.START_MSG);
    }

    public UserArgs getMissingArgsFromUser(UserArgs userArgs) {
        if (userArgs.mazeGeneratorType() == null) {
            userArgs.mazeGeneratorType(getMazeGeneratorType());
        }
        if (userArgs.mazeSolverType() == null) {
            userArgs.mazeSolverType(getMazeSolverType());
        }
        userArgs.width(
            readNumber(UserMessages.MAZE_WIDTH_INPUT_MSG, UserMessages.MIN_MAZE_SIZE, UserMessages.MAX_MAZE_SIZE));
        userArgs.height(
            readNumber(UserMessages.MAZE_HEIGHT_INPUT_MSG, UserMessages.MIN_MAZE_SIZE, UserMessages.MAX_MAZE_SIZE));
        userArgs.startCoordinate(Coordinate.builder()
            .x(readNumber(UserMessages.MAZE_START_X_INPUT_MSG, 0, userArgs.width()))
            .y(readNumber(UserMessages.MAZE_START_Y_INPUT_MSG, 0, userArgs.height()))
            .build());
        userArgs.endCoordinate(Coordinate.builder()
            .x(readNumber(UserMessages.MAZE_FINISH_X_INPUT_MSG, 0, userArgs.width()))
            .y(readNumber(UserMessages.MAZE_FINISH_Y_INPUT_MSG, 0, userArgs.height()))
            .build());
        return userArgs;
    }

    public void sendNoPathFoundMessage() {
        output.println(UserMessages.NO_PATH_FOUND_MSG);
    }

    public void sendPathFoundMessage(String mazeImage) {
        output.println(UserMessages.PATH_FOUND_MSG);
        output.println(mazeImage);
    }

    public void sendGeneratedMazeImage(String mazeImage) {
        output.println(UserMessages.GENERATED_MAZE_MSG);
        output.println(mazeImage);
    }

    public void sendUserArgsErrMessage() {
        output.println(UserMessages.ERROR_USER_ARGS_MSG);
    }

    private MazeSolverType getMazeSolverType() {
        output.print(UserMessages.MAZE_SOLVER_TYPE_INPUT_MSG + MazeSolverType.getListToPrint());
        int index = readNumber(1, MazeSolverType.values().length + 1);
        MazeSolverType type = MazeSolverType.values()[index - 1];
        output.printf(UserMessages.MAZE_SOLVER_TYPE_OUTPUT_MSG, type);
        return type;
    }

    private MazeGeneratorType getMazeGeneratorType() {
        output.print(UserMessages.MAZE_GENERATOR_TYPE_INPUT_MSG + MazeGeneratorType.getListToPrint());
        int index = readNumber(1, MazeGeneratorType.values().length + 1);
        MazeGeneratorType type = MazeGeneratorType.values()[index - 1];
        output.printf(UserMessages.MAZE_GENERATOR_TYPE_OUTPUT_MSG, type);
        return type;
    }

    private int readNumber(String msg, int minVal, int maxVal) {
        output.print(msg);
        int number = readNumber(minVal, maxVal);
        output.printf(UserMessages.VALUE_OUTPUT_MSG, number);
        return number;
    }

    private int readNumber(int minVal, int maxVal) {
        String userInput = input.read();
        Optional<Integer> number = getNumberFromLine(userInput, minVal, maxVal);
        while (!userInput.isEmpty() && number.isEmpty()) {
            output.println(UserMessages.ERROR_INPUT_MSG);
            userInput = input.read();
            number = getNumberFromLine(userInput, minVal, maxVal);
        }
        return number.orElseGet(() -> getRandomNumberInRange(minVal, maxVal));
    }

    private Optional<Integer> getNumberFromLine(String line, int minVal, int maxVal) {
        if (StringUtils.isNumeric(line)) {
            int val = Integer.parseInt(line);
            if (val >= minVal && val < maxVal) {
                return Optional.of(val);
            }
        }
        return Optional.empty();
    }
}

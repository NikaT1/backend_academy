package backend.academy;

import backend.academy.algo.maze.MazeGeneratorType;
import backend.academy.algo.path.MazeSolverType;
import backend.academy.display.UserArgs;
import backend.academy.display.UserIOService;
import backend.academy.display.io.ConsoleOutput;
import backend.academy.display.io.Input;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static backend.academy.display.UserMessages.ERROR_INPUT_MSG;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DisplayTest {
    private final String correctInput1 = "5";
    private final String correctInput2 = "4";
    private ByteArrayOutputStream byteOutputStream;
    private PrintStream outputStream;
    @Mock
    private Input input;

    @BeforeEach
    public void beforeEach() {
        byteOutputStream = new ByteArrayOutputStream();
        outputStream = new PrintStream(byteOutputStream);
    }

    @ParameterizedTest
    @MethodSource("provideIncorrectInput")
    public void checkGetMissingArgsIncorrectInputTest(String incorrectInput) {
        when(input.read()).thenReturn(incorrectInput, correctInput1,
            incorrectInput, correctInput1,
            incorrectInput, correctInput2,
            incorrectInput, correctInput2,
            incorrectInput, correctInput2,
            incorrectInput, correctInput2);
        UserIOService userIOService = new UserIOService(input, new ConsoleOutput(outputStream));
        UserArgs userArgs = new UserArgs();
        userArgs.mazeSolverType(MazeSolverType.DFS);
        userArgs.mazeGeneratorType(MazeGeneratorType.PRIM);
        userIOService.getMissingArgsFromUser(userArgs);
        Assertions.assertTrue(byteOutputStream.toString().contains(ERROR_INPUT_MSG));
        int correctNumber1 = Integer.parseInt(correctInput1);
        int correctNumber2 = Integer.parseInt(correctInput2);
        Assertions.assertAll(
            () -> Assertions.assertEquals(correctNumber1, userArgs.width()),
            () -> Assertions.assertEquals(correctNumber1, userArgs.height()),
            () -> Assertions.assertEquals(correctNumber2, userArgs.startCoordinate().x()),
            () -> Assertions.assertEquals(correctNumber2, userArgs.startCoordinate().y()),
            () -> Assertions.assertEquals(correctNumber2, userArgs.endCoordinate().x()),
            () -> Assertions.assertEquals(correctNumber2, userArgs.endCoordinate().y())
        );
    }

    @Test
    public void checkGetMissingArgsCorrectInputTest() {
        when(input.read()).thenReturn(correctInput1,
            correctInput1,
            correctInput2,
            correctInput2,
            correctInput2,
            correctInput2);
        UserIOService userIOService = new UserIOService(input, new ConsoleOutput(outputStream));
        UserArgs userArgs = new UserArgs();
        userArgs.mazeSolverType(MazeSolverType.DFS);
        userArgs.mazeGeneratorType(MazeGeneratorType.PRIM);
        userIOService.getMissingArgsFromUser(userArgs);
        Assertions.assertFalse(byteOutputStream.toString().contains(ERROR_INPUT_MSG));
        int correctNumber1 = Integer.parseInt(correctInput1);
        int correctNumber2 = Integer.parseInt(correctInput2);
        Assertions.assertAll(
            () -> Assertions.assertEquals(correctNumber1, userArgs.width()),
            () -> Assertions.assertEquals(correctNumber1, userArgs.height()),
            () -> Assertions.assertEquals(correctNumber2, userArgs.startCoordinate().x()),
            () -> Assertions.assertEquals(correctNumber2, userArgs.startCoordinate().y()),
            () -> Assertions.assertEquals(correctNumber2, userArgs.endCoordinate().x()),
            () -> Assertions.assertEquals(correctNumber2, userArgs.endCoordinate().y())
        );
    }

    private static Stream<String> provideIncorrectInput() {
        return Stream.of(
            "ghgh", "[", "-5"
        );
    }

    @AfterEach
    public void afterEach() {
        outputStream.close();
    }

}

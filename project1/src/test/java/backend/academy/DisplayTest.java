package backend.academy;

import backend.academy.display.UserIOService;
import backend.academy.display.io.ConsoleOutput;
import backend.academy.display.io.Input;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;
import static backend.academy.display.UserMessages.ERROR_INPUT_MSG;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DisplayTest {
    private final String correctInput = "a";
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
    public void checkReenteringLetterTest(String incorrectInput) {
        when(input.read()).thenReturn(incorrectInput, correctInput);
        UserIOService userIOService = new UserIOService(input, new ConsoleOutput(outputStream));
        userIOService.getNextUserLetter();
        Assertions.assertTrue(byteOutputStream.toString().contains(ERROR_INPUT_MSG));
    }

    @ParameterizedTest
    @MethodSource("provideCorrectInput")
    public void checkEnteringLetterInDifferentRegistersTest(String correctInput) {
        when(input.read()).thenReturn(correctInput, correctInput);
        UserIOService userIOService = new UserIOService(input, new ConsoleOutput(outputStream));
        char letter = userIOService.getNextUserLetter();
        Assertions.assertEquals(String.valueOf(letter), correctInput.toLowerCase(),
            "Введенная и полученная буквы не совпадают");
    }

    private static Stream<String> provideIncorrectInput() {
        return Stream.of(
            "4", "[", "fddfd"
        );
    }

    private static Stream<String> provideCorrectInput() {
        return Stream.of(
            "a", "A"
        );
    }

    @AfterEach
    public void afterEach() {
        outputStream.close();
    }

}

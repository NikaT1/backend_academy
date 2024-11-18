package backend.academy;

import backend.academy.display.UserArgs;
import backend.academy.display.UserIOService;
import backend.academy.game.GameSession;
import backend.academy.game.SessionState;
import backend.academy.words.WordsDictionaryService;
import backend.academy.words.model.ComplexityLevel;
import backend.academy.words.model.Word;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.stream.Stream;
import static backend.academy.GameSessionGenerator.getGameSession;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameTest {

    private final String baseDir = System.getProperty("user.dir") + "/src/main/resources";
    private static UserIOService userIOService;
    @Mock
    private WordsDictionaryService wordsDictionaryService;
    @Mock
    private UserArgs userArgs;

    @BeforeAll
    static void beforeAll() {
        userIOService = mock(UserIOService.class);
        doNothing().when(userIOService).sendWordStartInfo(any());
        doNothing().when(userIOService).sendLoseFinalMessage();
        doNothing().when(userIOService).sendWinFinalMessage();
        doNothing().when(userIOService).sendGameAnswer(any());
    }

    @Test
    @DisplayName("Проверка корректного состояния игры в зависимости от введенной пользователем буквы")
    public void checkGameLetterStateTest() {
        GameSession gameSession = new GameSession(new Word("abcd", ""), 4);

        Assertions.assertTrue(gameSession.addLetter('a'), "Некорректное состояние после ввода верной буквы");
        Assertions.assertEquals(gameSession.currentStepCount(), 0);
        Assertions.assertEquals(gameSession.currentState(), SessionState.IN_PROCESS);

        Assertions.assertFalse(gameSession.addLetter('r'), "Некорректное состояние после ввода неверной буквы");
        Assertions.assertEquals(gameSession.currentStepCount(), 1);
        Assertions.assertEquals(gameSession.currentState(), SessionState.IN_PROCESS);
    }

    @ParameterizedTest
    @MethodSource("provideStatusGameParams")
    @DisplayName("Проверка корректного завершения игры в зависимости от ввода пользователя (выигрыш/проигрыш)")
    public void checkGameStateTest(SessionState state, int maxStepsCount) {
        GameSession gameSession = new GameSession(new Word("abcd", ""), maxStepsCount);
        try (MockedStatic<GameSessionGenerator> mockStatic = mockStatic(GameSessionGenerator.class)) {
            mockStatic.when(() -> getGameSession(any(), any())).thenReturn(gameSession);
            when(userIOService.getNextUserLetter()).thenReturn('a', 'b', 't', 'r', 'c', 'd');
            GameDriver gameDriver = new GameDriver(userIOService).initGameSession(userArgs, wordsDictionaryService);
            gameDriver.startGame();
            Assertions.assertEquals(gameSession.currentState(), state, "Неверное состояние игры");
        }
    }

    @Test
    @DisplayName("Проверка взятия пути к файлу со словами из переменной окружения")
    public void checkFilePathFromEnvTest() {
        String filePath = "my_file_path";
        System.setProperty("file", filePath);
        Assertions.assertEquals(GameInitializer.getWordsFilePathFromEnv("file"),
            filePath, "Сохраненный и считанный путь к файлу не совпадают");
    }

    @Test
    @DisplayName("Проверка взятия пути к файлу со словами из переменной окружения")
    public void checkDefaultFilePathTest() {
        Assertions.assertTrue(GameInitializer.getWordsFilePathFromEnv("incorrect_env").contains(baseDir),
            "Некорректный дефолтный путь к файлу");
    }

    @Test
    public void checkGameSessionGeneratorTest() {
        String word = "test";
        when(wordsDictionaryService.getRandomWord(any(), any())).thenReturn(new Word(word, "test_hint"));
        when(userArgs.level()).thenReturn(ComplexityLevel.ELEMENTARY);
        GameSession gameSession = getGameSession(wordsDictionaryService, userArgs);
        Assertions.assertAll(
            () -> Assertions.assertEquals(gameSession.maxStepsCount(), ComplexityLevel.ELEMENTARY.maxStepCount()),
            () -> Assertions.assertEquals(String.valueOf(gameSession.word()), word)
        );
    }

    @Test
    public void checkNoGameSessionExceptionTest() {
        GameDriver gameDriver = new GameDriver(userIOService);
        assertThatThrownBy(
            gameDriver::startGame)
            .isInstanceOf(NullPointerException.class);
    }

    private static Stream<Arguments> provideStatusGameParams() {
        return Stream.of(
            Arguments.of(SessionState.WIN, 4),
            Arguments.of(SessionState.LOSE, 2)
        );
    }
}

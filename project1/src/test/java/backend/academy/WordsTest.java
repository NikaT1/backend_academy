package backend.academy;

import backend.academy.display.UserIOService;
import backend.academy.words.WordsDictionaryRepository;
import backend.academy.words.exception.IncorrectWordsFileException;
import backend.academy.words.exception.NoSuchWordsSpecifiedException;
import backend.academy.words.model.ComplexityLevel;
import backend.academy.words.WordsDictionaryService;
import backend.academy.words.exception.IncorrectWordLengthException;
import backend.academy.words.model.Word;
import backend.academy.words.model.WordsCategories;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import static backend.academy.GameInitializer.createWordsDictionary;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class WordsTest {
    private static UserIOService userIOService;
    private final String baseDir = System.getProperty("user.dir") + "/src/test/resources";

    @BeforeAll
    static void beforeAll() {
        userIOService = mock(UserIOService.class);
        doNothing().when(userIOService).sendWordStartInfo(any());
        doNothing().when(userIOService).sendLoseFinalMessage();
        doNothing().when(userIOService).sendWinFinalMessage();
        doNothing().when(userIOService).sendGameAnswer(any());
    }

    @ParameterizedTest
    @MethodSource("provideWordsParams")
    void checkCorrectWordSelectingTest(String category, ComplexityLevel level, String... words) throws IOException {
        String path = baseDir + "/game_words.json";
        WordsDictionaryService wordsDictionaryService = createWordsDictionary(path);
        Assertions.assertNotNull(wordsDictionaryService);
        Word word = wordsDictionaryService.getRandomWord(category, level);
        Assertions.assertTrue(List.of(words).contains(word.name()), "Выбранное слово не соответствует " +
            "установленной категории и уровню сложности");
    }

    @Test
    public void checkIncorrectWordsFileTest() {
        String path = baseDir + "/game_words_incorrect.json";
        assertThatThrownBy(() ->
            createWordsDictionary(path))
            .isInstanceOf(IncorrectWordsFileException.class)
            .hasMessageContaining("Incorrect words file has been provided");
    }

    @Test
    public void checkNoWordsSpecifiedExceptionTest() throws IOException {
        String path = baseDir + "/game_words_empty.json";
        WordsDictionaryService wordsDictionary = createWordsDictionary(path);
        Assertions.assertNotNull(wordsDictionary);
        assertThatThrownBy(
            () -> wordsDictionary.getRandomWord("animals", ComplexityLevel.ELEMENTARY))
            .isInstanceOf(NoSuchWordsSpecifiedException.class)
            .hasMessageContaining("No such game words were specified");
    }

    @Test
    public void checkNoDictionaryExceptionTest() {
        WordsDictionaryRepository repository = new WordsDictionaryRepository();
        WordsDictionaryService service = new WordsDictionaryService(repository);
        assertThatThrownBy(
            service::getWordsCategories)
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void checkCannotModifyCategoriesTest() throws IOException {
        String path = baseDir + "/game_words.json";
        WordsDictionaryService service = createWordsDictionary(path);
        WordsCategories wordsCategories = service.getWordsCategories();
        assertThatThrownBy(
            () -> wordsCategories.categories().add("test")
        )
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void checkTooShortWordExceptionTest() {
        String path = baseDir + "/game_words_incorrect_length.json";
        assertThatThrownBy(
            () -> createWordsDictionary(path))
            .isInstanceOf(IncorrectWordLengthException.class);

    }

    private static Stream<Arguments> provideWordsParams() {
        return Stream.of(
            Arguments.of("animals", ComplexityLevel.ELEMENTARY, new String[] {"fish", "bird"}),
            Arguments.of("animals", ComplexityLevel.MEDIUM, new String[] {"hamster", "tortoise"}),
            Arguments.of("entertainments", ComplexityLevel.ADVANCED, new String[] {"exhibition", "rollerblading"})
        );
    }

}

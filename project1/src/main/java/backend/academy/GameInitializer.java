package backend.academy;

import backend.academy.display.UserArgs;
import backend.academy.display.UserIOService;
import backend.academy.display.io.FileInput;
import backend.academy.display.io.Input;
import backend.academy.words.WordsDictionaryRepository;
import backend.academy.words.WordsDictionaryService;
import backend.academy.words.exception.IncorrectWordLengthException;
import backend.academy.words.exception.IncorrectWordsFileException;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.io.IOException;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class GameInitializer {
    public static WordsDictionaryService createWordsDictionary(String path) throws IOException {
        try (Input fileInput = new FileInput(path)) {
            WordsDictionaryRepository repository = new WordsDictionaryRepository()
                .initDictionary(fileInput);
            return new WordsDictionaryService(repository);
        } catch (IncorrectWordsFileException | IncorrectWordLengthException e) {
            log.error("Wrong structure of words file: " + e);
            throw e;
        } catch (IOException e) {
            log.error("It is impossible to read data from file: " + e);
            throw e;
        }
    }

    public static UserArgs loadUserArgs(String[] args, UserIOService userIO) {
        try {
            UserArgs userArgs = new UserArgs();
            JCommander.newBuilder()
                .addObject(userArgs)
                .build()
                .parse(args);
            return userArgs;
        } catch (ParameterException e) {
            log.error("Invalid user params specified. The program execution will continue with empty params.");
            userIO.sendUserArgsErrMessage();
            return new UserArgs();
        }
    }

    public static String getWordsFilePathFromEnv(String envName) {
        return System.getProperty(envName,
            System.getProperty("user.dir") + "/src/main/resources/game_words.json");
    }
}

package backend.academy;

import backend.academy.display.UserArgs;
import backend.academy.display.UserIOService;
import backend.academy.display.image.HangedManGenerator;
import backend.academy.display.io.ConsoleInput;
import backend.academy.display.io.ConsoleOutput;
import backend.academy.display.io.Input;
import backend.academy.display.io.Output;
import backend.academy.words.WordsDictionaryService;
import backend.academy.words.exception.IncorrectWordLengthException;
import backend.academy.words.exception.IncorrectWordsFileException;
import backend.academy.words.exception.NoSuchWordsSpecifiedException;
import java.io.IOException;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import static backend.academy.GameInitializer.createWordsDictionary;
import static backend.academy.GameInitializer.getWordsFilePathFromEnv;
import static backend.academy.GameInitializer.loadUserArgs;

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

    public static void init(Input input, Output output, String[] args) throws IOException {
        UserIOService userIO = new UserIOService(input, output);
        try {
            String wordsFilePath = getWordsFilePathFromEnv("words_file");
            WordsDictionaryService dictionary = createWordsDictionary(wordsFilePath);
            userIO.sendUserStartInfo();
            UserArgs userArgs = loadUserArgs(args, userIO);
            userArgs = userIO.getMissingArgsFromUser(userArgs, dictionary.getWordsCategories());
            userIO.pictureGenerator(new HangedManGenerator(userArgs.level().maxStepCount()));
            new GameDriver(userIO)
                .initGameSession(userArgs, dictionary)
                .startGame();
        } catch (IncorrectWordsFileException e) {
            userIO.sendWordsInitErrMessage(); //невозможно продолжить игру, если нет слов
        } catch (IncorrectWordLengthException | NoSuchWordsSpecifiedException e) {
            //невозможно продолжить игру, если в файле отсутствуют/некорректные слова
            userIO.sendWordsErrMessage(e.getMessage());
        }
    }

}

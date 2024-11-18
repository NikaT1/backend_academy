package backend.academy;

import backend.academy.display.UserArgs;
import backend.academy.game.GameSession;
import backend.academy.words.WordsDictionaryService;
import backend.academy.words.exception.IncorrectWordLengthException;
import backend.academy.words.exception.NoSuchWordsSpecifiedException;
import backend.academy.words.model.Word;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class GameSessionGenerator {
    public static GameSession getGameSession(WordsDictionaryService dictionary, UserArgs args) {
        try {
            Word word = dictionary.getRandomWord(args.category(), args.level());
            return new GameSession(word, args.level().maxStepCount());
        } catch (IncorrectWordLengthException | NoSuchWordsSpecifiedException e) {
            log.error("Incorrect dictionary composition: " + e);
            throw e;
        }
    }
}

package backend.academy.display;

import backend.academy.display.exception.NoPictureGeneratorFoundException;
import backend.academy.display.image.PictureGenerator;
import backend.academy.display.io.Input;
import backend.academy.display.io.Output;
import backend.academy.game.GuessResult;
import backend.academy.words.model.ComplexityLevel;
import backend.academy.words.model.WordsCategories;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import static backend.academy.random.RandomNumberGenerator.getRandomNumberInRange;

@RequiredArgsConstructor
public class UserIOService {

    private final Input input;
    private final Output output;
    @Setter
    private PictureGenerator pictureGenerator;

    public void sendUserStartInfo() {
        output.println(UserMessages.START_MSG);
    }

    public void sendWordStartInfo(String hiddenWord) {
        output.printf(UserMessages.ANSWER_STATISTIC, hiddenWord);
    }

    public UserArgs getMissingArgsFromUser(UserArgs userArgs, WordsCategories categories) {
        if (userArgs.category() == null) {
            userArgs.category(getCategory(categories));
        }
        if (userArgs.level() == null) {
            userArgs.level(getLevel());
        }
        return userArgs;
    }

    public char getNextUserLetter() {
        output.println(UserMessages.NEW_LETTER_MSG);
        String line = input.read();
        while (!Pattern.matches(UserMessages.CORRECT_LETTER_RGX, line)) {
            output.println(UserMessages.ERROR_INPUT_MSG);
            line = input.read();
        }
        return line.toLowerCase().charAt(0);
    }

    public void sendGameAnswer(GuessResult guessResult) {
        output.clear();

        if (guessResult.correctLetter()) {
            output.println(UserMessages.CORRECT_ANSWER_MSG);
        } else {
            output.println(UserMessages.WRONG_ANSWER_MSG);
            Optional.ofNullable(pictureGenerator)
                .orElseThrow(NoPictureGeneratorFoundException::new)
                .updatePicture();
        }
        output.printf(UserMessages.GAME_STATISTIC, guessResult.currentStepCount(), guessResult.maxStepCount());
        output.println(pictureGenerator.getPicture());
        output.printf(UserMessages.ANSWER_STATISTIC, guessResult.currentAnswerStr());
        output.printf(UserMessages.LETTERS_STATISTIC, guessResult.currentWrongLettersStr());
        if (guessResult.needToShowHint()) {
            output.printf(UserMessages.HINT, guessResult.hint());
        }
    }

    public void sendRepeatedLetterMessage() {
        output.println(UserMessages.ERROR_REPEATED_LETTER_MSG);
    }

    public void sendWinFinalMessage() {
        output.println(UserMessages.WIN_MSG);
    }

    public void sendLoseFinalMessage() {
        output.println(UserMessages.LOSE_MSG);
    }

    public void sendWordsInitErrMessage() {
        output.println(UserMessages.ERROR_INIT_WORDS_MSG);
    }

    public void sendUserArgsErrMessage() {
        output.println(UserMessages.ERROR_USER_ARGS_MSG);
    }

    public void sendWordsErrMessage(String err) {
        output.println(UserMessages.ERROR_WORDS_MSG + err);
    }

    private String getCategory(WordsCategories wordsCategories) {
        output.print(UserMessages.CATEGORY_INPUT_MSG + wordsCategories.categoriesToPrint());
        int index = readNumber(1, wordsCategories.categories().size() + 1);
        String category = wordsCategories.categories().get(index - 1);
        output.printf(UserMessages.CATEGORY_OUTPUT_MSG, category);
        return category;
    }

    private ComplexityLevel getLevel() {
        output.print(UserMessages.LEVEL_INPUT_MSG + ComplexityLevel.getListToPrint());
        int index = readNumber(1, ComplexityLevel.values().length + 1);
        ComplexityLevel level = ComplexityLevel.values()[index - 1];
        output.printf(UserMessages.LEVEL_OUTPUT_MSG, level);
        return level;
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

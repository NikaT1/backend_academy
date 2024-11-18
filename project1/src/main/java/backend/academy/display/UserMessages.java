package backend.academy.display;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMessages {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CLEAR_SCREEN = "\033[H\033[2J";

    public static final String DEFAULT_ARG_MSG = "(enter nothing for random selection):" + System.lineSeparator();
    public static final String START_MSG = GREEN + "Welcome to the Hangman game!" + System.lineSeparator() + RESET;
    public static final String CATEGORY_INPUT_MSG = "Enter the number of the selected category "
        + DEFAULT_ARG_MSG;
    public static final String LEVEL_INPUT_MSG = "Enter the number of the selected complexity level "
        + DEFAULT_ARG_MSG;
    public static final String CATEGORY_OUTPUT_MSG = YELLOW + "Selected category: %s" + System.lineSeparator() + RESET;
    public static final String LEVEL_OUTPUT_MSG = YELLOW + "Selected level: %s" + System.lineSeparator() + RESET;
    public static final String NEW_LETTER_MSG = "Enter a letter: ";
    public static final String ERROR_INPUT_MSG = "Error, repeat the input: ";
    public static final String ERROR_REPEATED_LETTER_MSG = "The letter was already there, repeat the input";
    public static final String CORRECT_ANSWER_MSG = GREEN + "Congratulations, you guessed a letter!" + RESET;
    public static final String WRONG_ANSWER_MSG = RED + "Unfortunately, you made a mistake!" + RESET;
    public static final String GAME_STATISTIC = "Spent: %d attempt(s) from %d" + System.lineSeparator();
    public static final String ANSWER_STATISTIC = YELLOW + "Hidden word: %s" + System.lineSeparator() + RESET;
    public static final String HINT = YELLOW + "Hint: %s" + System.lineSeparator() + RESET;
    public static final String LETTERS_STATISTIC = "Incorrect letters: %s" + System.lineSeparator();
    public static final String WIN_MSG = GREEN + "Congratulations, you have won!!!" + RESET;
    public static final String LOSE_MSG = RED + "Unfortunately, you lost(" + RESET;
    public static final String ERROR_INIT_WORDS_MSG = RED + "It is impossible to create a dictionary of words"
        + " - an incorrect file" + RESET;
    public static final String ERROR_USER_ARGS_MSG = RED + "Incorrect program startup arguments -"
        + " values will be ignored" + RESET;
    public static final String ERROR_WORDS_MSG = RED + "Incorrect or missing words in the dictionary: " + RESET;
    public static final String CORRECT_LETTER_RGX = "[a-zA-Z]";
}

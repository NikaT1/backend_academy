package backend.academy.words.exception;

public class IncorrectWordLengthException extends IllegalArgumentException {
    public IncorrectWordLengthException(String incorrectWord, int minLength, int maxLength) {
        super("Selected word '" + incorrectWord + "' is too short. The word length should be between " + minLength
            + " and " + maxLength);
    }
}

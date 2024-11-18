package backend.academy.words.exception;

import java.io.IOException;

public class IncorrectWordsFileException extends IOException {
    public IncorrectWordsFileException(Exception e) {
        super("Incorrect words file has been provided", e);
    }

    public IncorrectWordsFileException(String msg) {
        super("Incorrect words file has been provided: " + msg);
    }
}

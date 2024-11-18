package backend.academy;

import backend.academy.display.io.FileInput;
import backend.academy.display.io.InputFactory;
import backend.academy.display.io.URLInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.MalformedURLException;

public class InputTest {

    private static final String CORRECT_URL =
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
    private static final String INCORRECT_URL = "https://incorrect_examples/master";
    private static final String CORRECT_FILE_PATTERN = "**/correct_file.txt";
    private static final String INCORRECT_FILE_PATTERN = "**/incorrect_file.txt";

    @Test
    void checkCorrectUrlInputTest() {
        URLInput urlInput = new URLInput(CORRECT_URL);
        Assertions.assertDoesNotThrow(() -> urlInput.read());
    }

    @Test
    void checkIncorrectUrlInputTest() throws MalformedURLException {
        URLInput urlInput = new URLInput(INCORRECT_URL);
        Assertions.assertThrows(IllegalArgumentException.class, () -> urlInput.read());
    }

    @Test
    void checkCorrectFileInputTest() {
        FileInput fileInput = new FileInput(CORRECT_FILE_PATTERN);
        Assertions.assertDoesNotThrow(() -> fileInput.read());
    }

    @Test
    void checkIncorrectFileInputTest() throws IOException {
        FileInput fileInput = new FileInput(INCORRECT_FILE_PATTERN);
        Assertions.assertTrue(fileInput.read().toList().isEmpty());
    }

    @Test
    void checkInputFactoryLogicTest() {
        InputFactory factory = new InputFactory();
        Assertions.assertTrue(factory.getInput(CORRECT_FILE_PATTERN) instanceof FileInput);
        Assertions.assertTrue(factory.getInput(CORRECT_URL) instanceof URLInput);
    }
}

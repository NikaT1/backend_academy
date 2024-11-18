package backend.academy.display.io;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleInput implements Input {

    private final Scanner input;

    public ConsoleInput(InputStream inputStream) {
        input = new Scanner(inputStream, StandardCharsets.UTF_8);
    }

    @Override
    public String read() {
        return input.nextLine();
    }

    @Override
    public void close() {
        input.close();
    }
}

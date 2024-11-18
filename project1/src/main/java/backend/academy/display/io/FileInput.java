package backend.academy.display.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileInput implements Input {

    private final Scanner input;

    public FileInput(String path) throws IOException {
        input = new Scanner(new File(path), StandardCharsets.UTF_8)
            .useDelimiter(System.lineSeparator());
    }

    @Override
    public String read() {
        StringBuilder builder = new StringBuilder();
        while (input.hasNext()) {
            builder.append(input.next());
        }
        return builder.toString();
    }

    @Override
    public void close() {
        input.close();
    }

}

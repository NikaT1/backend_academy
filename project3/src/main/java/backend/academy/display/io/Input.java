package backend.academy.display.io;

import java.io.IOException;
import java.util.stream.Stream;

public interface Input {
    Stream<String> read() throws IOException;
}

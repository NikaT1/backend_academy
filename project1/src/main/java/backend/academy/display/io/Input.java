package backend.academy.display.io;

import java.io.Closeable;

public interface Input extends Closeable {
    String read();
}

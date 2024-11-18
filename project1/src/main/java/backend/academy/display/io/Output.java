package backend.academy.display.io;

import java.io.Closeable;

public interface Output extends Closeable {

    void print(String msg);

    void println(String msg);

    void printf(String format, Object... args);

    void clear();
}

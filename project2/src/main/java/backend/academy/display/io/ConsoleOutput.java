package backend.academy.display.io;

import java.io.PrintStream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleOutput implements Output {
    private final PrintStream output;

    @Override
    public void print(String msg) {
        output.print(msg);
        output.flush();
    }

    @Override
    public void println(String msg) {
        output.println(msg);
    }

    @Override
    public void printf(String format, Object... args) {
        output.printf(format, args);
        output.flush();
    }

    @Override
    public void close() {
        output.close();
    }
}

package backend.academy.display;

import backend.academy.display.io.Input;
import backend.academy.display.io.InputFactory;
import backend.academy.log.LogRecord;
import backend.academy.log.LogRecordParser;
import java.io.IOException;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogInputService {

    private final InputFactory inputFactory;

    public Stream<LogRecord> readLogRecords(String path) {
        Input input = inputFactory.getInput(path);
        try {
            return input.read()
                .map(LogRecordParser::parse);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error during reading from path", e);
        }
    }
}

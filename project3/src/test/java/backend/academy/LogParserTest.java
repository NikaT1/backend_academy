package backend.academy;

import backend.academy.log.LogRecord;
import backend.academy.log.LogRecordParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.format.DateTimeParseException;

public class LogParserTest {

    private final static String CORRECT_ADDR = "93.180.71.3";
    private final static String CORRECT_LOG_RECORD =
        "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET product_1 HTTP/1.1\" 304 0 \"-\" \"Deb HTTP/1.3 (ubuntu10.21)\"";
    private final static String INCORRECT_DATE_LOG =
        "93.180.71.3 - - [17/May/2015: +0000] \"GET product_1 HTTP/1.1\" 304 0 \"-\" \"Deb HTTP/1.3 (ubuntu10.21)\"";
    private final static String INCORRECT_STRUCTURE_LOG =
        "93.180.71.3 - - \"GET product_1 HTTP/1.1\" 304 0 \"-\" \"Deb HTTP/1.3 (ubuntu10.21)\"";

    @Test
    void checkLogParserWithCorrectDataTest() {
        LogRecord logRecord = LogRecordParser.parse(CORRECT_LOG_RECORD);
        Assertions.assertEquals(CORRECT_ADDR, logRecord.remoteAddr());
    }

    @Test
    void checkLogParserWithIncorrectDateTest() {
        Assertions.assertThrows(DateTimeParseException.class, () -> LogRecordParser.parse(INCORRECT_DATE_LOG));
    }

    @Test
    void checkLogParserWithIncorrectLogStructureTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> LogRecordParser.parse(INCORRECT_STRUCTURE_LOG));
    }
}

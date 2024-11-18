package backend.academy;

import backend.academy.display.UserArgs;
import backend.academy.display.report.ReportFormat;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserArgsTest {

    private static final String[] CORRECT_ARGS =
        "-F adoc -f 2015-01-17T08:05:32 -t 2016-01-17T08:05:32 -p test".split(" ");
    private static final String[] ONLY_REQUIRED_CORRECT_ARGS = "-p test".split(" ");
    private static final String[] INCORRECT_DATE_ARGS = "-F adoc -f 2015-01-17 -p test".split(" ");
    private static final String[] INCORRECT_FORMAT_ARGS = "-F test -p test".split(" ");

    @Test
    void checkCorrectUserArgsInputTest() {
        UserArgs userArgs = new UserArgs();
        JCommander.newBuilder()
            .addObject(userArgs)
            .build()
            .parse(CORRECT_ARGS);
        Assertions.assertEquals(userArgs.path(), "test");
        Assertions.assertEquals(userArgs.format(), ReportFormat.ADOC);
        Assertions.assertEquals(userArgs.dateFrom(),
            LocalDateTime.parse("2015-01-17T08:05:32", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        Assertions.assertEquals(userArgs.dateTo(),
            LocalDateTime.parse("2016-01-17T08:05:32", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @Test
    void checkCorrectUserArgsWithOnlyRequiredValuesInputTest() {
        UserArgs userArgs = new UserArgs();
        JCommander.newBuilder()
            .addObject(userArgs)
            .build()
            .parse(ONLY_REQUIRED_CORRECT_ARGS);
        Assertions.assertEquals(userArgs.path(), "test");
        Assertions.assertNull(userArgs.format());
        Assertions.assertNull(userArgs.dateFrom());
        Assertions.assertNull(userArgs.dateTo());
    }

    @Test
    void checkIncorrectDateUserArgsInputTest() {
        UserArgs userArgs = new UserArgs();
        Assertions.assertThrows(ParameterException.class, () -> JCommander.newBuilder()
            .addObject(userArgs)
            .build()
            .parse(INCORRECT_DATE_ARGS));
    }

    @Test
    void checkIncorrectFormatUserArgsInputTest() {
        UserArgs userArgs = new UserArgs();
        Assertions.assertThrows(ParameterException.class, () -> JCommander.newBuilder()
            .addObject(userArgs)
            .build()
            .parse(INCORRECT_FORMAT_ARGS));
    }
}

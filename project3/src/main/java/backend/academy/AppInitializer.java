package backend.academy;

import backend.academy.display.UserArgs;
import backend.academy.display.UserArgsValidator;
import backend.academy.log.LogField;
import backend.academy.log.LogRecord;
import com.beust.jcommander.JCommander;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class AppInitializer {

    public static UserArgs loadUserArgs(String[] args) {
        UserArgs userArgs = new UserArgs();
        JCommander.newBuilder()
            .addObject(userArgs)
            .build()
            .parse(args);
        UserArgsValidator.checkUserArgsParams(userArgs);
        return userArgs;
    }

    public static List<Predicate<LogRecord>> prepareAndGetFilters(UserArgs userArgs) {
        List<Predicate<LogRecord>> predicates = new ArrayList<>();
        predicates.add(getDateFilter(userArgs.dateFrom(), userArgs.dateTo()));
        if (userArgs.logField() != null) {
            predicates.add(getLogFieldFilter(userArgs.logField(), userArgs.valuePattern()));
        }
        return predicates;
    }

    public static Predicate<LogRecord> getDateFilter(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (r) -> {
            boolean isActual = true;
            if (dateFrom != null) {
                isActual = dateFrom.isBefore(r.timeLocal().toLocalDateTime());
            }
            if (dateTo != null) {
                isActual = isActual && dateTo.isAfter(r.timeLocal().toLocalDateTime());
            }
            return isActual;
        };
    }

    public static Predicate<LogRecord> getLogFieldFilter(LogField field, Pattern pattern) {
        return (r) -> switch (field) {
            case REMOTE_ADDR -> pattern.matcher(r.remoteAddr()).find();
            case REMOTE_USER -> pattern.matcher(r.remoteUser()).find();
            case TIME_LOCAL -> pattern.matcher(r.timeLocal().toString()).find();
            case HTTP_METHOD -> pattern.matcher(r.request().httpMethod()).find();
            case RESOURCE -> pattern.matcher(r.request().resource()).find();
            case HTTP_VERSION -> pattern.matcher(r.request().httpVersion()).find();
            case STATUS -> pattern.matcher(r.status()).find();
            case BODY_BYTES_SENT -> pattern.matcher(r.bodyBytesSent().toString()).find();
            case HTTP_REFERER -> pattern.matcher(r.httpReferer()).find();
            case HTTP_USER_AGENT -> pattern.matcher(r.httpUserAgent()).find();
        };
    }
}

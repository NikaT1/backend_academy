package backend.academy.log;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogRecordParser {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
    private static final Pattern LOG_PATTERN = Pattern.compile("^([\\w\\.:]*) - ([\\w-]*) \\[(.*)\\] \"(\\w{3,7})"
        + " ([/\\w]*) ([/\\w\\.]*)\" (\\d{3}) (\\d*) \"(.*)\" \"(.*)\"$");
    private static final Integer REMOTE_ADDR_GROUP = 1;
    private static final Integer REMOTE_USER_GROUP = 2;
    private static final Integer TIME_LOCAL_GROUP = 3;
    private static final Integer HTTP_METHOD_GROUP = 4;
    private static final Integer RESOURCE_GROUP = 5;
    private static final Integer HTTP_VERSION_GROUP = 6;
    private static final Integer STATUS_GROUP = 7;
    private static final Integer BODY_BYTES_SENT_GROUP = 8;
    private static final Integer HTTP_REFERER_GROUP = 9;
    private static final Integer HTTP_USER_AGENT_GROUP = 10;

    @SuppressFBWarnings("NAB_NEEDLESS_BOXING_VALUEOF")
    public static LogRecord parse(String logRecord) {
        Matcher matcher = LOG_PATTERN.matcher(logRecord);
        if (matcher.find()) {
            return LogRecord.builder()
                .remoteAddr(matcher.group(REMOTE_ADDR_GROUP))
                .bodyBytesSent(Long.parseLong(matcher.group(BODY_BYTES_SENT_GROUP)))
                .httpUserAgent(matcher.group(HTTP_USER_AGENT_GROUP))
                .httpReferer(matcher.group(HTTP_REFERER_GROUP))
                .request(new Request(
                    matcher.group(HTTP_METHOD_GROUP),
                    matcher.group(RESOURCE_GROUP),
                    matcher.group(HTTP_VERSION_GROUP)
                ))
                .status(matcher.group(STATUS_GROUP))
                .remoteUser(matcher.group(REMOTE_USER_GROUP))
                .timeLocal(OffsetDateTime.parse(matcher.group(TIME_LOCAL_GROUP), DATE_TIME_FORMATTER))
                .build();
        }
        throw new IllegalArgumentException("Incorrect log record format");
    }
}

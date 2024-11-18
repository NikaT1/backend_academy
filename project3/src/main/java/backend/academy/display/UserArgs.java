package backend.academy.display;

import backend.academy.display.report.ReportFormat;
import backend.academy.log.LogField;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserArgs {

    @Parameter(names = {"--path", "-p"}, required = true,
        description = "Path to one or more NGINX log files - local path or URL")
    private String path;
    @Parameter(names = {"--from", "-f"}, description = "Time parameter 'from'", converter = DateISO8601Converter.class)
    private LocalDateTime dateFrom;
    @Parameter(names = {"--to", "-t"}, description = "Time parameter 'to'", converter = DateISO8601Converter.class)
    private LocalDateTime dateTo;
    @Parameter(names = {"-F", "--format"}, description = "Report format", converter = ReportFormatConverter.class)
    private ReportFormat format;
    @Parameter(names = {"--filter-field"}, description = "Field for filter", converter = FilterFieldConverter.class)
    private LogField logField;
    @Parameter(names = {"--filter-value"}, description = "Value filter", converter = FilterValueConverter.class)
    private Pattern valuePattern;

    static class DateISO8601Converter implements IStringConverter<LocalDateTime> {
        @Override
        public LocalDateTime convert(String value) {
            try {
                return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException e) {
                throw new ParameterException("Invalid date format - " + value, e);
            }
        }
    }

    static class ReportFormatConverter implements IStringConverter<ReportFormat> {
        @Override
        public ReportFormat convert(String value) {
            try {
                return ReportFormat.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ParameterException("Invalid report format - " + value, e);
            }
        }
    }

    static class FilterFieldConverter implements IStringConverter<LogField> {
        @Override
        public LogField convert(String value) {
            try {
                return LogField.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ParameterException("Invalid filter-field format - " + value, e);
            }
        }
    }

    static class FilterValueConverter implements IStringConverter<Pattern> {
        @Override
        public Pattern convert(String value) {
            try {
                return Pattern.compile(value);
            } catch (PatternSyntaxException e) {
                throw new ParameterException("Invalid filter-value format - " + value, e);
            }
        }
    }
}

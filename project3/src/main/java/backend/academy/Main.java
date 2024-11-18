package backend.academy;

import backend.academy.display.LogInputService;
import backend.academy.display.UserArgs;
import backend.academy.display.UserOutputService;
import backend.academy.display.io.ConsoleOutput;
import backend.academy.display.io.InputFactory;
import backend.academy.display.io.Output;
import backend.academy.display.report.ReportRender;
import backend.academy.display.report.ReportRenderFactory;
import backend.academy.log.LogRecord;
import backend.academy.stat.Statistic;
import backend.academy.stat.StatisticManager;
import backend.academy.stat.statistics.CommonStatistic;
import backend.academy.stat.statistics.HttpCodesStatistic;
import backend.academy.stat.statistics.ResourseStatistic;
import backend.academy.stat.statistics.UserAgentStatistic;
import com.beust.jcommander.ParameterException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import static backend.academy.AppInitializer.loadUserArgs;

@UtilityClass
@Log4j2
public class Main {
    public static void main(String[] args) {
        try (Output consoleOutput = new ConsoleOutput(System.out)) {
            UserOutputService userOutputService = new UserOutputService(consoleOutput);
            init(userOutputService, args);
        } catch (IOException e) {
            log.error("Error occurred during the input/output process: " + e);
        }
    }

    public static void init(UserOutputService output, String[] args) {
        try {
            UserArgs userArgs = loadUserArgs(args);
            LogInputService logInputService = new LogInputService(new InputFactory());
            StatisticManager statisticManager = new StatisticManager()
                .addStatistic(new CommonStatistic(userArgs.dateFrom(), userArgs.dateTo()))
                .addStatistic(new HttpCodesStatistic())
                .addStatistic(new UserAgentStatistic())
                .addStatistic(new ResourseStatistic());

            Stream<LogRecord> stream = logInputService.readLogRecords(userArgs.path());
            List<Statistic> statistics = statisticManager.prepareAllStatistic(stream,
                AppInitializer.prepareAndGetFilters(userArgs));

            ReportRender reportRender = ReportRenderFactory.getReportRender(userArgs.format());
            output.sendReport(reportRender.render(statistics));
        } catch (ParameterException e) {
            log.error("Invalid user params specified: " + e);
            output.sendUserArgsError(e.getMessage());
        } catch (DateTimeParseException e) {
            log.error("Invalid date format: " + e);
            output.sendExecutionError(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Error during execution: " + e);
            output.sendExecutionError(e.getMessage());
        }
    }
}

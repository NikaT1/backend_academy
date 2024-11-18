package backend.academy;

import backend.academy.display.LogInputService;
import backend.academy.display.UserArgs;
import backend.academy.display.io.InputFactory;
import backend.academy.display.report.ReportRender;
import backend.academy.display.report.ReportRenderFactory;
import backend.academy.log.LogRecord;
import backend.academy.stat.statistics.CommonStatistic;
import backend.academy.stat.statistics.HttpCodesStatistic;
import backend.academy.stat.statistics.ResourseStatistic;
import backend.academy.stat.Statistic;
import backend.academy.stat.StatisticManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Stream;
import static backend.academy.AppInitializer.loadUserArgs;

public class AppTest {

    private static final String[] CORRECT_ARGS_WITHOUT_DATE = "-F adoc -p **/resources/correct_file.txt".split(" ");
    private static final String[] CORRECT_ARGS_MIN_DATE =
        "-F adoc -f 2015-05-17T09:00:00 -p **/resources/correct_file.txt".split(" ");
    private static final String[] CORRECT_ARGS_MAX_DATE =
        "-F adoc -t 2015-05-17T10:00:00 -p **/resources/correct_file.txt".split(" ");
    private static final String[] CORRECT_ARGS_FULL_DATE =
        "-F adoc -f 2015-05-17T09:00:00 -t 2015-05-17T10:00:00 -p **/resources/correct_file.txt".split(" ");
    private static final String[] CORRECT_ARGS_FILTER =
        "-F adoc -p **/resources/correct_file.txt --filter-field http_user_agent --filter-value 10.17".split(" ");

    @Test
    void checkAppLogicCommonStatisticWithoutDateTest() {
        UserArgs userArgs = loadUserArgs(CORRECT_ARGS_WITHOUT_DATE);
        List<Statistic> statistics = prepareStatistic(new CommonStatistic(userArgs.dateFrom(), userArgs.dateTo()),
            userArgs);

        ReportRender reportRender = ReportRenderFactory.getReportRender(userArgs.format());
        Assertions.assertTrue(reportRender.render(statistics).replace(System.lineSeparator(), "")
            .contains("| Начальная дата | - | Конечная дата | - | Количество запросов | 3 " +
                "| Средний размер ответа | 100b | 95p размера ответа | 100b "));
    }

    @Test
    void checkAppLogicHttpCodeStatisticWithoutDateTest() {
        UserArgs userArgs = loadUserArgs(CORRECT_ARGS_WITHOUT_DATE);
        List<Statistic> statistics = prepareStatistic(new HttpCodesStatistic(), userArgs);
        ReportRender reportRender = ReportRenderFactory.getReportRender(userArgs.format());
        Assertions.assertTrue(reportRender.render(statistics).replace(System.lineSeparator(), "")
            .contains("| 200 | 2 | 201 | 1"));
    }

    @Test
    void checkAppLogicResourceStatisticWithoutDateTest() {
        UserArgs userArgs = loadUserArgs(CORRECT_ARGS_WITHOUT_DATE);
        List<Statistic> statistics = prepareStatistic(new ResourseStatistic(), userArgs);

        ReportRender reportRender = ReportRenderFactory.getReportRender(userArgs.format());
        Assertions.assertTrue(reportRender.render(statistics).replace(System.lineSeparator(), "")
            .contains("| /downloads/product_1 | 3"));
    }

    @Test
    void checkAppLogicCommonStatisticWithMinDateTest() {
        UserArgs userArgs = loadUserArgs(CORRECT_ARGS_MIN_DATE);
        List<Statistic> statistics = prepareStatistic(new CommonStatistic(userArgs.dateFrom(), userArgs.dateTo()),
            userArgs);

        ReportRender reportRender = ReportRenderFactory.getReportRender(userArgs.format());
        Assertions.assertTrue(reportRender.render(statistics).replace(System.lineSeparator(), "")
            .contains("| Количество запросов | 2"));
    }

    @Test
    void checkAppLogicCommonStatisticWithMaxDateTest() {
        UserArgs userArgs = loadUserArgs(CORRECT_ARGS_MAX_DATE);
        List<Statistic> statistics = prepareStatistic(new CommonStatistic(userArgs.dateFrom(), userArgs.dateTo()),
            userArgs);

        ReportRender reportRender = ReportRenderFactory.getReportRender(userArgs.format());
        Assertions.assertTrue(reportRender.render(statistics).replace(System.lineSeparator(), "")
            .contains("| Количество запросов | 2"));
    }

    @Test
    void checkAppLogicCommonStatisticWithFullDateTest() {
        UserArgs userArgs = loadUserArgs(CORRECT_ARGS_FULL_DATE);
        List<Statistic> statistics = prepareStatistic(new CommonStatistic(userArgs.dateFrom(), userArgs.dateTo()),
            userArgs);

        ReportRender reportRender = ReportRenderFactory.getReportRender(userArgs.format());
        Assertions.assertTrue(reportRender.render(statistics).replace(System.lineSeparator(), "")
            .contains("| Количество запросов | 1"));
    }

    @Test
    void checkAppLogicCommonStatisticWithValueFilterTest() {
        UserArgs userArgs = loadUserArgs(CORRECT_ARGS_FILTER);
        List<Statistic> statistics = prepareStatistic(new CommonStatistic(userArgs.dateFrom(), userArgs.dateTo()),
            userArgs);

        ReportRender reportRender = ReportRenderFactory.getReportRender(userArgs.format());
        Assertions.assertTrue(reportRender.render(statistics).replace(System.lineSeparator(), "")
            .contains("| Количество запросов | 1"));
    }

    private List<Statistic> prepareStatistic(Statistic statistic, UserArgs userArgs) {
        LogInputService logInputService = new LogInputService(new InputFactory());
        StatisticManager statisticManager = new StatisticManager()
            .addStatistic(statistic);
        Stream<LogRecord> stream = logInputService.readLogRecords(userArgs.path());
        return statisticManager.prepareAllStatistic(stream, AppInitializer.prepareAndGetFilters(userArgs));
    }
}

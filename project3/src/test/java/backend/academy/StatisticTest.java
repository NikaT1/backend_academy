package backend.academy;

import backend.academy.log.LogRecord;
import backend.academy.log.Request;
import backend.academy.stat.statistics.CommonStatistic;
import backend.academy.stat.statistics.HttpCodesStatistic;
import backend.academy.stat.statistics.ResourseStatistic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StatisticTest {

    private static LogRecord logRecord;

    @BeforeAll
    static void prepareData() {
        logRecord = LogRecord.builder()
            .bodyBytesSent(1L)
            .status("200")
            .request(new Request("GET", "test", "1"))
            .build();
    }

    @Test
    void checkCommonStatisticTest() {
        CommonStatistic statistic = new CommonStatistic(null, null);
        statistic.updateStatistic(logRecord);
        Assertions.assertEquals(statistic.getStatistic().get(3).get(1).value(), "1"); //количество запросов
        Assertions.assertEquals(statistic.getStatistic().get(4).get(1).value(), "1b"); //ср размер ответа

        statistic.updateStatistic(logRecord);
        Assertions.assertEquals(statistic.getStatistic().get(3).get(1).value(), "2"); //количество запросов
        Assertions.assertEquals(statistic.getStatistic().get(4).get(1).value(), "1b"); //ср размер ответа
    }

    @Test
    void checkHttpCodesStatisticTest() {
        HttpCodesStatistic statistic = new HttpCodesStatistic();
        statistic.updateStatistic(logRecord);
        Assertions.assertEquals(statistic.getStatistic().get(1).get(0).value(), "200"); //код ответа
        Assertions.assertEquals(statistic.getStatistic().get(1).get(1).value(), "1"); //кол-во

        statistic.updateStatistic(logRecord);
        Assertions.assertEquals(statistic.getStatistic().get(1).get(0).value(), "200"); //код ответа
        Assertions.assertEquals(statistic.getStatistic().get(1).get(1).value(), "2"); //кол-во
    }

    @Test
    void checkResourceStatisticTest() {
        ResourseStatistic statistic = new ResourseStatistic();
        statistic.updateStatistic(logRecord);
        Assertions.assertEquals(statistic.getStatistic().get(1).get(0).value(), "test"); //ресурс
        Assertions.assertEquals(statistic.getStatistic().get(1).get(1).value(), "1"); //кол-во

        statistic.updateStatistic(logRecord);
        Assertions.assertEquals(statistic.getStatistic().get(1).get(0).value(), "test"); //ресурс
        Assertions.assertEquals(statistic.getStatistic().get(1).get(1).value(), "2"); //кол-во
    }
}

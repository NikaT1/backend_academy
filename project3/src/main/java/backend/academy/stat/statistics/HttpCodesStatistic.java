package backend.academy.stat.statistics;

import backend.academy.log.LogRecord;
import backend.academy.stat.SortableByIntCountStatistic;
import backend.academy.stat.StatisticNames;
import backend.academy.stat.metrics.Metric;
import java.util.ArrayList;
import java.util.List;

public class HttpCodesStatistic extends SortableByIntCountStatistic {

    public HttpCodesStatistic() {
        super(StatisticNames.CODE_STATISTIC_NAME);
    }

    @Override
    public void updateStatistic(LogRecord logRecord) {
        String statusCode = logRecord.status();
        incrementCountStatistic(statusCode);
    }

    @Override
    public List<List<Metric>> getStatistic() {
        List<List<Metric>> statistics = new ArrayList<>();
        statistics.add(prepareMetricRow(StatisticNames.CODE_HEADER, StatisticNames.COUNT_HEADER));
        prepareAllSortableStatisticRows(statistics);
        return statistics;
    }
}

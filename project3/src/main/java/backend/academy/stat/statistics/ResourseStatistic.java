package backend.academy.stat.statistics;

import backend.academy.log.LogRecord;
import backend.academy.stat.SortableByIntCountStatistic;
import backend.academy.stat.StatisticNames;
import backend.academy.stat.metrics.Metric;
import java.util.ArrayList;
import java.util.List;

public class ResourseStatistic extends SortableByIntCountStatistic {

    public ResourseStatistic() {
        super(StatisticNames.RESOURCE_STATISTIC_NAME);
    }

    @Override
    public void updateStatistic(LogRecord logRecord) {
        String resource = logRecord.request().resource();
        incrementCountStatistic(resource);
    }

    @Override
    public List<List<Metric>> getStatistic() {
        List<List<Metric>> statistics = new ArrayList<>();
        statistics.add(prepareMetricRow(StatisticNames.RESOURCE_HEADER, StatisticNames.COUNT_HEADER));
        prepareAllSortableStatisticRows(statistics);
        return statistics;
    }
}

package backend.academy.stat.statistics;

import backend.academy.log.LogRecord;
import backend.academy.stat.SortableByIntCountStatistic;
import backend.academy.stat.StatisticNames;
import backend.academy.stat.metrics.Metric;
import java.util.ArrayList;
import java.util.List;

public class UserAgentStatistic extends SortableByIntCountStatistic {

    public UserAgentStatistic() {
        super(StatisticNames.USER_AGENT_STATISTIC_NAME);
    }

    @Override
    public void updateStatistic(LogRecord logRecord) {
        String userAgent = logRecord.httpUserAgent();
        incrementCountStatistic(userAgent);
    }

    @Override
    public List<List<Metric>> getStatistic() {
        List<List<Metric>> statistics = new ArrayList<>();
        statistics.add(prepareMetricRow(StatisticNames.USER_AGENT_HEADER, StatisticNames.COUNT_HEADER));
        prepareAllSortableStatisticRows(statistics);
        return statistics;
    }
}

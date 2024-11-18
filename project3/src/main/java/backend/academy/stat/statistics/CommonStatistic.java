package backend.academy.stat.statistics;

import backend.academy.log.LogRecord;
import backend.academy.stat.Statistic;
import backend.academy.stat.StatisticNames;
import backend.academy.stat.metrics.Metric;
import com.google.common.math.Quantiles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommonStatistic extends Statistic {

    private static final int PERCENTILE_INDEX = 95;
    private final LocalDateTime startDate;
    private final LocalDateTime finishDate;
    private final List<Long> bodyBytesSentList;
    private Long bodyBytesSentSum = 0L;

    public CommonStatistic(LocalDateTime startDate, LocalDateTime finishDate) {
        super(StatisticNames.COMMON_STATISTIC_NAME);
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.bodyBytesSentList = new ArrayList<>();
    }

    @Override
    public void updateStatistic(LogRecord logRecord) {
        bodyBytesSentList.add(logRecord.bodyBytesSent());
        bodyBytesSentSum += logRecord.bodyBytesSent();
    }

    @Override
    public List<List<Metric>> getStatistic() {
        List<List<Metric>> statistics = new ArrayList<>();
        statistics.add(prepareMetricRow(StatisticNames.METRIC_HEADER, StatisticNames.VALUE_HEADER));
        statistics.add(prepareMetricRow(StatisticNames.START_DATE_PARAM, startDate));
        statistics.add(prepareMetricRow(StatisticNames.FINISH_DATE_PARAM, finishDate));
        statistics.add(prepareMetricRow(StatisticNames.COUNT_OF_REQUEST_METRIC, bodyBytesSentList.size()));
        statistics.add(
            prepareMetricRow(StatisticNames.RESPONSE_AVG_METRIC, addPrefix(getAvgSizeWithPrefix())));
        statistics.add(prepareMetricRow(StatisticNames.RESPONSE_95P_METRIC, addPrefix(getPercentileWithPrefix())));
        return statistics;
    }

    private long getAvgSizeWithPrefix() {
        if (bodyBytesSentList.isEmpty()) {
            return 0;
        }
        return bodyBytesSentSum / bodyBytesSentList.size();
    }

    private long getPercentileWithPrefix() {
        if (bodyBytesSentList.isEmpty()) {
            return 0;
        }
        return (long) Quantiles.percentiles().index(PERCENTILE_INDEX).compute(bodyBytesSentList);
    }

    private String addPrefix(long value) {
        return value + StatisticNames.VALUE_PREFIX;
    }
}

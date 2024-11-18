package backend.academy.stat;

import backend.academy.log.LogRecord;
import backend.academy.stat.metrics.Metric;
import backend.academy.stat.metrics.MetricType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Statistic {
    private final String name;

    public abstract void updateStatistic(LogRecord logRecord);

    public abstract List<List<Metric>> getStatistic();

    @SafeVarargs
    protected final <T> List<Metric> prepareMetricRow(T... values) {
        List<Metric> row = new ArrayList<>(values.length);
        for (T val : values) {
            row.add(prepareMetric(val));
        }
        return row;
    }

    private <T> Metric prepareMetric(T value) {
        Metric metric;
        if (value == null) {
            metric = new Metric(MetricType.STRING, "-");
        } else if (value instanceof LocalDateTime) {
            metric = new Metric(MetricType.LOCAL_DATE_TIME, String.valueOf(value));
        } else if (value instanceof Long) {
            metric = new Metric(MetricType.LONG, String.valueOf(value));
        } else {
            metric = new Metric(MetricType.STRING, String.valueOf(value));
        }
        return metric;
    }
}

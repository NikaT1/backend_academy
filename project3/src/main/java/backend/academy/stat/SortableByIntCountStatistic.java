package backend.academy.stat;

import backend.academy.stat.metrics.Metric;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SortableByIntCountStatistic extends Statistic {
    private final Map<String, Integer> countValueMap;

    public SortableByIntCountStatistic(String name) {
        super(name);
        this.countValueMap = new HashMap<>();
    }

    public void incrementCountStatistic(String key) {
        countValueMap.merge(key, 1, Integer::sum);
    }

    protected void prepareAllSortableStatisticRows(List<List<Metric>> statistics) {
        countValueMap.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(e -> statistics.add(prepareMetricRow(e.getKey(), e.getValue())));
    }
}

package backend.academy.stat;

import backend.academy.log.LogRecord;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jspecify.annotations.NonNull;

public class StatisticManager {
    private final List<Statistic> statistics = new ArrayList<>();
    private final Set<String> statisticNames = new HashSet<>();

    public final List<Statistic> prepareAllStatistic(
        Stream<LogRecord> recordStream,
        @NonNull List<Predicate<LogRecord>> filters
    ) {
        Stream<LogRecord> recordStreamWithFilter = recordStream;
        try {
            for (Predicate<LogRecord> filter : filters) {
                if (filter != null) {
                    recordStreamWithFilter = recordStreamWithFilter.filter(filter);
                }
            }
            recordStreamWithFilter.forEach(r -> statistics.forEach(s -> s.updateStatistic(r)));
        } catch (Exception e) {
            recordStreamWithFilter.close();
            throw new RuntimeException("Some error occurred during statistics calc", e);
        }
        return statistics;
    }

    public StatisticManager addStatistic(Statistic statistic) {
        if (statisticNames.add(statistic.name())) {
            statistics.add(statistic);
        }
        return this;
    }

    public boolean deleteStatistic(Statistic statistic) {
        statisticNames.remove(statistic.name());
        return statistics.remove(statistic);
    }
}

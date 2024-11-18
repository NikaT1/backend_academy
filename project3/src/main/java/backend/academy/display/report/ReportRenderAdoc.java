package backend.academy.display.report;

import backend.academy.stat.Statistic;
import backend.academy.stat.metrics.Metric;
import java.util.List;

public class ReportRenderAdoc extends BaseReportRender implements ReportRender {
    private static final String TABLE_OPTIONS = "[options='header']" + System.lineSeparator();
    private static final String TABLE_DELIMITER = "|====" + System.lineSeparator();

    @Override
    public String render(List<Statistic> statistics) {
        StringBuilder builder = new StringBuilder();
        statistics.forEach(s -> {
            builder.append("== ").append(s.name())
                .append(System.lineSeparator())
                .append(TABLE_OPTIONS)
                .append(TABLE_DELIMITER);
            renderTable(s.getStatistic(), builder);
            builder.append(TABLE_DELIMITER);
        });
        return builder.toString();
    }

    private void renderTable(List<List<Metric>> table, StringBuilder builder) {
        renderHeadline(table.get(0), builder);
        for (int i = 1; i < table.size(); i++) {
            List<Metric> row = table.get(i);
            row.forEach(el -> builder.append("| ").append(renderMetric(el)).append(' '));
            builder.append(System.lineSeparator());
        }
    }

    private void renderHeadline(List<Metric> headers, StringBuilder builder) {
        headers.forEach(h -> builder.append("| ").append(renderMetric(h)).append(' '));
        builder.append(System.lineSeparator());
    }
}

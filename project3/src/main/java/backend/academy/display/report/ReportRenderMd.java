package backend.academy.display.report;

import backend.academy.stat.Statistic;
import backend.academy.stat.metrics.Metric;
import java.util.List;

public class ReportRenderMd extends BaseReportRender implements ReportRender {

    @Override
    public String render(List<Statistic> statistics) {
        StringBuilder builder = new StringBuilder();
        statistics.forEach(s -> {
            builder.append("### ")
                .append(s.name()).append(System.lineSeparator());
            renderTable(s.getStatistic(), builder);
            builder.append(System.lineSeparator());
        });
        return builder.toString();
    }

    private void renderTable(List<List<Metric>> table, StringBuilder builder) {
        renderHeadline(table.get(0), builder);
        for (int i = 1; i < table.size(); i++) {
            List<Metric> row = table.get(i);
            row.forEach(el -> builder.append("| ").append(renderMetric(el)).append(' '));
            builder.append('|').append(System.lineSeparator());
        }
    }

    private void renderHeadline(List<Metric> headers, StringBuilder builder) {
        builder.append("| ");
        headers.forEach(h -> builder.append(renderMetric(h)).append(" | "));
        builder.append(System.lineSeparator())
            .append("| -- ".repeat(headers.size()))
            .append('|').append(System.lineSeparator());
    }
}

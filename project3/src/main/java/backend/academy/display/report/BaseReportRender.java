package backend.academy.display.report;

import backend.academy.stat.metrics.Metric;

public abstract class BaseReportRender {
    protected String renderMetric(Metric metric) {
        return switch (metric.metricType()) {
            case STRING, LONG -> metric.value();
            case LOCAL_DATE_TIME -> metric.value().replace('T', ' ');
        };
    }
}

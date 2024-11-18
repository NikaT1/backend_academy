package backend.academy.display.report;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ReportRenderFactory {

    public static ReportRender getReportRender(ReportFormat format) {
        if (format == null) {
            return new ReportRenderMd(); // формат по-умолчанию
        }
        return switch (format) {
            case MARKDOWN -> new ReportRenderMd();
            case ADOC -> new ReportRenderAdoc();
        };
    }
}

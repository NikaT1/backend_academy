package backend.academy.display.report;

import backend.academy.stat.Statistic;
import java.util.List;

public interface ReportRender {

    String render(List<Statistic> statistics);

}

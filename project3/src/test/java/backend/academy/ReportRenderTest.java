package backend.academy;

import backend.academy.display.report.ReportFormat;
import backend.academy.display.report.ReportRenderAdoc;
import backend.academy.display.report.ReportRenderFactory;
import backend.academy.display.report.ReportRenderMd;
import backend.academy.stat.Statistic;
import backend.academy.stat.metrics.Metric;
import backend.academy.stat.metrics.MetricType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportRenderTest {

    private static final List<List<Metric>> data = new ArrayList<>();

    @Mock
    private Statistic statistic;

    @BeforeAll
    static void prepareData() {
        data.add(new ArrayList<>(List.of(new Metric(MetricType.STRING, "a"),
            new Metric(MetricType.STRING, "b"),
            new Metric(MetricType.STRING, "c"))));
        data.add(new ArrayList<>(List.of(new Metric(MetricType.STRING, "d"),
            new Metric(MetricType.STRING, "e"),
            new Metric(MetricType.STRING, "f"))));
    }

    @Test
    void checkReportRenderAdocTest() {
        when(statistic.getStatistic()).thenReturn(data);
        when(statistic.name()).thenReturn("test");
        ReportRenderAdoc reportRenderAdoc = new ReportRenderAdoc();
        String report = reportRenderAdoc.render(List.of(statistic));
        Assertions.assertEquals("== test[options='header']|====| a | b | c | d | e | f " +
            "|====", report.replace(System.lineSeparator(), ""));
    }

    @Test
    void checkReportRenderMdTest() {
        when(statistic.getStatistic()).thenReturn(data);
        when(statistic.name()).thenReturn("test");
        ReportRenderMd reportRenderMd = new ReportRenderMd();
        String report = reportRenderMd.render(List.of(statistic));
        Assertions.assertEquals("### test| a | b | c | | -- | -- | -- |" +
            "| d | e | f |", report.replace(System.lineSeparator(), ""));
    }

    @ParameterizedTest
    @MethodSource("provideFormatArgs")
    void checkReportRenderFactoryLogicTest(ReportFormat format, Class<?> type) {
        Assertions.assertTrue(type.isInstance(ReportRenderFactory.getReportRender(format)));
    }

    private static Stream<Arguments> provideFormatArgs() {
        return Stream.of(
            Arguments.of(ReportFormat.ADOC, ReportRenderAdoc.class),
            Arguments.of(ReportFormat.MARKDOWN, ReportRenderMd.class),
            Arguments.of(null, ReportRenderMd.class)
        );
    }
}

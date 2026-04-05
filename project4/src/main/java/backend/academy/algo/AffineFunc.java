package backend.academy.algo;

import backend.academy.image.model.Point;
import java.util.function.BiFunction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import static backend.academy.random.RandomUtil.getRandomDoubleInRange;
import static backend.academy.random.RandomUtil.getRandomShortInRange;

@RequiredArgsConstructor
public class AffineFunc {
    private static final double MIN_VALUE = -1.0;
    private static final double MAX_VALUE = 1.0;
    private static final short MIN_COLOR = 0;
    private static final short MAX_COLOR = 255;
    private static final BiFunction<Double, Double, Boolean> COEF_CONDITION = (a, b) -> a * b + a * b > 1;
    private final double c;
    private final double f;
    private double a;
    private double b;
    private double d;
    private double e;
    @Getter
    private final short colorR;
    @Getter
    private final short colorG;
    @Getter
    private final short colorB;

    public AffineFunc() {
        colorR = getRandomShortInRange(MIN_COLOR, MAX_COLOR);
        colorG = getRandomShortInRange(MIN_COLOR, MAX_COLOR);
        colorB = getRandomShortInRange(MIN_COLOR, MAX_COLOR);

        c = getRandomDoubleInRange(MIN_VALUE, MAX_VALUE);
        f = getRandomDoubleInRange(MIN_VALUE, MAX_VALUE);

        do {
            DoublePair numberPair1 = generateDoublePairWithCondition(COEF_CONDITION);
            DoublePair numberPair2 = generateDoublePairWithCondition(COEF_CONDITION);

            a = numberPair1.numb1;
            d = numberPair1.numb2;
            b = numberPair2.numb1;
            e = numberPair2.numb2;
        } while (a * a + b * b + d * d + e * e >= 1 + Math.pow((a * e - b * d), 2));
    }

    private DoublePair generateDoublePairWithCondition(BiFunction<Double, Double, Boolean> condition) {
        double numb1;
        double numb2;

        do {
            numb1 = getRandomDoubleInRange(MIN_VALUE, MAX_VALUE);
            numb2 = getRandomDoubleInRange(MIN_VALUE, MAX_VALUE);
        } while (condition.apply(numb1, numb2));
        return new DoublePair(numb1, numb2);
    }

    public Point apply(Point point) {
        return Point.builder()
            .x(point.x() * a + point.y() * b + c)
            .y(point.x() * d + point.y() * e + f)
            .build();
    }

    private record DoublePair(double numb1, double numb2) {
    }
}

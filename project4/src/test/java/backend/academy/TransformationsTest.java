package backend.academy;

import backend.academy.algo.transformation.Transformation;
import backend.academy.algo.transformation.TransformationFactory;
import backend.academy.algo.transformation.TransformationType;
import backend.academy.image.model.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class TransformationsTest {

    @ParameterizedTest
    @MethodSource("TransformationsDataProvider")
    void checkTransformationAlgoTest(TransformationType type, double x, double y) {
        Transformation transformation = TransformationFactory.createTransformation(type);
        Point point = transformation.apply(new Point(0.3, 0.5));
        Assertions.assertEquals(x, point.x(), 0.01d);
        Assertions.assertEquals(y, point.y(), 0.01d);
    }

    public static Stream<Arguments> TransformationsDataProvider() {
        return Stream.of(
            Arguments.of(TransformationType.DIAMOND, 0.715, 0.283),
            Arguments.of(TransformationType.FISHEYE, 0.631, 0.379),
            Arguments.of(TransformationType.HEART, 0.329, -0.480),
            Arguments.of(TransformationType.LINEAR, 0.300, 0.500),
            Arguments.of(TransformationType.SINUSOIDAL, 0.295, 0.479),
            Arguments.of(TransformationType.SPHERICAL, 0.882, 1.470),
            Arguments.of(TransformationType.SWIRL, -0.371, 0.449)
        );
    }
}

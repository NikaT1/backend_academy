package backend.academy.algo.transformation;

import backend.academy.image.model.Point;

public class SinusoidalTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        return Point.builder()
            .x(Math.sin(point.x()))
            .y(Math.sin(point.y()))
            .build();
    }
}

package backend.academy.algo.transformation;

import backend.academy.image.model.Point;

public class FisheyeTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double r = 2.0 / (1.0 + Math.sqrt(point.x() * point.x() + point.y() * point.y()));
            return Point.builder()
            .x(r * point.y())
            .y(r * point.x())
            .build();
    }
}

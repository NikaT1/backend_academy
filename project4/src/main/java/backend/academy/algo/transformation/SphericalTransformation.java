package backend.academy.algo.transformation;

import backend.academy.image.model.Point;

public class SphericalTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double r2 = point.x() * point.x() + point.y() * point.y();
        return Point.builder()
            .x(point.x() / r2)
            .y(point.y() / r2)
            .build();
    }
}

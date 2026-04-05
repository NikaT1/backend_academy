package backend.academy.algo.transformation;

import backend.academy.image.model.Point;

public class SwirlTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double r2 = point.x() * point.x() + point.y() * point.y();
        return Point.builder()
            .x(point.x() * Math.sin(r2) - point.y() * Math.cos(r2))
            .y(point.x() * Math.cos(r2) + point.y() * Math.sin(r2))
            .build();
    }
}

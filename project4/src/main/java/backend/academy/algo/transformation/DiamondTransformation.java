package backend.academy.algo.transformation;

import backend.academy.image.model.Point;

public class DiamondTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());
        return Point.builder()
            .x(Math.sin(theta) * Math.cos(r))
            .y(Math.cos(theta) * Math.sin(r))
            .build();
    }
}

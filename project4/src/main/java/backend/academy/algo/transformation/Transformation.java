package backend.academy.algo.transformation;

import backend.academy.image.model.Point;

@FunctionalInterface
public interface Transformation {
    Point apply(Point point);
}

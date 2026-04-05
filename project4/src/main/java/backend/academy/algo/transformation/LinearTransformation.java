package backend.academy.algo.transformation;

import backend.academy.image.model.Point;

public class LinearTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        return point;
    }
}

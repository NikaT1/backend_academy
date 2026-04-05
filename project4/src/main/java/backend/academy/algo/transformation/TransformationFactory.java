package backend.academy.algo.transformation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TransformationFactory {
    public static Transformation createTransformation(TransformationType type) {
        return switch (type) {
            case DIAMOND -> new DiamondTransformation();
            case FISHEYE -> new FisheyeTransformation();
            case HEART -> new HeartTransformation();
            case LINEAR -> new LinearTransformation();
            case SINUSOIDAL -> new SinusoidalTransformation();
            case SPHERICAL -> new SphericalTransformation();
            case SWIRL -> new SwirlTransformation();
        };
    }
}

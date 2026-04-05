package backend.academy.image.render;

import backend.academy.algo.AffineFunc;
import backend.academy.algo.transformation.Transformation;
import backend.academy.image.model.Image;
import backend.academy.image.model.Pixel;
import backend.academy.image.model.Point;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import static backend.academy.random.RandomUtil.getRandomDoubleInRange;
import static backend.academy.random.RandomUtil.getRandomFromList;

@RequiredArgsConstructor
public abstract class Render {

    private final int minStep;

    public abstract void render(
        RenderConfig config,
        int symmetry,
        List<AffineFunc> affineFuncs,
        List<Transformation> transformations,
        Image image
    );

    @SuppressFBWarnings("NOS_NON_OWNED_SYNCHRONIZATION")
    protected void renderOneSample(
        RenderConfig config,
        int symmetry,
        List<AffineFunc> affineFuncs,
        List<Transformation> transformations,
        Image image
    ) {
        Point point = Point.builder()
            .x(getRandomDoubleInRange(config.xmin(), config.xmax()))
            .y(getRandomDoubleInRange(config.ymin(), config.ymax()))
            .build();

        for (int step = 0; step < config.iterationsPerSample() + minStep; step++) {
            AffineFunc affineFunc = getRandomFromList(affineFuncs);
            point = affineFunc.apply(point);
            point = getRandomFromList(transformations).apply(point);
            if (step >= minStep) {
                double theta = 0.0;
                for (int s = 0; s < symmetry; s++) {
                    theta += ((2 * Math.PI) / symmetry);
                    Point rotatedPoint = getRotatedPoint(point, theta);
                    Point resultPoint = getResultPoint(rotatedPoint, config.xmin(), config.xmax(), config.ymin(),
                        config.ymax(), image.width(), image.height());
                    //Если точка попала в область изображения
                    if (checkPointIsInImage(resultPoint, image.width(), image.height())) {
                        int xRes = (int) resultPoint.x();
                        int yRes = (int) resultPoint.y();
                        synchronized (image.getPixel(xRes, yRes)) {
                            Pixel pixel = image.getPixel(xRes, yRes);
                            changePixelColor(pixel, affineFunc.colorR(), affineFunc.colorG(), affineFunc.colorB());
                            pixel.incHitCount();
                        }
                    }
                }
            }
        }
    }

    protected boolean checkPointIsInImage(Point point, int width, int height) {
        return point.x() < width && point.y() < height && point.x() >= 0 && point.y() >= 0;
    }

    protected Point getRotatedPoint(Point point, double theta) {
        return Point.builder()
            .x(point.x() * Math.cos(theta) - point.y() * Math.sin(theta))
            .y(point.x() * Math.sin(theta) + point.y() * Math.cos(theta))
            .build();
    }

    protected Point getResultPoint(
        Point point,
        double xmin,
        double xmax,
        double ymin,
        double ymax,
        int width,
        int height
    ) {
        return Point.builder()
            .x(width - (int) (((xmax - point.x()) / (xmax - xmin)) * width))
            .y(height - (int) (((ymax - point.y()) / (ymax - ymin)) * height))
            .build();
    }

    private void changePixelColor(Pixel pixel, short colorR, short colorG, short colorB) {
        if (pixel.hitCount() == 0) {
            pixel.colorR(colorR);
            pixel.colorG(colorG);
            pixel.colorB(colorB);
        } else {
            pixel.colorR((short) ((pixel.colorR() + colorR) / 2));
            pixel.colorG((short) ((pixel.colorG() + colorG) / 2));
            pixel.colorB((short) ((pixel.colorB() + colorB) / 2));
        }
    }
}

package backend.academy.algo.postprocessor;

import backend.academy.image.model.Image;
import backend.academy.image.model.Pixel;
import lombok.RequiredArgsConstructor;
import static java.lang.Math.log10;

@RequiredArgsConstructor
public class CorrectionProcessor implements ImageProcessor {
    private final double gamma;

    @Override
    public void process(Image image) {
        int max = 0;
        for (int i = 0; i < image.width(); i++) {
            for (int j = 0; j < image.height(); j++) {
                max = Math.max(max, image.getPixel(i, j).hitCount());
            }
        }
        double maxNormal = log10(max);
        for (int i = 0; i < image.width(); i++) {
            for (int j = 0; j < image.height(); j++) {
                Pixel pixel = image.getPixel(i, j);
                double normal = log10(pixel.hitCount()) / maxNormal;
                double coef = Math.pow(normal, (1.0 / gamma));
                pixel.colorR((short) (pixel.colorR() * coef));
                pixel.colorG((short) (pixel.colorG() * coef));
                pixel.colorB((short) (pixel.colorB() * coef));
            }
        }
    }
}

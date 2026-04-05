package backend.academy.image.render;

import backend.academy.algo.AffineFunc;
import backend.academy.algo.transformation.Transformation;
import backend.academy.image.model.Image;
import java.util.List;

public class SingleThreadRenderImpl extends Render {

    public SingleThreadRenderImpl(int minStep) {
        super(minStep);
    }

    public void render(
        RenderConfig config,
        int symmetry,
        List<AffineFunc> affineFuncs,
        List<Transformation> transformations,
        Image image
    ) {
        for (int num = 0; num < config.numberSamples(); num++) {
            renderOneSample(
                config,
                symmetry,
                affineFuncs,
                transformations,
                image
            );
        }
    }
}

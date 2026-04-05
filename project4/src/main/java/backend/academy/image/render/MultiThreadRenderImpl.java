package backend.academy.image.render;

import backend.academy.algo.AffineFunc;
import backend.academy.algo.transformation.Transformation;
import backend.academy.image.model.Image;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadRenderImpl extends Render {

    private final int threadsCount;

    public MultiThreadRenderImpl(int minStep, int threadsCount) {
        super(minStep);
        this.threadsCount = threadsCount;
    }

    public void render(
        RenderConfig config,
        int symmetry,
        List<AffineFunc> affineFuncs,
        List<Transformation> transformations,
        Image image
    ) {
        try (ExecutorService executorServices = Executors.newFixedThreadPool(threadsCount)) {
            for (int num = 0; num < config.numberSamples(); num++) {
                executorServices.submit(() -> renderOneSample(
                        config,
                        symmetry,
                        affineFuncs,
                        transformations,
                        image
                    )
                );
            }
        }
    }
}

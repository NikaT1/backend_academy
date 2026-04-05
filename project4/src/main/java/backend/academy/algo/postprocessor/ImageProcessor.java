package backend.academy.algo.postprocessor;

import backend.academy.image.model.Image;

@FunctionalInterface
public interface ImageProcessor {
    void process(Image image);
}

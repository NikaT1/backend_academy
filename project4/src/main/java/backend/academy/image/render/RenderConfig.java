package backend.academy.image.render;

import lombok.Builder;

@Builder
public record RenderConfig(int numberSamples, int iterationsPerSample, double xmin, double xmax,
                           double ymin, double ymax) {
}

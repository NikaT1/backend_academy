package backend.academy.image.render;

import backend.academy.display.UserArgs;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RenderConfigMapper {
    public static RenderConfig userArgsToRenderConfig(UserArgs userArgs) {
        return RenderConfig.builder()
            .numberSamples(userArgs.numberSamples())
            .iterationsPerSample(userArgs.iterationsPerSample())
            .xmax(userArgs.xmax())
            .xmin(userArgs.xmin())
            .ymax(userArgs.ymax())
            .ymin(userArgs.ymin())
            .build();
    }
}

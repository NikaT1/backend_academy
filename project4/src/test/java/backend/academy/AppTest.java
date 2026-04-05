package backend.academy;

import backend.academy.algo.AffineFuncGenerator;
import backend.academy.display.UserArgs;
import backend.academy.image.model.Image;
import backend.academy.image.render.MultiThreadRenderImpl;
import backend.academy.image.render.Render;
import backend.academy.image.render.SingleThreadRenderImpl;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static backend.academy.AppInitializer.loadUserArgs;
import static backend.academy.image.render.RenderConfigMapper.userArgsToRenderConfig;

@Log4j2
public class AppTest {
    private static final int MIN_STEP = 20;

    @Test
    void checkMultiThreadsFasterThanSingleThreadTest() {
        UserArgs userArgs = loadUserArgs(new String[0]);
        Image image = Image.create(userArgs.width(), userArgs.height());

        long start = System.currentTimeMillis();
        Render render = new SingleThreadRenderImpl(MIN_STEP);

        render.render(
            userArgsToRenderConfig(userArgs),
            userArgs.symmetry(),
            AffineFuncGenerator.generateAffineFuncs(userArgs.funcsCount()),
            userArgs.transformationList(),
            image
        );
        long singleDuration = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        render = new MultiThreadRenderImpl(MIN_STEP, 4);

        render.render(
            userArgsToRenderConfig(userArgs),
            userArgs.symmetry(),
            AffineFuncGenerator.generateAffineFuncs(userArgs.funcsCount()),
            userArgs.transformationList(),
            image
        );
        long multiDuration = System.currentTimeMillis() - start;

        Assertions.assertTrue(multiDuration < singleDuration);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    void checkTimeOfMultiThreadsTest(int count) {
        UserArgs userArgs = loadUserArgs(new String[0]);
        Image image = Image.create(userArgs.width(), userArgs.height());

        long start = System.currentTimeMillis();
        Render render = new MultiThreadRenderImpl(MIN_STEP, count);

        render.render(
            userArgsToRenderConfig(userArgs),
            userArgs.symmetry(),
            AffineFuncGenerator.generateAffineFuncs(userArgs.funcsCount()),
            userArgs.transformationList(),
            image
        );
        long multiDuration = System.currentTimeMillis() - start;

        log.info(count + " - " + multiDuration);
    }
}

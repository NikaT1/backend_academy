package backend.academy;

import backend.academy.algo.AffineFuncGenerator;
import backend.academy.algo.postprocessor.CorrectionProcessor;
import backend.academy.algo.postprocessor.ImageProcessor;
import backend.academy.display.UserArgs;
import backend.academy.display.io.FileOutput;
import backend.academy.image.model.Image;
import backend.academy.image.render.MultiThreadRenderImpl;
import backend.academy.image.render.Render;
import backend.academy.image.render.SingleThreadRenderImpl;
import com.beust.jcommander.ParameterException;
import java.io.IOException;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import static backend.academy.AppInitializer.loadUserArgs;
import static backend.academy.image.render.RenderConfigMapper.userArgsToRenderConfig;

@UtilityClass
@Log4j2
public class Main {
    public static void main(String[] args) {
        try {
            UserArgs userArgs = loadUserArgs(args);
            Image image = Image.create(userArgs.width(), userArgs.height());

            Render render;
            if (userArgs.threadsCount() == 1) {
                render = new SingleThreadRenderImpl(userArgs.minStep());
            } else {
                render = new MultiThreadRenderImpl(userArgs.minStep(), userArgs.threadsCount());
            }
            render.render(
                userArgsToRenderConfig(userArgs),
                userArgs.symmetry(),
                AffineFuncGenerator.generateAffineFuncs(userArgs.funcsCount()),
                userArgs.transformationList(),
                image
            );

            ImageProcessor processor = new CorrectionProcessor(userArgs.gamma());
            processor.process(image);

            FileOutput fileOutput = new FileOutput(userArgs.filepath(), userArgs.format());
            fileOutput.print(image);
        } catch (ParameterException e) {
            log.error("Invalid user params specified: " + e);
        } catch (IOException e) {
            log.error("Error occurred during the output process: " + e);
        }
    }
}

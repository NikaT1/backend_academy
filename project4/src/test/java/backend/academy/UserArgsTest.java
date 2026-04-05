package backend.academy;

import backend.academy.algo.transformation.TransformationFactory;
import backend.academy.algo.transformation.TransformationType;
import backend.academy.display.UserArgs;
import backend.academy.display.io.ImageFormat;
import com.beust.jcommander.JCommander;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class UserArgsTest {

    private static final String[] CORRECT_ALL_ARGS =
        "-h 1080 -w 1920 -s 100 -i 100 -S 3 -g 2 -f 6 -t 1 -xmin 1 -xmax 1 -ymin 1 -ymax 1 -p test -T HEART -F PNG"
            .split(" ");

    @Test
    void checkCorrectUserArgsInputTest() {
        UserArgs userArgs = new UserArgs();
        JCommander.newBuilder()
            .addObject(userArgs)
            .build()
            .parse(CORRECT_ALL_ARGS);
        Assertions.assertEquals(userArgs.height(), 1080);
        Assertions.assertEquals(userArgs.format(), ImageFormat.PNG);
        Assertions.assertEquals(userArgs.transformationList().size(), 1);
    }

    @ParameterizedTest
    @EnumSource(ImageFormat.class)
    void checkImageFormatInputTest(ImageFormat format) {
        String inputArgs = "-F " + format.name();
        UserArgs userArgs = new UserArgs();
        JCommander.newBuilder()
            .addObject(userArgs)
            .build()
            .parse(inputArgs.split(" "));
        Assertions.assertEquals(format, userArgs.format());
    }

    @ParameterizedTest
    @EnumSource(TransformationType.class)
    void checkTransformationTypeInputTest(TransformationType type) {
        UserArgs userArgs = new UserArgs();
        JCommander.newBuilder()
            .addObject(userArgs)
            .build()
            .parse(("-T " + type.name()).split(" "));

        Assertions.assertEquals(TransformationFactory.createTransformation(type).getClass(),
            userArgs.transformationList().get(0).getClass());
    }
}

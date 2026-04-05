package backend.academy.display;

import backend.academy.algo.transformation.LinearTransformation;
import backend.academy.algo.transformation.Transformation;
import backend.academy.algo.transformation.TransformationFactory;
import backend.academy.algo.transformation.TransformationType;
import backend.academy.display.io.ImageFormat;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserArgs {

    private static final int HEIGHT = 1080;
    private static final int WIDTH = 1920;
    private static final int NUMBER_SAMPLES = 200000;
    private static final int ITERATIONS_PER_SAMPLE = 1000;
    private static final int SYMMETRY = 3;
    private static final int FUNCTIONS_COUNT = 6;
    private static final int THREADS_COUNT = 6;
    private static final double X_MIN = -1.777;
    private static final double X_MAX = 1.777;
    private static final int Y_MIN = -1;
    private static final int Y_MAX = 1;
    private static final double GAMMA = 2.2;
    private static final int MIN_STEP = 20;

    @Parameter(names = {"-h", "-height"}, description = "Height of image")
    private int height = HEIGHT;
    @Parameter(names = {"-w", "-width"}, description = "Width of image")
    private int width = WIDTH;
    @Parameter(names = {"-s", "-samples"}, description = "Number of samples")
    private int numberSamples = NUMBER_SAMPLES;
    @Parameter(names = {"-i", "-iterations"}, description = "Iterations per sample")
    private int iterationsPerSample = ITERATIONS_PER_SAMPLE;
    @Parameter(names = {"-S", "-symmetry"}, description = "Symmetry level")
    private int symmetry = SYMMETRY;
    @Parameter(names = {"-g", "-gamma"}, description = "Gamma fot correction")
    private double gamma = GAMMA;
    @Parameter(names = {"-f", "-funcs"}, description = "Count of affine functions")
    private int funcsCount = FUNCTIONS_COUNT;
    @Parameter(names = {"-t", "-threads"}, description = "Count of threads")
    private int threadsCount = THREADS_COUNT;
    @Parameter(names = {"-min_step"})
    private int minStep = MIN_STEP;
    @Parameter(names = {"-xmin"})
    private double xmin = X_MIN;
    @Parameter(names = {"-xmax"})
    private double xmax = X_MAX;
    @Parameter(names = {"-ymin"})
    private double ymin = Y_MIN;
    @Parameter(names = {"-ymax"})
    private double ymax = Y_MAX;
    @Parameter(names = {"-p", "-path"}, description = "Path of result file")
    private String filepath = "image_" + new SimpleDateFormat("dd_MM_yyyy").format(new Date());
    @Parameter(names = {"-T", "-transformations"}, description = "List of transformations",
        listConverter = TransformationListConverter.class,
        converter = TransformationListConverter.class)
    private List<Transformation> transformationList = List.of(new LinearTransformation());
    @Parameter(names = {"-F", "-format"}, description = "Format of result file", converter = ImageFormatConverter.class)
    private ImageFormat format = ImageFormat.PNG;

    public static class TransformationListConverter implements IStringConverter<List<Transformation>> {
        @Override
        public List<Transformation> convert(String transformationsNames) {
            String[] transformationsNamesList = transformationsNames.split(",");
            List<Transformation> transformations = new ArrayList<>(transformationsNamesList.length);
            for (String name : transformationsNamesList) {
                TransformationType type = TransformationType.valueOf(name);
                transformations.add(TransformationFactory.createTransformation(type));
            }
            return transformations;
        }
    }

    public static class ImageFormatConverter implements IStringConverter<ImageFormat> {
        @Override
        public ImageFormat convert(String value) {
            try {
                return ImageFormat.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ParameterException("Invalid image format - " + value, e);
            }
        }
    }
}

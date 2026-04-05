package backend.academy.random;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressFBWarnings("PREDICTABLE_RANDOM")
public class RandomUtil {
    private static final Random RANDOM = new Random();

    public static int getRandomIntInRange(int min, int max) {
        return RANDOM.nextInt(min, max);
    }

    public static short getRandomShortInRange(short min, short max) {
        return (short) RANDOM.nextInt(min, max);
    }

    public static double getRandomDoubleInRange(double min, double max) {
        return RANDOM.nextDouble(min, max);
    }

    public static <T> T getRandomFromList(List<T> elements) {
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        int i = getRandomIntInRange(0, elements.size());
        return elements.get(i);
    }
}

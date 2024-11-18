package backend.academy.random;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressFBWarnings("PREDICTABLE_RANDOM")
public class RandomNumberGenerator {

    private static final Random RANDOM = new Random(123);

    public static int getRandomNumberInRange(int max) {
        return RANDOM.nextInt(max);
    }

    public static int getRandomNumberInRange(int min, int max) {
        return min + RANDOM.nextInt(max - min);
    }

}

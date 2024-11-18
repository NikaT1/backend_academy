package backend.academy.random;

import backend.academy.graph.model.Coordinate;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressFBWarnings("PREDICTABLE_RANDOM")
public class RandomUtil {
    private static final Random RANDOM = new Random();

    public static Coordinate getRandomId(int width, int height) {
        int x = RANDOM.nextInt(width);
        int y = RANDOM.nextInt(height);
        return new Coordinate(x, y);
    }

    public static <T> Optional<T> pullRandomFromList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return Optional.empty();
        }
        int id = RANDOM.nextInt(list.size());
        T el = list.get(id);
        list.remove(id);
        return Optional.of(el);
    }

    public static int getRandomNumberInRange(int min, int max) {
        return RANDOM.nextInt(min, max);
    }

    public static int getRandomNumber() {
        return RANDOM.nextInt();
    }

}

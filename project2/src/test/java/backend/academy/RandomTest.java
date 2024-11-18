package backend.academy;

import backend.academy.graph.model.Coordinate;
import backend.academy.random.RandomUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomTest {

    private static final int testWidth = 4;
    private static final int testHeight = 5;

    @Test
    public void checkRandomNumberGenerationTest() {
        Coordinate id = RandomUtil.getRandomId(testWidth, testHeight);
        Assertions.assertTrue(id.x() < testWidth && id.y() < testHeight);
    }

    @ParameterizedTest
    @MethodSource("provideNumberRange")
    public void checkRandomNumberInRangeGenerationTest(int minVal, int maxVal) {
        int number = RandomUtil.getRandomNumberInRange(minVal, maxVal);
        Assertions.assertTrue(number >= minVal && number < maxVal);
    }

    @Test
    public void checkPullRandomElementFromListTest() {
        List<Integer> prevList = List.of(1, 2, 3);
        List<Integer> list = new ArrayList<>(prevList);
        Integer element = RandomUtil.pullRandomFromList(list).orElseThrow();
        Assertions.assertTrue(prevList.contains(element));
        Assertions.assertFalse(list.contains(element));
    }

    @Test
    public void checkPullRandomElementFromEmptyListTest() {
        List<Integer> list = new ArrayList<>();
        Optional<Integer> element = RandomUtil.pullRandomFromList(list);
        Assertions.assertTrue(element.isEmpty());
    }

    private static Stream<Arguments> provideNumberRange() {
        return Stream.of(
            Arguments.of(1, 4),
            Arguments.of(2, 3)
        );
    }
}

package backend.academy.algo;

import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AffineFuncGenerator {

    public static List<AffineFunc> generateAffineFuncs(int count) {
        List<AffineFunc> affineFuncs = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            affineFuncs.add(new AffineFunc());
        }
        return affineFuncs;
    }
}

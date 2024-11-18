package backend.academy.words.model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public record WordsDictionary(Map<String, EnumMap<ComplexityLevel, List<Word>>> dictionary) {
}

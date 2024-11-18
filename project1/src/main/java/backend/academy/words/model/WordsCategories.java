package backend.academy.words.model;

import java.util.List;
import lombok.Getter;

@Getter
public class WordsCategories {
    private final List<String> categories;
    private final String categoriesToPrint;

    public WordsCategories(List<String> categories) {
        this.categories = categories;
        categoriesToPrint = prepareCategoriesToPrint(categories);
    }

    private String prepareCategoriesToPrint(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(i + 1).append(" - ").append(list.get(i)).append(System.lineSeparator());
        }
        return builder.toString();
    }

}

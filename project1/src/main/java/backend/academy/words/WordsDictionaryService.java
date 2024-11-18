package backend.academy.words;

import backend.academy.words.model.ComplexityLevel;
import backend.academy.words.model.Word;
import backend.academy.words.model.WordsCategories;
import java.util.List;
import lombok.RequiredArgsConstructor;
import static backend.academy.random.RandomNumberGenerator.getRandomNumberInRange;

@RequiredArgsConstructor
public class WordsDictionaryService {
    private final WordsDictionaryRepository wordsDictionaryRepository;

    public Word getRandomWord(String category, ComplexityLevel complexity) {
        List<Word> words = wordsDictionaryRepository.getWordsListByCategoryAndComplexity(category, complexity);
        Word word = words.get(getRandomNumberInRange(words.size()));
        wordsDictionaryRepository.checkWordLength(word, complexity);
        return word;
    }

    public WordsCategories getWordsCategories() {
        return wordsDictionaryRepository.getWordsCategories();
    }
}

package backend.academy.words;

import backend.academy.display.io.Input;
import backend.academy.words.exception.IncorrectWordLengthException;
import backend.academy.words.exception.IncorrectWordsFileException;
import backend.academy.words.exception.NoSuchWordsSpecifiedException;
import backend.academy.words.model.ComplexityLevel;
import backend.academy.words.model.Word;
import backend.academy.words.model.WordsCategories;
import backend.academy.words.model.WordsDictionary;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WordsDictionaryRepository {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private WordsDictionary wordsDictionary;
    private WordsCategories wordsCategories;

    public WordsDictionaryRepository initDictionary(Input wordsInput) throws IncorrectWordsFileException {
        try {
            wordsDictionary = OBJECT_MAPPER.readValue(wordsInput.read(), WordsDictionary.class);
        } catch (IOException e) {
            throw new IncorrectWordsFileException(e);
        }
        wordsCategories = new WordsCategories(List.copyOf(wordsDictionary.dictionary().keySet()));
        checkWordsInDictionary();
        return this;
    }

    private void checkWordsInDictionary() throws IncorrectWordsFileException {
        if (wordsCategories.categories().isEmpty()) {
            throw new IncorrectWordsFileException("no categories found");
        }
        for (String category : wordsCategories.categories()) {
            EnumMap<ComplexityLevel, List<Word>> wordMap = wordsDictionary.dictionary().get(category);
            boolean notAllLevels = Arrays.stream(ComplexityLevel.values())
                .anyMatch(l -> !wordMap.containsKey(l));
            if (notAllLevels) {
                throw new IncorrectWordsFileException("not all levels found in categories");
            }
            checkWordsLength(wordMap);
        }
    }

    private void checkWordsLength(EnumMap<ComplexityLevel, List<Word>> wordMap) {
        for (ComplexityLevel level : ComplexityLevel.values()) {
            wordMap.get(level).forEach(word -> checkWordLength(word, level));
        }
    }

    public void checkWordLength(Word word, ComplexityLevel level) {
        if (word.name().length() < level.minLength() || level.maxLength() < word.name().length()) {
            throw new IncorrectWordLengthException(word.name(), level.minLength(), level.maxLength());
        }
    }

    public List<Word> getWordsListByCategoryAndComplexity(String category, ComplexityLevel complexity) {
        Optional.of(wordsDictionary);
        List<Word> list = Optional.of(wordsDictionary.dictionary().get(category))
            .orElseThrow(NoSuchWordsSpecifiedException::new)
            .get(complexity);

        if (list == null || list.isEmpty()) {
            throw new NoSuchWordsSpecifiedException();
        }
        return Collections.unmodifiableList(list);
    }

    public WordsCategories getWordsCategories() {
        Optional.of(wordsCategories);
        return wordsCategories;
    }
}

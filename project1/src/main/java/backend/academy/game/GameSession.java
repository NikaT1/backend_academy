package backend.academy.game;

import backend.academy.words.model.Word;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

public class GameSession {
    // минимальный остаток попыток для подсказки
    private final static int MIN_STEP_FOR_HINT = 3;
    @Getter
    private final char[] word;
    @Getter
    private final String hint;
    @Getter
    private final int maxStepsCount;
    @Getter
    private int currentStepCount = 0;
    @Getter
    private SessionState currentState;
    private final Map<Character, Boolean> correctLetters;
    private final Set<Character> wrongLetters = new HashSet<>();

    public GameSession(Word word, int maxStepsCount) {
        this.maxStepsCount = maxStepsCount;
        this.word = word.name().toCharArray();
        this.hint = word.hint();
        correctLetters = new HashMap<>(word.name().length());
        for (char c : this.word) {
            this.correctLetters.put(c, false);
        }
        this.currentState = SessionState.IN_PROCESS;
    }

    public String getCurrentAnswerState() {
        StringBuilder builder = new StringBuilder();
        for (char c : word) {
            if (correctLetters.get(c)) {
                builder.append(c);
            } else {
                builder.append('_');
            }
        }
        return builder.toString();
    }

    public boolean addLetter(char letter) {
        boolean result = true;
        if (correctLetters.containsKey(letter)) {
            correctLetters.put(letter, true);
        } else {
            wrongLetters.add(letter);
            currentStepCount++;
            result = false;
        }
        updateSessionState();
        return result;
    }

    public boolean checkNotRepeatedLetter(char letter) {
        return !(correctLetters.get(letter) != null && correctLetters.get(letter) || wrongLetters.contains(letter));
    }

    public String getWrongLettersStr() {
        return wrongLetters.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(" "));
    }

    public boolean isNeededToShowHint() {
        return maxStepsCount - currentStepCount < MIN_STEP_FOR_HINT;
    }

    private void updateSessionState() {
        boolean result = correctLetters.values().stream().anyMatch(val -> !val);
        if (!result) {
            currentState = SessionState.WIN;
        } else if (currentStepCount == maxStepsCount) {
            currentState = SessionState.LOSE;
        }
    }
}


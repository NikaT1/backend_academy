package backend.academy.game;

import lombok.Builder;

@Builder
public record GuessResult(
    int currentStepCount,
    int maxStepCount,
    boolean correctLetter,
    String currentAnswerStr,
    boolean needToShowHint,
    String hint,
    String currentWrongLettersStr
) {
}

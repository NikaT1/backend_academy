package backend.academy;

import backend.academy.display.UserArgs;
import backend.academy.display.UserIOService;
import backend.academy.game.GameSession;
import backend.academy.game.GuessResult;
import backend.academy.game.SessionState;
import backend.academy.words.WordsDictionaryService;
import java.util.Optional;
import static backend.academy.GameSessionGenerator.getGameSession;

public class GameDriver {

    private final UserIOService userIOService;
    private GameSession gameSession;

    public GameDriver(UserIOService userIOService) {
        this.userIOService = userIOService;

    }

    public GameDriver initGameSession(UserArgs userArgs, WordsDictionaryService dictionary) {
        this.gameSession = getGameSession(dictionary, userArgs);
        return this;
    }

    public void startGame() {
        Optional.of(gameSession);
        startUserInputLoop();
        sumUpResults();
    }

    private void startUserInputLoop() {
        userIOService.sendWordStartInfo(gameSession.getCurrentAnswerState());
        while (gameSession.currentState() == SessionState.IN_PROCESS) {
            char letter = readNextLetter();
            GuessResult guessResult = processUserInput(letter);
            userIOService.sendGameAnswer(guessResult);
        }
    }

    private void sumUpResults() {
        if (gameSession.currentState() == SessionState.WIN) {
            userIOService.sendWinFinalMessage();
        } else {
            userIOService.sendLoseFinalMessage();
        }
    }

    private char readNextLetter() {
        char letter = userIOService.getNextUserLetter();
        while (!gameSession.checkNotRepeatedLetter(letter)) {
            userIOService.sendRepeatedLetterMessage();
            letter = userIOService.getNextUserLetter();
        }
        return letter;
    }

    private GuessResult processUserInput(char letter) {
        boolean result = gameSession.addLetter(letter);
        return GuessResult.builder()
            .correctLetter(result)
            .currentStepCount(gameSession.currentStepCount())
            .maxStepCount(gameSession.maxStepsCount())
            .currentAnswerStr(gameSession.getCurrentAnswerState())
            .needToShowHint(gameSession.isNeededToShowHint())
            .hint(gameSession.hint())
            .currentWrongLettersStr(gameSession.getWrongLettersStr())
            .build();
    }
}

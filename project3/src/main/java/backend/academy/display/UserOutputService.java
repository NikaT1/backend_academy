package backend.academy.display;

import backend.academy.display.io.Output;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserOutputService {

    private final Output output;

    public void sendReport(String report) {
        output.println(report);
    }

    public void sendUserArgsError(String msg) {
        output.printf(UserMessages.INCORRECT_USER_ARGS, msg);
    }

    public void sendExecutionError(String msg) {
        output.printf(UserMessages.ERROR_DURING_EXECUTION, msg);
    }
}

package backend.academy.display;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMessages {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[31m";

    public static final String INCORRECT_USER_ARGS = RED + "Incorrect startup parameters are set: %s" + RESET;
    public static final String ERROR_DURING_EXECUTION = RED + "Error has occurred: %s" + RESET;
}

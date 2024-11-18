package backend.academy.display;

import com.beust.jcommander.ParameterException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserArgsValidator {
    public static void checkUserArgsParams(UserArgs userArgs) {
        if (userArgs.logField() != null) {
            if (userArgs.valuePattern() == null) {
                throw new ParameterException("Param filter-value can't be null");
            }
        } else {
            if (userArgs.valuePattern() != null) {
                throw new ParameterException("Param filter-value should be used with filter-field param");
            }
        }
    }
}

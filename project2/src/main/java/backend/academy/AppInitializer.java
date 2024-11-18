package backend.academy;

import backend.academy.display.UserArgs;
import backend.academy.display.UserIOService;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class AppInitializer {

    public static UserArgs loadUserArgs(String[] args, UserIOService userIO) {
        try {
            UserArgs userArgs = new UserArgs();
            JCommander.newBuilder()
                .addObject(userArgs)
                .build()
                .parse(args);
            return userArgs;
        } catch (ParameterException e) {
            log.error("Invalid user params specified. The program execution will continue with empty params.");
            userIO.sendUserArgsErrMessage();
            return new UserArgs();
        }
    }
}

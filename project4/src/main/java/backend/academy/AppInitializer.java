package backend.academy;

import backend.academy.display.UserArgs;
import com.beust.jcommander.JCommander;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class AppInitializer {
    public static UserArgs loadUserArgs(String[] args) {
        UserArgs userArgs = new UserArgs();
        JCommander.newBuilder()
            .addObject(userArgs)
            .build()
            .parse(args);
        return userArgs;
    }
}

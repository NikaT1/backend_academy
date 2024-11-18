package backend.academy.display.io;

import org.apache.commons.validator.routines.UrlValidator;

public class InputFactory {
    private static final String[] URI_SCHEME = {"http", "https"};
    private static final UrlValidator URL_VALIDATOR = new UrlValidator(URI_SCHEME);

    public Input getInput(String path) {
        if (URL_VALIDATOR.isValid(path)) {
            return prepareURLInput(path);
        } else {
            return prepareFileInput(path);
        }
    }

    private FileInput prepareFileInput(String path) {
        return new FileInput(path);
    }

    private URLInput prepareURLInput(String path) {
        return new URLInput(path);
    }
}

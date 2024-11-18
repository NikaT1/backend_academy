package backend.academy.words.exception;

public class NoSuchWordsSpecifiedException extends IllegalArgumentException {
    public NoSuchWordsSpecifiedException() {
        super("No such game words were specified");
    }
}

package backend.academy.display.exception;

public class NoPictureGeneratorFoundException extends RuntimeException {
    public NoPictureGeneratorFoundException() {
        super("Picture generator was not found");
    }
}

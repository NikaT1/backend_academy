package backend.academy.display.io;

import backend.academy.image.model.Image;
import java.io.IOException;

public interface Output {
    void print(Image image) throws IOException;
}

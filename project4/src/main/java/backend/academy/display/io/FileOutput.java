package backend.academy.display.io;

import backend.academy.image.model.Image;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public class FileOutput implements Output {

    private final File file;
    private final ImageFormat imageFormat;

    @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
    public FileOutput(String filename, ImageFormat imageFormat) throws IOException {
        try {
            Path path = Path.of(prepareFullFileName(filename, imageFormat));
            if (Files.exists(path)) {
                Files.delete(path);
            }
            file = Files.createFile(path).toFile();
            this.imageFormat = imageFormat;
        } catch (IOException e) {
            throw new IOException("Incorrect result filename was specified", e);
        }
    }

    @Override
    public void print(Image image) throws IOException {
        BufferedImage bi = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_BGR);

        for (int i = 0; i < image.width(); i++) {
            for (int j = 0; j < image.height(); j++) {
                bi.setRGB(i, j,
                    new Color(
                        image.getPixel(i, j).colorR(),
                        image.getPixel(i, j).colorG(),
                        image.getPixel(i, j).colorB()
                    ).getRGB()
                );
            }
        }
        ImageIO.write(bi, imageFormat.format(), file);
    }

    private String prepareFullFileName(String filename, ImageFormat imageFormat) {
        return filename + '.' + imageFormat.format();
    }
}

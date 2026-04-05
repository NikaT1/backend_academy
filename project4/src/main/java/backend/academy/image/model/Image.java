package backend.academy.image.model;

public record Image(Pixel[][] pixels, int width, int height) {
    public static Image create(int width, int height) {
        Pixel[][] pixels = new Pixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = new Pixel();
            }
        }
        return new Image(pixels, width, height);
    }

    public Pixel getPixel(int x, int y) {
        return pixels[x][y];
    }
}

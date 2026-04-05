package backend.academy.display.io;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ImageFormat {
    JPEG("jpeg"), BMP("bmp"), PNG("png");

    @Getter
    private final String format;
}

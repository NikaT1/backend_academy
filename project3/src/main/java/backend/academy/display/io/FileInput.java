package backend.academy.display.io;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class FileInput implements Input {

    private static final Path ROOT_DIR = Paths.get("").toAbsolutePath();
    private final String path;

    @Override
    public Stream<String> read() throws IOException {
        PathMatcher pathMatcher;
        try {
            pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + path);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input file pattern: " + path, e);
        }

        try (Stream<Path> pathStream = Files.walk(ROOT_DIR)) {
            List<Path> foundFiles = pathStream
                .filter(p -> Files.isRegularFile(p) && pathMatcher.matches(p))
                .toList();

            return foundFiles.stream().flatMap(this::readFromSingleFile);
        }
    }

    private Stream<String> readFromSingleFile(Path path) {
        try {
            return Files.lines(path);
        } catch (IOException e) {
            log.warn("Invalid file will be skipped: " + e.getMessage());
            return Stream.empty();
        }
    }
}

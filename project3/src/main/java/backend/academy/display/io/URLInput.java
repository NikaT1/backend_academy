package backend.academy.display.io;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class URLInput implements Input {
    private static final int SUCCESSFUL_CODE = 200;

    private final String path;

    @Override
    public Stream<String> read() throws IOException {
        HttpResponse<Stream<String>> response;
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(path)).build();
            response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofLines());
        } catch (InterruptedException e) {
            throw new IOException("Error during getting data from url", e);
        } catch (URISyntaxException e) {
            throw new IOException("Incorrect form of url", e);
        }

        if (response.statusCode() == SUCCESSFUL_CODE) {
            return response.body();
        } else {
            throw new IOException("Unsuccessful data request");
        }
    }
}

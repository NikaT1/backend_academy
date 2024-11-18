package backend.academy.log;

public record Request(String httpMethod, String resource, String httpVersion) {
}

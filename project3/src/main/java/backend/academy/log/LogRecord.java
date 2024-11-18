package backend.academy.log;

import java.time.OffsetDateTime;
import lombok.Builder;

@Builder
public record LogRecord(String remoteAddr,
                        String remoteUser,
                        OffsetDateTime timeLocal,
                        Request request,
                        String status,
                        Long bodyBytesSent,
                        String httpReferer,
                        String httpUserAgent) {
}

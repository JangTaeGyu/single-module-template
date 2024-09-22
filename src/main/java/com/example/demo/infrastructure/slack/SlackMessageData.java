package com.example.demo.infrastructure.slack;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@Slf4j
public class SlackMessageData {
    private static final String TEMPLATE_LINE_FORMAT = "%s : ```%s```";
    private static final String NEW_LINE = "\n";

    private final String method;
    private final String uri;
    private final String param;
    private final String body;
    private final Exception error;

    public SlackMessageData(HttpServletRequest request, Exception error) {
        this.method = request.getMethod();
        this.uri = request.getRequestURI();
        this.param = request.getQueryString();
        this.body = getRequestBody(request);
        this.error = error;
    }

    private String getRequestBody(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    return "Unsupported Encoding";
                }
            }
        }

        return "{}";
    }

    public String toTemplate() {
        StringBuilder result = new StringBuilder();

        Stream.of(
                        formatLine("RequestType", method),
                        formatLine("URL", uri),
                        formatLine("Param", param),
                        formatLine("Body", body),
                        formatErrorLine(error)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(line -> result.append(line).append(NEW_LINE).append(NEW_LINE));

        return result.toString().trim();
    }

    private Optional<String> formatLine(String label, String value) {
        return Optional.ofNullable(value)
                .map(v -> String.format(TEMPLATE_LINE_FORMAT, label, v));
    }

    private Optional<String> formatErrorLine(Exception error) {
        return Optional.ofNullable(error)
                .map(e -> String.format(TEMPLATE_LINE_FORMAT, "Error", e.getClass().getCanonicalName() + " : " + e.getMessage()));
    }
}

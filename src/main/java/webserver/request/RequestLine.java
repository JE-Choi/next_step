package webserver.request;

import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestLine {
    public static RequestLine NULL_OBJECT = new RequestLine() {
        @Override
        public boolean isValid() {
            return false;
        }
    };

    private final HttpMethod method;
    private final URI uri;
    private final String httpVersion;

    public RequestLine() {
        this.method = HttpMethod.NOT_FOUND;
        this.uri = new URI("");
        this.httpVersion = "";
    }

    public RequestLine(String line) {
        final String[] tokens = line.split("\\s");
        this.method = HttpMethod.getMethod(tokens[0]);
        this.uri = new URI(tokens[1]);
        this.httpVersion = tokens[2];
    }

    public HttpMethod getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return "HttpHead{" +
                "method='" + method + '\'' +
                ", url='" + uri.getRequestUri() + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine requestLine = (RequestLine) o;
        return Objects.equals(method, requestLine.method) &&
                Objects.equals(uri, requestLine.uri) &&
                Objects.equals(httpVersion, requestLine.httpVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, uri, httpVersion);
    }

    public boolean isValid() {
        return true;
    }
}

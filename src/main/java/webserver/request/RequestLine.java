package webserver.request;

import java.util.Arrays;
import java.util.Objects;

public class RequestLine {
    public static RequestLine NULL_OBJECT = new RequestLine("","",""){
        @Override
        public boolean isValid() {
            return false;
        }
    };

    private final HttpMethod method;
    private final String url;
    private final String httpVersion;

    public RequestLine(final String method, final String url, final String httpVersion) {
        this.method = HttpMethod.getMethod(method);
        this.url = url;
        this.httpVersion = httpVersion;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public enum HttpMethod {
        GET, POST, PUT, DELETE, NOT_FOUND;
        public static HttpMethod getMethod(final String str) {
            return Arrays.stream(values()).filter(method -> method.name().equals(str)).findFirst().orElse(NOT_FOUND);
        }
    }

    @Override
    public String toString() {
        return "HttpHead{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine requestLine = (RequestLine) o;
        return Objects.equals(method, requestLine.method) &&
                Objects.equals(url, requestLine.url) &&
                Objects.equals(httpVersion, requestLine.httpVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, url, httpVersion);
    }

    public boolean isValid(){
        return true;
    }
}
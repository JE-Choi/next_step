package webserver.request;

import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestLine {
    public static RequestLine NULL_OBJECT = new RequestLine("", "", "") {
        @Override
        public boolean isValid() {
            return false;
        }
    };

    private final HttpMethod method;
    private final String url;
    private final String httpVersion;
    private final Map<String, String> queryString;

    public RequestLine(final String method, final String url, final String httpVersion) {
        this.method = HttpMethod.getMethod(method);
        String[] urlSplits = url.split("\\?");
        this.url = urlSplits[0];
        this.queryString = urlSplits.length > 1 ? HttpRequestUtils.parseQueryString(urlSplits[1]) : new HashMap<>();
        this.httpVersion = httpVersion;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getQueryString() {
        return this.queryString;
    }

    public String getHttpVersion() {
        return httpVersion;
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

    public boolean isValid() {
        return true;
    }
}

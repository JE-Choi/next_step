package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
/**
 * Todo:
 * - interface vs abstract
 * <p>
 * 참고: https://testmanager.tistory.com/346
 */
public class HttpRequestDefault implements HttpRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestDefault.class);
    private RequestLine requestLine = RequestLine.NULL_OBJECT;
    private RequestHeader requestHeader = RequestHeader.NULL_OBJECT;
    private Map<String, String> requestBody = new HashMap<>();

    public HttpRequestDefault(final BufferedReader bufferedReader) throws IOException {
        if(Objects.isNull(bufferedReader)){
            return;
        }
        setHeader(bufferedReader);
        setBody(bufferedReader);
    }

    @Override
    public RequestLine getRequestLine() {
        if (!requestLine.isValid()) {
            throw new IllegalStateException("httpHead is valid");
        }
        return requestLine;
    }

    @Override
    public RequestHeader getRequestHeader() {
        if (!requestHeader.isValid()) {
            throw new IllegalStateException("requestHeader is valid");
        }
        return requestHeader;
    }

    @Override
    public Map<String, String> getRequestBody() {
        return requestBody;
    }

    private void setHeader(final BufferedReader bufferedReader) throws IOException {
        final RequestHeader requestHeader = new RequestHeader();
        String line = bufferedReader.readLine();
        while (StringUtils.isNotEmpty(line)) {
            appendHeader(line, requestHeader);
            line = bufferedReader.readLine();
        }

        this.requestHeader = requestHeader;
    }

    private void appendHeader(final String line, final RequestHeader requestHeader) {
        HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(line);
        final boolean isRequestLine = Objects.isNull(pair);
        final boolean isRequestHeader = Objects.nonNull(pair);
        if (isRequestLine) {
            this.requestLine = new RequestLine(line);
        } else if (isRequestHeader) {
            requestHeader.put(pair.getKey(), pair.getValue());
        }
    }

    private void setBody(final BufferedReader bufferedReader) throws IOException {
        try {
            // 범위가 클수도?!
            int contentLength = Integer.parseInt(getRequestHeader().get(HttpHeader.CONTENT_LENGTH).trim());
            if (contentLength > 0) {
                String body = IOUtils.readData(bufferedReader, contentLength);
                this.requestBody = HttpRequestUtils.parseQueryString(body);
            }
        } catch (NumberFormatException | NullPointerException e) {
            // ...✔ ok ok~~
        }
    }

    @Override
    public String toString() {
        return "HttpRequestDefault{" +
                "requestLine=" + getRequestLine() +
                ", \nrequestHeader=" + getRequestHeader() +
                ", \nrequestBody=" + getRequestBody() +
                '}';
    }
}

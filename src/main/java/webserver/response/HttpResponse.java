package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.resolver.ViewResolver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class HttpResponse implements Response {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);

    private final Map headerMap = new HashMap<String, String>();
    private final ViewResolver viewResolver;
    private final DataOutputStream dos;

    public HttpResponse(final ViewResolver viewResolver, final DataOutputStream dos) {
        this.viewResolver = viewResolver;
        this.dos = dos;
    }

    @Override
    public void addHeader(final String key, final String value) {
        this.headerMap.put(key, value);
    }

    @Override
    public void sendResponse() {
        responseHeader();
        responseBody();
    }

    private void responseHeader() {
        Set<Map.Entry<String, String>> entrySet = this.headerMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n"); // 확장할 예정
            while (iterator.hasNext()) {
                final Map.Entry<String, String> next = iterator.next();
                dos.writeBytes(String.format("%s: %s\r\n", next.getKey(), next.getValue()));
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void responseBody() {
        try {
            dos.write(viewResolver.getBodyBytes(), 0, viewResolver.getBodyBytes().length);
            dos.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "headerMap=" + headerMap +
                '}';
    }
}

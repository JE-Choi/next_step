package webserver.request;

import java.util.Map;

public class RequestWrapper {
    private final HttpRequest request;

    public RequestWrapper(HttpRequest request) {
        this.request = request;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public String getParameter(String key) throws NullPointerException {
        switch (request.getRequestLine().getMethod()) {
            case GET:
                return this.request.getRequestLine().getUri().getQueryString().get(key);
            case POST:
                return this.request.getRequestBody().get(key);
            default:
                throw new NullPointerException();
        }
    }
}

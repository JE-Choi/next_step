package webserver.request;

public class RequestWrapper {
    private final HttpRequest request;

    public RequestWrapper(HttpRequest request) {
        this.request = request;
    }

    public HttpRequest getRequest() {
        return request;
    }
}

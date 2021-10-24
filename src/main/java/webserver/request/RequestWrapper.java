package webserver.request;

import webserver.session.Session;

import java.util.Objects;

public class RequestWrapper {
    private final HttpRequest request;

    public RequestWrapper(HttpRequest request) {
        this.request = request;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public boolean isLogined() {
        Object user = this.getRequest().getSession().getAttribute("user");
        return Objects.nonNull(user);
    }

    public Session getSession() {
        return this.request.getSession();
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

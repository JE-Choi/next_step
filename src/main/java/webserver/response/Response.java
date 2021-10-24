package webserver.response;
public interface Response {
    void addHeader(final String key, final String value);

    void sendResponse();
}

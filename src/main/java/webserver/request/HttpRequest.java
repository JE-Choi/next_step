package webserver.request;
import java.util.Map;

public interface HttpRequest {
    RequestLine getRequestLine();

    RequestHeader getRequestHeader();

    Map<String, String> getRequestBody();
}

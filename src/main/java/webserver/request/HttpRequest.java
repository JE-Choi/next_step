package webserver.request;
import webserver.cookie.Cookie;
import webserver.session.Session;

import java.util.Map;

public interface HttpRequest {
    RequestLine getRequestLine();

    RequestHeader getRequestHeader();

    Map<String, String> getRequestBody();

    Cookie getCookies();

    Session getSession();
}

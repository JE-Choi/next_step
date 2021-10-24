package webserver.cookie;

public interface Cookie {
    String getCookie(String name);

    String getCookie(CookieKey cookieKey);

    /**
     *  Todo: 이거 사용하는걸로 수정되어야 함.
     */
    void setCookie(final String key, final String value);
}

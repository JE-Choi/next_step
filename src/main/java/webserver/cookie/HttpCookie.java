package webserver.cookie;

import util.HttpRequestUtils;

import java.util.Map;

public abstract class HttpCookie implements Cookie {
    private final Map<String, String> cookies;

    public HttpCookie(String cookiesValue) {
        this.cookies = HttpRequestUtils.parseCookies(cookiesValue);
    }

    @Override
    public String getCookie(String name) {
        return this.cookies.get(name);
    }

    @Override
    public String getCookie(CookieKey cookieKey) {
        return this.cookies.get(cookieKey.getKey());
    }

    @Override
    public void setCookie(final String key, final String value) {
        this.cookies.put(key, value);
    }

    @Override
    public String toString() {
        return cookies.toString();
    }
}

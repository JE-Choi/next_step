package webserver.cookie;

public enum CookieKey {
    JSESSIONID("JSESSIONID");
    private final String key;

    CookieKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

package webserver.session;

import java.util.Objects;

public class SessionKey {
    private final String key ;

    public SessionKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.equals((SessionKey) o);
    }

    public boolean equals(SessionKey sessionKey){
        return this.key.equals(sessionKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}

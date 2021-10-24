package webserver.session;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class HttpSession implements Session {
    private final UUID uuid;
    private final Map<String, Object> map = new HashMap<>();

    public HttpSession() {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public String getId() {
        return uuid.toString();
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.map.put(name, value);
    }

    @Nullable
    @Override
    public Object getAttribute(String name) {
        return this.map.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        this.map.remove(name);
    }

    @Override
    public void invalidate() {
        /**
         * 하위도메인이 상위도메인에 접근해서, 보류
         */
//        HttpSessions.remove(id);
    }
}

package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSessions {
    private static final Map<SessionKey, Session> SESSIONS = new HashMap<>();

    public static Session findSessionByKey(final SessionKey key) {
        final Session session = HttpSessions.SESSIONS.get(key);
        if (Objects.nonNull(session)) {
            return session;
        } else {
            /**
             * 클라이언트가 처음 접근하는 경우, 클라이언트가 사용할 세션 아이디를 생성한 후 쿠키를 통해 전달한다.
             */
            return createSession();
        }
    }

    public static Session createSession() {
        final HttpSessionDefault newSession = new HttpSessionDefault();
        HttpSessions.appendSession(new SessionKey(newSession.getId()), newSession);
        return newSession;
    }

    public static void appendSession(final SessionKey key, final Session session) {
        HttpSessions.SESSIONS.put(key, session);
    }

    // Todo: 지우기 추가
}

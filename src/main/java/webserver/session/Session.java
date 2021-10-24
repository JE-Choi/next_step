package webserver.session;

public interface Session {
    /**
     * 현재 세션에 할당되어있는 고유한 세션 아이디를 반환
     */
    String getId();

    /**
     * 현재 세션에 value인자로 접달되는 객체를 name 인자이름으로 저장
     */
    void setAttribute(final String name, final Object value);

    /**
     * name 인자로 저장되어있는 객체 값 반환
     */
    Object getAttribute(final String name);

    /**
     * name 인자로 저장되어있는 객체 값 삭제
     */
    void removeAttribute(final String name);

    /**
     * 현재 세션에 저장되어있는 모든 값을 삭제
     */
    void invalidate();
}

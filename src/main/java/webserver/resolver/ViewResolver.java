package webserver.resolver;

import java.nio.charset.Charset;

/**
 * 응답에 대한 byte[]가 반환되어야 함.
 */
public interface ViewResolver {
    String getContentType();

    Charset getCharset();

    /**
     * 여기서 body 생성되면 안됨.(중복요청 가능성있음)
     */
    byte[] getBodyBytes();

    String getBody();
}

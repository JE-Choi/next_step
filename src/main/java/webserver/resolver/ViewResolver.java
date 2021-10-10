package webserver.resolver;

import java.nio.charset.Charset;

/**
 * 응답에 대한 byte[]가 반환되어야 함.
 */
public interface ViewResolver {
    String getContentType();

    Charset getCharset();

    byte[] getBodyBytes();
}

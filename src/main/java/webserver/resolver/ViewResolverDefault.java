package webserver.resolver;

import webserver.request.HttpRequest;

import java.nio.charset.Charset;

public abstract class ViewResolverDefault implements ViewResolver{
    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
    private static final String DEFAULT_CONTENT_TYPE = "text/html";
    protected final HttpRequest request;

    public ViewResolverDefault(HttpRequest request) {
        this.request = request;
    }

    @Override
    public String getContentType() {
        return DEFAULT_CONTENT_TYPE;
    }

    @Override
    public Charset getCharset() {
        return DEFAULT_CHARSET;
    }

    @Override
    public byte[] getBodyBytes() {
        return "Invalid request.".getBytes();
    }
}

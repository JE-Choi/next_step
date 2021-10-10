package webserver.resolver;

import webserver.request.HttpRequest;

public interface ViewResolverFactory {
    ViewResolver create(HttpRequest request);
}

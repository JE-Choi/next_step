package webserver.servlet.annotation;

import webserver.request.HttpRequest;

import java.lang.reflect.Method;

public interface ApiReflection {
    Method findApiByRequest(final HttpRequest request);
}

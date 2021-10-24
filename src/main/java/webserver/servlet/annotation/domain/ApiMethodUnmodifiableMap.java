package webserver.servlet.annotation.domain;

import webserver.request.HttpMethod;
import webserver.servlet.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Map;

public class ApiMethodUnmodifiableMap implements ApiMethodMap {
    private final Map<String, Map<HttpMethod, Method>> map;

    public ApiMethodUnmodifiableMap(final Map<String, Map<HttpMethod, Method>> map) {
        this.map = map;
    }

    public ApiMethodUnmodifiableMap(final ApiMethodMap map) {
        this.map = map.getMap();
    }

    @Override
    public Map<HttpMethod, Method> put(Method method, RequestMapping requestMapping) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Method getMethod(String servletPath, HttpMethod httpMethod) {
        return this.map.get(servletPath).get(httpMethod);
    }

    @Override
    public Map<String, Map<HttpMethod, Method>> getMap() {
        return this.map;
    }

    @Override
    public String toString() {
        return "ApiMethodModifiableMap{" +
                "map=" + map +
                '}';
    }
}

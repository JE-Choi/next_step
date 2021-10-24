package webserver.servlet.annotation.domain;

import webserver.request.HttpMethod;
import webserver.servlet.annotation.RequestMapping;
import webserver.servlet.util.ApiAnnotationUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ApiMethodModifiableMap implements ApiMethodMap {
    private final Map<String, Map<HttpMethod, Method>> map;

    public ApiMethodModifiableMap(final Map<String, Map<HttpMethod, Method>> map) {
        this.map = map;
    }

    public Map<HttpMethod, Method> put (Method method, RequestMapping requestMapping){
        final String servletPath = ApiAnnotationUtils.getServletPath(method, requestMapping.method());
        map.computeIfAbsent(servletPath, (key) -> new HashMap<>()).put(requestMapping.method(), method);

        return map.get(servletPath);
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

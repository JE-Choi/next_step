package webserver.servlet.annotation.domain;

import webserver.request.HttpMethod;
import webserver.servlet.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Map;

public interface ApiMethodMap {
    Map<HttpMethod, Method> put (Method method, RequestMapping requestMapping);
    // Question: 불변객체로 변경할때 getMap이 필요함. (필드에 private로 해놨는데.. 있어도 되는건가..?)
    Map<String, Map<HttpMethod, Method>> getMap();
    Method getMethod(String servletPath, HttpMethod httpMethod) ;
}

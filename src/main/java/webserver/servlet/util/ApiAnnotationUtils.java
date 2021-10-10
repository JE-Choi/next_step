package webserver.servlet.util;

import util.StringUtils;
import webserver.request.HttpMethod;
import webserver.servlet.annotation.RequestMapping;
import webserver.servlet.annotation.RestController;

import java.lang.reflect.Method;

/**
 * Question: 인터페이스 만들고 싶었는데
 * static메소드를 선언해야 해서 구현체로 만듬..
 */
public class ApiAnnotationUtils {
    private ApiAnnotationUtils(){}

    public static String getClassValue(final Class<?> clazz) {
        return clazz.getAnnotation(RestController.class).value();
    }

    public static String getMethodValue(final Method method, final HttpMethod httpMethod) {
        RequestMapping[] requestMappings = method.getAnnotationsByType(RequestMapping.class);
        for (RequestMapping requestMapping : requestMappings) {
            /**
             * EX)
             * requestMapping.method() == [GET, POST]
             * httpMethod == GET
             */
            if (requestMapping.method() == httpMethod) {
                return requestMapping.value();
            }
        }
        return "";
    }

    public static String getServletPath(Method method, HttpMethod httpMethod) {
        return StringUtils.defaultStr(String.format("%s%s", getClassValue(method.getDeclaringClass()), getMethodValue(method, httpMethod)), "/");
    }
}

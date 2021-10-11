package webserver.servlet.annotation;

import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.request.RequestLine;
import webserver.servlet.util.ApiAnnotationUtils;

import java.lang.reflect.Method;
import java.util.*;

public class ApiReflections {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiReflections.class);
    private final static String WEB_SERVER_PACKAGE_NAME = "webserver";
    private final static Reflections reflections = new Reflections(
            new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(WEB_SERVER_PACKAGE_NAME))
                    .setScanners(new MethodAnnotationsScanner())
                    .filterInputsBy((String str) -> str != null && str.endsWith(".class"))
    );
    private final static Map<String, Map<HttpMethod, Method>> API_METHOD_MAP = init();

    private final static Map<String, Map<HttpMethod, Method>> init() {
        Map<String, Map<HttpMethod, Method>> map = new HashMap<>();
        // 컨테이너 어노테이션에 접근
        Set<Method> containerMethods = reflections.getMethodsAnnotatedWith(RequestMappings.class);
        for (Method method : containerMethods) {
            // 실 사용되는 어노테이션
            appendMethodMap(map, method);
        }

        // 실 사용되는 어노테이션 접근
        Set<Method> methods2 = reflections.getMethodsAnnotatedWith(RequestMapping.class);
        for (Method method : methods2) {
            appendMethodMap(map, method);
        }
        Map<String, Map<HttpMethod, Method>> result = Collections.unmodifiableMap(map);
        LOGGER.debug(result.toString());
        return result;
    }

    private static void appendMethodMap(Map<String, Map<HttpMethod, Method>> map, Method method) {
        RequestMapping[] annotationsByType = method.getAnnotationsByType(RequestMapping.class);
        for (final RequestMapping requestMapping : annotationsByType) {
            final String servletPath = ApiAnnotationUtils.getServletPath(method, requestMapping.method());
            map.computeIfAbsent(servletPath, (key) -> new HashMap<>()).put(requestMapping.method(), method);
        }
    }

    @Nullable
    public static Method findApiByRequest(final HttpRequest request) {
        final RequestLine requestLine = request.getRequestLine();
        try {
            return ApiReflections.API_METHOD_MAP.get(requestLine.getUri().getRequestUri()).get(requestLine.getMethod());
        } catch (NullPointerException e) {
            return null;
        }
    }
}

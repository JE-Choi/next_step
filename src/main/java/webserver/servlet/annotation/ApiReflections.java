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
        // Question: ImmutableMap하려니까 set도중에 get이 안됨.
        Map<String, Map<HttpMethod, Method>> map = new HashMap<>();
        // 컨테이너 어노테이션에 접근
        Set<Method> containerMethods = reflections.getMethodsAnnotatedWith(RequestMappings.class);
        for (Method method : containerMethods) {
            // 실 사용되는 어노테이션
            RequestMapping[] annotationsByType = method.getAnnotationsByType(RequestMapping.class);
            appendMethodMap(map, method, annotationsByType);
        }

        // 실 사용되는 어노테이션 접근
        Set<Method> methods2 = reflections.getMethodsAnnotatedWith(RequestMapping.class);
        for (Method method : methods2) {
            RequestMapping[] annotationsByType = method.getAnnotationsByType(RequestMapping.class);
            appendMethodMap(map, method, annotationsByType);
        }
        Map<String, Map<HttpMethod, Method>> result = Collections.unmodifiableMap(map);
        LOGGER.debug(result.toString());
        return result;
    }

    private static void appendMethodMap(Map<String, Map<HttpMethod, Method>> map, Method method, RequestMapping[] annotationsByType) {
        for (final RequestMapping requestMapping : annotationsByType) {
            final String servletPath = ApiAnnotationUtils.getServletPath(method, requestMapping.method());
            final Map<HttpMethod, Method> dataMap = Objects.nonNull(map.get(servletPath)) ? map.get(servletPath) : new HashMap<>();
            dataMap.put(requestMapping.method(), method);
            map.put(servletPath, dataMap);
        }
    }

    @Nullable
    public static Method findApiByRequest(final HttpRequest request) {
        final RequestLine requestLine = request.getRequestLine();
        try {
            return ApiReflections.API_METHOD_MAP.get(requestLine.getUrl()).get(requestLine.getMethod());
        } catch (NullPointerException e) {
            return null;
        }
    }
}

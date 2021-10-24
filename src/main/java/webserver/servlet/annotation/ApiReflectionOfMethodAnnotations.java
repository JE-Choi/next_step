package webserver.servlet.annotation;

import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.request.RequestLine;
import webserver.servlet.annotation.domain.ApiMethodMap;
import webserver.servlet.annotation.domain.ApiMethodModifiableMap;
import webserver.servlet.annotation.domain.ApiMethodUnmodifiableMap;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

public class ApiReflectionOfMethodAnnotations extends ApiReflectionAbstract {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiReflectionOfMethodAnnotations.class);
    private final static Reflections reflections = new Reflections(
            new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(WEB_SERVER_PACKAGE_NAME))
                    .setScanners(new MethodAnnotationsScanner())
                    .filterInputsBy((String str) -> str != null && str.endsWith(".class"))
    );
    private final static ApiMethodMap API_METHOD_MAP = init();

    private final static ApiMethodMap init() {
        final ApiMethodMap map = new ApiMethodModifiableMap(new HashMap<>());
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
        ApiMethodMap result = new ApiMethodUnmodifiableMap(map);
        LOGGER.debug(result.toString());
        return result;
    }

    private static void appendMethodMap(final ApiMethodMap map, Method method) {
        RequestMapping[] annotationsByType = method.getAnnotationsByType(RequestMapping.class);
        for (final RequestMapping requestMapping : annotationsByType) {
            map.put(method, requestMapping);
        }
    }

    @Nullable
    public Method findApiByRequest(final HttpRequest request) {
        final RequestLine requestLine = request.getRequestLine();
        try {
            return ApiReflectionOfMethodAnnotations.API_METHOD_MAP.getMethod(requestLine.getUri().getRequestUri(), requestLine.getMethod());
        } catch (NullPointerException e) {
            return null;
        }
    }
}

package webserver.servlet.annotation;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import webserver.request.HttpMethod;

import java.lang.reflect.Method;
import java.util.*;

import org.reflections.scanners.MethodAnnotationsScanner;
import webserver.servlet.util.ApiAnnotationUtils;

/**
 * https://advenoh.tistory.com/21
 * https://stackoverflow.com/questions/60559501/scanner-typeannotationsscanner-was-not-configured-when-used-from-a-referenced-l
 */
@Deprecated
public class MethodAnnotationExecutor {
    final static String WEB_SERVER_PACKAGE_NAME = "webserver";
    private final static Reflections reflections = new Reflections(
            new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(WEB_SERVER_PACKAGE_NAME))
                    .setScanners(new MethodAnnotationsScanner())
                    .filterInputsBy((String str) -> str != null && str.endsWith(".class"))
    );

    public static void main(String[] args) throws NoSuchMethodException {
        // Question: ImmutableMap하려니까 set도중에 get이 안됨.
//        final ImmutableMap.Builder<String, Map<HttpMethod, Method>> builder = ImmutableMap.builder();
        Map<String, Map<HttpMethod, Method>> map = new HashMap<>();
        // 컨테이너 어노테이션에 접근
        Set<Method> containerMethods = reflections.getMethodsAnnotatedWith(RequestMappings.class);
        for (Method method : containerMethods) {
            // 실 사용되는 어노테이션
            RequestMapping[] annotationsByType = method.getAnnotationsByType(RequestMapping.class);
            MethodAnnotationExecutor.appendMethodMap(map, method, annotationsByType);
        }

        // 실 사용되는 어노테이션 접근
        Set<Method> methods2 = reflections.getMethodsAnnotatedWith(RequestMapping.class);
        for (Method method : methods2) {
            RequestMapping[] annotationsByType = method.getAnnotationsByType(RequestMapping.class);
            MethodAnnotationExecutor.appendMethodMap(map, method, annotationsByType);
        }
        Map<String, Map<HttpMethod, Method>> result = Collections.unmodifiableMap(map);
        System.out.println(result);
    }

    private static void appendMethodMap(Map<String, Map<HttpMethod, Method>> map, Method method, RequestMapping[] annotationsByType) {
        for (final RequestMapping requestMapping : annotationsByType) {
            final String servletPath = ApiAnnotationUtils.getServletPath(method, requestMapping.method());
            final Map<HttpMethod, Method> dataMap = Objects.nonNull(map.get(servletPath)) ? map.get(servletPath) : new HashMap<>();
            dataMap.put(requestMapping.method(), method);
            map.put(servletPath, dataMap);
        }
    }
}

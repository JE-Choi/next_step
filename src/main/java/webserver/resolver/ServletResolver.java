package webserver.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.request.RequestWrapper;
import webserver.servlet.annotation.ApiReflection;
import webserver.servlet.annotation.ApiReflectionOfMethodAnnotations;

import java.lang.reflect.Method;

public class ServletResolver extends ViewResolverDefault {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletResolver.class);
    private static final ApiReflection API_REFLECTION = new ApiReflectionOfMethodAnnotations();
    private final Method method;
    private final String body;

    public ServletResolver(HttpRequest request) {
        super(request);
        this.method = API_REFLECTION.findApiByRequest(request);
        this.body = createBody();
    }

    private String createBody() {
        try {
             return this.method.invoke(method.getDeclaringClass().newInstance(), new RequestWrapper(request)).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return super.getBody();
    }

    @Override
    public String getBody() {
        return this.body;
    }

    @Override
    public byte[] getBodyBytes() {
        return getBody().getBytes();
    }
}

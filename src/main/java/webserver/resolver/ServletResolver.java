package webserver.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.request.RequestWrapper;
import util.StringUtils;
import webserver.servlet.annotation.ApiReflections;

import java.lang.reflect.Method;

public class ServletResolver extends ViewResolverDefault {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletResolver.class);
    private final Method method;
    private final String body;

    public ServletResolver(HttpRequest request) {
        super(request);
        this.method = ApiReflections.findApiByRequest(request);
        this.body = createBody();
    }

    private String createBody() {
        try {
            /**
             * Question: newInstance쓰지 말라는데, 사용하지 않는 순간 의존성? (https://jwdeveloper.tistory.com/44)
             */
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

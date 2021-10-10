package webserver.resolver;

import webserver.request.HttpRequest;
import webserver.request.RequestWrapper;
import webserver.servlet.annotation.ApiReflections;

import java.lang.reflect.Method;

public class ServletResolver extends ViewResolverDefault {
    private final Method method;
    public ServletResolver(HttpRequest request) {
        super(request);
        this.method = ApiReflections.findApiByRequest(request);
    }

    @Override
    public byte[] getBodyBytes() {
        try {
            /**
             * Question: newInstance쓰지 말라는데, 사용하지 않는 순간 의존성? (https://jwdeveloper.tistory.com/44)
             */
            this.method.invoke(method.getDeclaringClass().newInstance(), new RequestWrapper(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.getBodyBytes();
    }
}

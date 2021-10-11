package webserver.resolver;

import webserver.request.HttpRequest;
import webserver.servlet.annotation.ApiReflections;

import java.lang.reflect.Method;
import java.util.Objects;

public class ViewResolverFactoryDefault implements ViewResolverFactory {
    @Override
    public ViewResolver create(HttpRequest request) {
        /**
         * https://mangkyu.tistory.com/18
         * Dispatcher Servlet이 모든 요청을 처리하다보니
         * 이미지나 HTML/CSS/JavaScript 등과 같은 정적 파일에 대한 요청마저 모두 가로채는 까닭에 정적자원(Static Resources)을 불러오지 못하는 상황 발생가능
         * 해결1) 정적 자원에 대한 요청과 애플리케이션에 대한 요청을 분리
         * 해결2) (✔) 애플리케이션에 대한 요청을 탐색하고 없으면 정적 자원에 대한 요청으로 처리
         */
        final Method apiByRequest = ApiReflections.findApiByRequest(request);
        // 애플리케이션에 대한 요청이 있으면
        if(Objects.nonNull(apiByRequest)){
            return new ServletResolver(request);
        }
        // 애플리케이션에 대한 요청이 없으면
        return new ResourceViewResolver(request);
    }
}

package webserver.response;

import webserver.resolver.ViewResolver;

import java.io.DataOutputStream;

public class HttpResponseDefault extends HttpResponse {
    public HttpResponseDefault(ViewResolver viewResolver, DataOutputStream dos) {
        super(viewResolver, dos);
    }
}

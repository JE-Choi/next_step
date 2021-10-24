package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringUtils;
import webserver.cookie.CookieKey;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestDefault;
import webserver.resolver.ViewResolver;
import webserver.resolver.ViewResolverFactory;
import webserver.resolver.ViewResolverFactoryDefault;
import webserver.response.HttpResponseDefault;
import webserver.response.Response;
import webserver.session.HttpSessions;
import webserver.session.Session;

/**
 * Note: 참고할  RequestHandler: com.sun.tools.sjavac.server.RequestHandler
 */
public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final ViewResolverFactory viewResolverFactory = new ViewResolverFactoryDefault();

    private Socket socket;

    public RequestHandler(Socket connectionSocket) {
        this.socket = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", socket.getInetAddress(),
                socket.getPort());
        // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.defaultCharset()));
             OutputStream out = socket.getOutputStream()) {
            // 요청 분석
            final HttpRequest request = createHttpRequest(bufferedReader);
            log.info(request.toString());
            // 응답 생성
            sendResponse(out, request);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private HttpRequest createHttpRequest(BufferedReader bufferedReader) throws IOException {
        return new HttpRequestDefault(bufferedReader);
    }

    private void sendResponse(final OutputStream out, final HttpRequest request) {
        final DataOutputStream dos = new DataOutputStream(out);
        final ViewResolver viewResolver = viewResolverFactory.create(request);

        final Response response = new HttpResponseDefault(viewResolver, dos);
        final Session session = HttpSessions.createSession();

        if (StringUtils.isEmpty(request.getCookies().getCookie(CookieKey.JSESSIONID))) {
              /**
             * Todo: CookieValue객체화해서, path, max-age같은거 설정할 수 있게끔.
             * https://ko.javascript.info/cookie
             * Max-Age: 1시간 뒤에 쿠키가 삭제됨.
             * httpOnly: 이 옵션은 자바스크립트 같은 클라이언트 측 스크립트가 쿠키를 사용할 수 없게 합니다
             */
            response.addHeader("Set-Cookie", "JSESSIONID=" + session.getId() + "; Path=/; Max-Age=" + 3600+"; Samesite=Strict; HttpOnly;");
        }
        response.addHeader("Content-Type", viewResolver.getContentType() + ";charset=" + viewResolver.getCharset());
        response.addHeader("Content-Length", String.valueOf(viewResolver.getBodyBytes().length));
        response.sendResponse();
    }
}

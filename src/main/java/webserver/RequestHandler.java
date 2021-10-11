package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestDefault;
import webserver.resolver.ViewResolver;
import webserver.resolver.ViewResolverFactory;
import webserver.resolver.ViewResolverFactoryDefault;

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
        DataOutputStream dos = new DataOutputStream(out);
        ViewResolver viewResolver = viewResolverFactory.create(request);
        response200Header(dos, viewResolver);
        responseBody(dos, viewResolver);
    }

    private void response200Header(final DataOutputStream dos, final ViewResolver viewResolver) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + viewResolver.getContentType() + ";charset=" + viewResolver.getCharset() + "\r\n");
            dos.writeBytes("Content-Length: " + viewResolver.getBodyBytes().length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, final ViewResolver viewResolver) {
        try {
            dos.write(viewResolver.getBodyBytes(), 0, viewResolver.getBodyBytes().length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

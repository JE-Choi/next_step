package webserver.request;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Note: 고민해야 할 부분
 * - post요청은 어떻게 테스트? => Body값을 어떻게 테스트코드로 만들지?
 */
public class HttpRequestDefaultTest {
    StringJoiner getRequest = new StringJoiner("\n");

    @Before
    public void getRequestInit() throws Exception {
        this.getRequest.add("GET /user/create?userId=userId&password=1234&name=userName&email=name1%40aa.com HTTP/1.1");
        this.getRequest.add("Host: localhost:8090");
        this.getRequest.add("Connection: keep-alive");
        this.getRequest.add("sec-ch-ua: \"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"");
        this.getRequest.add("sec-ch-ua-mobile: ?0");
    }

    @Test
    public void get요청_테스트() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(this.getRequest.toString().getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        HttpRequest httpRequestDefault = new HttpRequestDefault(bufferedReader);

        // RequestLine 검증
        RequestLine requestLine = httpRequestDefault.getRequestLine();
        Assert.assertEquals(HttpMethod.GET, requestLine.getMethod());
        Assert.assertEquals("/user/create", requestLine.getUri().getRequestUri());
        Assert.assertEquals(new HashMap() {{
            put("userId", "userId");
            put("password", "1234");
            put("name", "userName");
            put("email", "name1%40aa.com");
        }}, requestLine.getUri().getQueryString());
        Assert.assertEquals("HTTP/1.1", requestLine.getHttpVersion());

        // RequestHeader 검증
        RequestHeader requestHeader = httpRequestDefault.getRequestHeader();
        Assert.assertEquals("keep-alive", requestHeader.get("Connection"));
        Assert.assertEquals("localhost:8090", requestHeader.get("Host"));

        // requestBody 검증
        Map<String, String> requestBody = httpRequestDefault.getRequestBody();
        Assert.assertEquals(0, requestBody.size());
    }
}
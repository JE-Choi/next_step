package webserver.request;

import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class URI {
    private final String requestUri;
    private final Map<String, String> queryString;

    public URI(String str) {
        String[] urlSplits = str.split("\\?");
        this.requestUri = urlSplits[0];
        this.queryString = urlSplits.length > 1 ? HttpRequestUtils.parseQueryString(urlSplits[1]) : new HashMap<>();
    }

    public String getRequestUri() {
        return requestUri;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }
}

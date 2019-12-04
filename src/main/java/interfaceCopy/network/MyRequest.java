package interfaceCopy.network;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class MyRequest {
    public enum Method {
        POST, GET
    }

    public enum PostMethod {
        URL_ENCODED, FORM_DATA, RAW, BINARY
    }

    private String url;
    private String scheme = "";
    private String host = "";
    private String path = "";
    private String port = "";
    private String tag = "";
    private String requestCharset = "utf-8";
    private Map<String, String> headerMap = new HashMap<>();
    private Map<String, String> map = new HashMap<>();
    private Method method = Method.GET;
    private PostMethod postMethod = PostMethod.URL_ENCODED;
    private Map<String, String> commonHeaderMap = new HashMap<>();

    public String getScheme() {
        return scheme;
    }

    public MyRequest setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public String getHost() {
        return host;
    }

    public MyRequest setHost(String host) {
        this.host = host;
        return this;
    }

    public String getPath() {
        return path;
    }

    public MyRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public String getPort() {
        return port;
    }

    public MyRequest setPort(String port) {
        this.port = port;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public MyRequest setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getRequestCharset() {
        return requestCharset;
    }

    public MyRequest setRequestCharset(String requestCharset) {
        this.requestCharset = requestCharset;
        return this;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public PostMethod getPostMethod() {
        return postMethod;
    }

    public void setPostMethod(PostMethod postMethod) {
        this.postMethod = postMethod;
    }

    public Map<String, String> getCommonHeaderMap() {
        return commonHeaderMap;
    }

    public void setCommonHeaderMap(Map<String, String> commonHeaderMap) {
        this.commonHeaderMap = commonHeaderMap;
    }

    public Request buildRequest() throws IOException {
        String urlWithoutToken = scheme + "://" + host + ":" + port + path;
        Request.Builder builder = new Request.Builder();
        if (method == Method.GET) {
            for (String key : map.keySet()) {
                map.put(key, URLEncoder.encode(map.get(key), "utf-8"));
            }
            String token = getTokenByMap();
            System.out.println("token:" + token);
            url = urlWithoutToken + "?" + token;
            builder.get();
        }
        if (method == Method.POST) {
            url = urlWithoutToken;
            RequestBody requestBody = null;
            switch (postMethod) {
                case URL_ENCODED:
                    FormBody.Builder formBody = new FormBody.Builder(Charset.forName(requestCharset));
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        formBody.add(entry.getKey(), entry.getValue());
                    }
                    requestBody = formBody.build();
                    break;
                default:
                    throw new RuntimeException("no such postMethod:" + postMethod);
            }
            builder.post(requestBody);
        }
        builder.url(url);
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, String> entry : commonHeaderMap.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    private String getTokenByMap() {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            str.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String s = str.toString();
        return s.substring(0, s.length() - 1);
    }
}

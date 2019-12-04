package interfaceCopy.test.process;

import interfaceCopy.network.MyRequest;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSONHttpRequestProcessor {
    protected MyRequest parse(String requestJsonString) {
        JSONObject requestJson = new JSONObject(requestJsonString);
        String scheme = requestJson.getString("scheme");
        String host = requestJson.getString("host");
        String path = requestJson.getString("path");
        String port = requestJson.getString("port");
        String tag = requestJson.getString("tag");
        String requestCharset = requestJson.getString("requestCharset");

        MyRequest myRequest = new MyRequest();
        myRequest.setScheme(scheme).
                setHost(host).
                setPath(path).
                setPort(port).
                setTag(tag).
                setRequestCharset(requestCharset);

        String method = requestJson.getString("method");
        String postMethod = requestJson.getString("post_method");

        //header
        Map<String, String> headers = new HashMap<>();
        myRequest.setHeaderMap(headers);
        if (requestJson.has("headers")) {
            JSONObject object = requestJson.getJSONObject("headers");
            for (String s : object.keySet()) {
                headers.put(s, object.getString(s));
            }
        }

        //url_encoded
        Map<String, String> map = new HashMap<>();
        myRequest.setMap(map);
        if (requestJson.has("textBody")) {
            JSONObject object = requestJson.getJSONObject("textBody");
            for (String s : object.keySet()) {
                map.put(s, object.getString(s));
            }
        }

        //multiMap.form.raw

        switch (method) {
            case "get":
                myRequest.setMethod(MyRequest.Method.GET);
                break;
            case "post":
                myRequest.setMethod(MyRequest.Method.POST);
                switch (postMethod.toLowerCase()) {
                    case "url_encoded":
                        myRequest.setPostMethod(MyRequest.PostMethod.URL_ENCODED);
                        break;
                    default:
                        throw new RuntimeException("post method not supported :" + postMethod);
                }
                break;
            default:
                throw new RuntimeException("request method not supported :" + method);
        }
        return myRequest;
    }
}

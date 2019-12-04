package interfaceCopy.test.process;

import interfaceCopy.network.MyHttpClient;
import interfaceCopy.network.MyRequest;
import interfaceCopy.network.MyResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListJSONHttpRequestPrePostExceptionProcessor extends JSONHttpPrePostExceptionProcessor implements IDeliverableHttpRequestProcessor {
    public static final String AUTO_PUT_REGEX = "(\\w+|@)\\.(\\w+)";
    private String requestJsonString;
    private Class fileSourceClass;
    private Map<String, String> deliverMap = new HashMap<>();

    public ListJSONHttpRequestPrePostExceptionProcessor(String requestJsonString, IHttpPrePostExceptionCallback rulePrePostCallBack) {
        super(rulePrePostCallBack);
        this.requestJsonString = requestJsonString;
        this.fileSourceClass = ListJSONHttpRequestPrePostExceptionProcessor.class;
    }

    @Override
    public void addDeliver(Map<String, String> deliver) {
        this.deliverMap.putAll(deliver);
    }

    @Override
    public void start() {
        MyResponse response = null;
        JSONObject jsonObject = new JSONObject(requestJsonString);
        JSONArray array = jsonObject.getJSONArray("requests");
        for (int i = 0; i < array.length(); i++) {
            JSONObject requestJson = array.getJSONObject(i);
            String jsonAfterParse = rulePrePostCallBack.preParse(requestJson.toString());
            MyRequest request = super.parse(jsonAfterParse);

            replace(request, deliverMap);

            rulePrePostCallBack.pre(request, response, deliverMap);

            try {
                response = MyHttpClient.getInstance().request(request);
            } catch (IOException e) {
                rulePrePostCallBack.exception(request, e, deliverMap);
            }

            rulePrePostCallBack.post(request, response, deliverMap);
        }
    }

    private void replace(MyRequest request, Map<String, String> deliverMap) {
        String tag = request.getTag();
        Pattern patternData = Pattern.compile(AUTO_PUT_REGEX);
//        deliverMap.put("@.test", "test");

        for (Map.Entry<String, String> entry : deliverMap.entrySet()) {
            Matcher matcher = patternData.matcher(entry.getKey());
            if (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                if (key.equals("@") || key.equals(tag)) {
                    request.getMap().put(value, entry.getValue());
                }
            }
        }
    }
}

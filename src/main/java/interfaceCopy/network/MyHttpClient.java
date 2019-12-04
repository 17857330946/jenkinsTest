package interfaceCopy.network;

import interfaceCopy.data.IByteDataSource;
import interfaceCopy.data.file.StringPathFileByteDataSource;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyHttpClient {
    private long connectTimeout = 6;
    private long writeTimeout = 6;
    private long readTimeout = 6;
    private Map<String, String> commonHeader = new HashMap<>();

    private static MyHttpClient instance = new MyHttpClient();

    private MyHttpClient() {
        init();
    }

    public static MyHttpClient getInstance() {
        return instance;
    }

    private void init() {
        IByteDataSource byteDataSource = new StringPathFileByteDataSource(String.format("classpath:%s/netconfig.json", "config"), this.getClass());
        try {
            String json = new String(byteDataSource.getData(), "utf-8");
            JSONObject netConfig = new JSONObject(json);
            connectTimeout = netConfig.getLong("connectTimeout");
            writeTimeout = netConfig.getLong("writeTimeout");
            readTimeout = netConfig.getLong("readTimeout");
            JSONObject commonHeaders = netConfig.getJSONObject("commonHeaders");
            for (String s : commonHeaders.keySet()) {
                this.commonHeader.put(s, commonHeaders.getString(s));
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private ThreadLocal<OkHttpClient> okHttpClient = ThreadLocal.withInitial(() -> {
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(connectTimeout, TimeUnit.SECONDS).
                writeTimeout(writeTimeout, TimeUnit.SECONDS).
                readTimeout(readTimeout, TimeUnit.SECONDS).build();
        return client;
    });

    public MyResponse request(MyRequest request) throws IOException {
        request.setCommonHeaderMap(commonHeader);
        Call call = this.okHttpClient.get().newCall(request.buildRequest());
        Response response = call.execute();
        return new MyResponse(response);
    }
}

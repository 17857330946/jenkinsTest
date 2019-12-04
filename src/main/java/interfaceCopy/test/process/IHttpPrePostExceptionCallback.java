package interfaceCopy.test.process;

import interfaceCopy.network.MyRequest;
import interfaceCopy.network.MyResponse;

import java.util.Map;

public interface IHttpPrePostExceptionCallback {
    String preParse(String source);

    void pre(MyRequest request, MyResponse response, Map<String, String> deliver);

    void post(MyRequest request, MyResponse response, Map<String, String> deliver);

    void exception(MyRequest request, Exception e, Map<String, String> deliver);
}

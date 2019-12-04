package interfaceCopy.test;

import interfaceCopy.network.MyRequest;
import interfaceCopy.network.MyResponse;
import interfaceCopy.test.process.IHttpPrePostExceptionCallback;

import java.util.Map;

public class CommonTest extends ClassLoadFileBase implements IHttpPrePostExceptionCallback {

    @Override
    public String preParse(String source) {
        return source;
    }

    @Override
    public void pre(MyRequest request, MyResponse response, Map<String, String> deliver) {

    }

    @Override
    public void post(MyRequest request, MyResponse response, Map<String, String> deliver) {

    }

    @Override
    public void exception(MyRequest request, Exception e, Map<String, String> deliver) {
        throw new RuntimeException(e.getMessage(), e);
    }
}

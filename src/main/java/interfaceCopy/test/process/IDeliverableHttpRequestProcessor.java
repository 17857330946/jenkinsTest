package interfaceCopy.test.process;

import java.util.Map;

public interface IDeliverableHttpRequestProcessor {
    void addDeliver(Map<String, String> deliver);

    void start();
}

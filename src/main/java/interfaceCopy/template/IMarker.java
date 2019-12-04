package interfaceCopy.template;

import java.util.Map;

public interface IMarker {
    /**
     * 默认原样返回
     */
    String mark(String source, Map<String, Object> map) throws Throwable;
}

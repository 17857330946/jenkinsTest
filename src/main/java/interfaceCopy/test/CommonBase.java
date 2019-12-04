package interfaceCopy.test;

import interfaceCopy.data.annotation.IgnoreNamedParam;
import interfaceCopy.data.annotation.NamedParam;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class CommonBase extends Base {
    private Map<String, String> allParams = new HashMap<>();
    private ThreadLocal<Map<String, String>> params = ThreadLocal.withInitial(() -> new HashMap<>());

    @BeforeClass
    protected void beforeClassGetAllParam(ITestContext context) {
        allParams = context.getCurrentXmlTest().getAllParameters();
    }

    public Map<String, String> getAllParams() {
        return allParams;
    }

    //这是每个用例用来获取参数的方法
    @BeforeMethod
    protected void beforeMethodGetParam(Method method, Object[] objects) {
        Map<String, String> temp = new HashMap<>();
        Parameter[] parameters = method.getParameters();

        if (parameters == null) {
            return;
        } else {
            for (int i = 0; i < parameters.length; i++) {
                boolean ignore = false;
                String name = parameters[i].getName();
                Annotation[] annotations = parameters[i].getAnnotations();
                if (annotations.length == 0) {
                    return;
                } else {
                    for (Annotation annotation : annotations) {
                        if (annotation.annotationType() == NamedParam.class) {
                            ignore = false;
                            NamedParam named = (NamedParam) annotation;
                            name = named.value();
                        }
                        if (annotation.annotationType() == IgnoreNamedParam.class) {
                            ignore = true;
                        }
                    }
                }
                if (!ignore) {
                    temp.put(name, objects[i].toString());
                }
                params.set(temp);
            }
        }
    }

    public Map<String, String> getParams() {
        return params.get();
    }
}

package interfaceCopy;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import interfaceCopy.data.annotation.ByteDataSource;
import interfaceCopy.data.annotation.CsvDataProvider;
import interfaceCopy.data.annotation.IgnoreNamedParam;
import interfaceCopy.data.annotation.NamedParam;
import interfaceCopy.data.dataProvider.DataProviders;
import interfaceCopy.guice.module.factory.ByteDataSourceModuleFactory;
import interfaceCopy.network.MyRequest;
import interfaceCopy.network.MyResponse;
import interfaceCopy.template.IMarker;
import interfaceCopy.test.CommonTest;
import interfaceCopy.test.process.IDeliverableHttpRequestProcessor;
import interfaceCopy.test.process.ListJSONHttpRequestPrePostExceptionProcessor;
import io.qameta.allure.Description;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ByteDataSource(filePath = "classpath:test/request/request.json", charset = "utf-8")
@Guice(moduleFactory = ByteDataSourceModuleFactory.class)
public class Copy extends CommonTest {
    @Inject
    @Named("Freemarker")
    IMarker marker;

    @Description("copy allure")
    @CsvDataProvider(path = "classpath:test/request/data.csv", charset = "utf-8")
    @Test(dataProvider = "csv", dataProviderClass = DataProviders.class, description = "copy")
    public void copy(@NamedParam("mid") String mid,
                     @NamedParam("tip") String tip) {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> params = super.getParams();
        System.out.println("params:" + params);
        Map<String, String> allParams = super.getAllParams();
        System.out.println("allParams:" + allParams);
        map.putAll(params);
        map.putAll(allParams);
        String jsonContent = getJsonFileContent();
        try {
            String jsonMark = marker.mark(jsonContent, map);
//            System.out.println("jsonMark:" + jsonMark);
            IDeliverableHttpRequestProcessor prePostExceptionProcessor = new ListJSONHttpRequestPrePostExceptionProcessor(jsonMark, this);
            prePostExceptionProcessor.addDeliver(super.getAllParams());
            prePostExceptionProcessor.start();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }

    @Override
    public void post(MyRequest request, MyResponse response, Map<String, String> deliver) {
        try {
            String res = response.string("utf-8");
            System.out.println("res:" + res);
        } catch (IOException e) {
            throw new RuntimeException("response string() error");
        }
    }
}

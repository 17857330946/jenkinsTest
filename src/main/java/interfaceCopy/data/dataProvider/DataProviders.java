package interfaceCopy.data.dataProvider;

import interfaceCopy.data.ITestDataSource;
import interfaceCopy.data.annotation.CsvDataProvider;
import interfaceCopy.data.file.CsvFileTestDataSource;
import interfaceCopy.data.file.StringPathFileByteDataSource;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Iterator;

public class DataProviders {
    @DataProvider(name = "csv")
    public static Iterator<Object[]> csvData(Method method) throws Throwable {
        CsvDataProvider csvDataProvider = method.getAnnotation(CsvDataProvider.class);
        if (csvDataProvider == null) {
            throw new RuntimeException("csv文件不存在");
        }
        String path = csvDataProvider.path();
        Class cls = csvDataProvider.cls();
        String charset = csvDataProvider.charset();
        ITestDataSource iTestDataSource = new CsvFileTestDataSource(new StringPathFileByteDataSource(path, cls), charset);
        return iTestDataSource.getData();
    }
}

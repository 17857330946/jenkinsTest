package interfaceCopy.data;

import java.util.Iterator;

public interface ITestDataSource {
    Iterator<Object[]> getData() throws Throwable;
}

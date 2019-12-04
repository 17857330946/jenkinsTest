package interfaceCopy.test;

import interfaceCopy.data.IByteDataSource;
import interfaceCopy.data.annotation.ByteDataSource;
import interfaceCopy.data.file.StringPathFileByteDataSource;
import org.testng.annotations.BeforeClass;

public class ClassLoadFileBase extends CommonBase {
    private String content;

    @BeforeClass
    protected void classLoadJsonFileBase() throws Throwable {
        ByteDataSource byteDataSource = this.getClass().getAnnotation(ByteDataSource.class);
        if (byteDataSource == null) {
            throw new RuntimeException("类如果继承ClassLoadFileBase，必须含有ByteDataSource注解");
        }

        String filePath = byteDataSource.filePath();
        Class cls = byteDataSource.cls();
        String charset = byteDataSource.charset();

        IByteDataSource iByteDataSource = new StringPathFileByteDataSource(filePath, cls);
        content = new String(iByteDataSource.getData(), charset);
    }

    public String getJsonFileContent() {
        return content;
    }
}

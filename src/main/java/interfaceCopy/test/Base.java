package interfaceCopy.test;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

public class Base {
    private String key;

    @BeforeClass
    protected void beforeClassGetKey(ITestContext context) {
        this.key = context.getSuite().getName();
    }

    public String getKey() {
        return key;
    }
}

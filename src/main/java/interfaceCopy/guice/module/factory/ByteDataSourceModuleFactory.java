package interfaceCopy.guice.module.factory;

import com.google.inject.Module;
import interfaceCopy.guice.module.ByteDataSourceModule;
import org.testng.IModuleFactory;
import org.testng.ITestContext;

public class ByteDataSourceModuleFactory implements IModuleFactory {
    @Override
    public Module createModule(ITestContext context, Class<?> testClass) {
        ByteDataSourceModule module = new ByteDataSourceModule();
        return module;
    }
}

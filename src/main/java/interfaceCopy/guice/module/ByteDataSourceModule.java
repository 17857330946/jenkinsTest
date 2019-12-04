package interfaceCopy.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import interfaceCopy.template.IMarker;
import interfaceCopy.template.freemark.FreemarkerHelper;

//把原有InstallCommonModule省略
public class ByteDataSourceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IMarker.class).annotatedWith(Names.named("Freemarker")).toInstance(new FreemarkerHelper());
    }
}

package interfaceCopy.template.freemark;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import interfaceCopy.template.IMarker;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/*
替换方法的实现类
 */
public class FreemarkerHelper implements IMarker {

    @Override
    public String mark(String source, Map<String, Object> map) throws IOException, TemplateException {
        Configuration cfg = MyFreemarkerConfiguration.getInstance().getConfiguration();
        Template template = new Template("mark", source, cfg);
        Writer stringWriter = new StringWriter();
        template.process(map, stringWriter);
        return stringWriter.toString();
    }
}

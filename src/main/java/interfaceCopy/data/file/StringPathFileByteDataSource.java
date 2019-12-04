package interfaceCopy.data.file;

import interfaceCopy.data.IByteDataSource;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringPathFileByteDataSource implements IByteDataSource {

    public static final String pattern = "^(\\w+):([:\\w/\\\\.]+)$";

    private Optional<String> path;
    private Optional<Class> cls;

    public StringPathFileByteDataSource(String path, Class cls) {
        this.path = Optional.of(path);
        this.cls = Optional.of(cls);
    }

    @Override
    public byte[] getData() throws Throwable {
        if (!path.isPresent()) {
            throw new RuntimeException("path is null;");
        }
        if (!cls.isPresent()) {
            throw new RuntimeException("cls is null;");
        }
        String pathData = path.get();
        Pattern patternData = Pattern.compile(pattern);

        Matcher matcher = patternData.matcher(pathData);
        boolean match = matcher.find();
        if (!match) {
            throw new RuntimeException("path is illegal;");
        }
        switch (matcher.group(1)) {
            case "classpath":
                String classpath = matcher.group(2);
                if (!classpath.startsWith("/")) {
                    classpath = "/" + classpath;
                }
                InputStream temp = cls.get().getResourceAsStream(classpath);
                if (temp == null) {
                    throw new RuntimeException("stream is null;");
                }
                byte[] data = IOUtils.toByteArray(temp);
                IOUtils.closeQuietly(temp);
                return data;
            default:
                throw new RuntimeException("not supported;");
        }
    }
}

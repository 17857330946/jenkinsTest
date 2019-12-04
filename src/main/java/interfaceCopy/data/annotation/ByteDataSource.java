package interfaceCopy.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ByteDataSource {
    String filePath();
    Class cls() default ByteDataSource.class;
    String charset() default "utf-8";
}

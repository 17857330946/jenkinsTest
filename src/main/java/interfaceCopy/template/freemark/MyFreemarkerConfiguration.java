package interfaceCopy.template.freemark;


import freemarker.template.Configuration;

public class MyFreemarkerConfiguration {
    private static MyFreemarkerConfiguration ourInstance = new MyFreemarkerConfiguration();
    private Configuration configuration;

    private MyFreemarkerConfiguration() {
        configuration = initialValue();
    }

    public static MyFreemarkerConfiguration getInstance() {
        return ourInstance;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    private Configuration initialValue() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setDefaultEncoding("utf-8");
        return cfg;
    }
}

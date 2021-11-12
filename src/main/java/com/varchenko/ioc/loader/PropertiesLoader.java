package com.varchenko.ioc.loader;


import java.io.*;
import java.util.Map;
import java.util.Properties;

public class PropertiesLoader {
    private static final String PATH = "src/main/resources";
    private static final String ENV = "API";

    public static Property getEntityProperty() throws FileNotFoundException {
        Property property = new Property();
        InputStream is = new FileInputStream(getRightPath());
        property.setReflections(loadProperty(is).getProperty(Keys.REFLECTIONS.getProperties()));
        return property;
    }

    public static String readEnv(String name) {
        Map<String, String> getenv = System.getenv();
        return getenv.get(name);
    }

    private static Properties loadProperty(InputStream reader) {
        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static String getRightPath() {
        return PATH + "/" + "application-" + readEnv(ENV) + ".yml";
    }
}

package com.ibm.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum Config {

    DB_USERNAME("db.username", "admin"),
    DB_PASSWORD("db.password", "admin"),
    DB_FILE_PATH("db.file.path", "./my.db"),
    SERVER_PORT("server.port", "8080"),
    H2_WEB_PORT("h2.web.port", "8082"),
    ;

    private static final String PROPERTIES_FILE = "app.properties";

    private final String key;
    private final String defaultValue;

    Config(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String value() {
        String envValue = System.getenv(key.toUpperCase().replace('.', '_'));
        if (envValue != null) {
            return envValue;
        }

        File propertiesFile = new File(PROPERTIES_FILE);

        if (propertiesFile.exists()) {
            try (InputStream input = new FileInputStream(propertiesFile)) {
                Properties prop = new Properties();
                prop.load(input);
                if (prop.containsKey(key)) {
                    return prop.getProperty(key);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Failed to read config property: " + key, ex);
            }
        } else {
            System.out.println("Properties file not found: " + PROPERTIES_FILE + ", using default values where applicable.");
        }

        return defaultValue;
    }

}

package com.internship.aston_project.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {

    private final Properties properties = new Properties();

    public PropertiesLoader(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + filePath, e);
        }
    }

    public String getFilePath(String key) {
        return properties.getProperty(key);
    }
}

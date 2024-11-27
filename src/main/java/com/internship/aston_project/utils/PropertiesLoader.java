package com.internship.aston_project.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {

    private static final Properties properties = new Properties();

    public PropertiesLoader() {
        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    public static String getFilePath(String key) {
        return properties.getProperty(key);
    }

    public static String getAddressBasedOnType(String type) {
        switch (type) {
            case "1":
                return getFilePath("bus.file.path");
            case "2":
                return getFilePath("student.file.path");
            case "3":
                return getFilePath("user.file.path");
            default:
                return "Ошибка введённых данных, попробуйте снова";
        }
    }
}

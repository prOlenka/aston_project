package com.internship.aston_project.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить файл свойств", e);
        }
    }

    public static String getAddressBasedOnType(String type) {
        return switch (type) {
            case "1" -> properties.getProperty("bus.file.path");
            case "2" -> properties.getProperty("student.file.path");
            case "3" -> properties.getProperty("user.file.path");
            default -> "Ошибка введённых данных, попробуйте снова";
        };
    }
}
package com.internship.aston_project.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private static final Properties properties = new Properties();

    // Статический блок для загрузки конфигурации из файла application.properties
    static {
        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(fis);  // Загружает все свойства из файла
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить файл свойств", e);  // Обработка ошибок
        }
    }

    // Метод для получения пути к файлу, в зависимости от типа фабрики
    public static String getAddressBasedOnType(String type) {
        return switch (type) {
            case "BusFactory" -> properties.getProperty("bus.file.path");  // Путь к файлу для BusFactory
            case "StudentFactory" -> properties.getProperty("student.file.path");  // Путь к файлу для StudentFactory
            case "UserFactory" -> properties.getProperty("user.file.path");  // Путь к файлу для UserFactory
            default -> "Ошибка введённых данных, попробуйте снова";  // Если тип неизвестен, вернуть ошибку
        };
    }
}

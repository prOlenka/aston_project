package com.internship.aston_project.utils;

import com.internship.aston_project.factory.BusFactory;
import com.internship.aston_project.factory.StudentFactory;
import com.internship.aston_project.factory.UserFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtils {
    // Чтение данных из файла
    public static List<String> readFile(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "Путь к файлу не может быть null");
        return Files.readAllLines(Paths.get(filePath));  // Читает все строки из файла
    }

    // Запись данных в файл
    public static void writeToFile(String filePath, List<String> lines) throws IOException {
        Files.write(Paths.get(filePath), lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);  // Записывает строки в файл
    }

    // Формирование строки для записи в зависимости от типа объекта
    public static String filePrefix(String type, String first, String second, String third) {
        return switch (type) {
            case "Bus" -> "Number: " + first + ", Model: " + second + ", Mileage: " + third;
            case "Student" -> "GroupNumber: " + first + ", AverageGrade: " + second + ", GradeBookNumber: " + third;
            case "User" -> "Name: " + first + ", Email: " + second + ", Password: " + third;
            default -> "Ошибка введённых данных, попробуйте снова";  // В случае некорректных данных
        };
    }

    // Подготовка и запись данных в файл
    public static <T> void prepareAndWrite(String filePath, String type, List<T> data) throws IOException {
        List<String> formattedLines = new ArrayList<>();

        for (T item : data) {
            String[] parts = item.toString().split(",\\s*");
            if (parts.length >= 3) {
                // Формирование строки на основе типа объекта
                formattedLines.add(filePrefix(
                        type.equals("BusFactory") ? "Bus" :
                                type.equals("StudentFactory") ? "Student" : "User",
                        parts[0].trim(), parts[1].trim(), parts[2].trim()
                ));
            }
        }

        writeToFile(filePath, formattedLines);  // Запись сформированных строк в файл
    }

    // Разбор строки по типу
    public static String parseLineByType(String line, String type) {
        return switch (type) {
            case "BusFactory" -> new BusFactory().parse(line);  // Разбор строки как Bus
            case "StudentFactory" -> new StudentFactory().parse(line);  // Разбор строки как Student
            case "UserFactory" -> new UserFactory().parse(line);  // Разбор строки как User
            default -> throw new IllegalArgumentException("Неизвестный тип: " + type);  // Исключение для неизвестного типа
        };
    }
}

package com.internship.aston_project.utils;

import com.internship.aston_project.factory.BusFactory;
import com.internship.aston_project.factory.StudentFactory;
import com.internship.aston_project.factory.UserFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

//Класс для ввода/вывода данных
public class FileUtils {
    public static List<String> readFile(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "Путь к файлу не может быть null");
        return Files.readAllLines(Paths.get(filePath));
    }

    public static void writeToFile(String filePath, List<String> lines) throws IOException {
        Files.write(Paths.get(filePath), lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static String filePrefix(String type, String first, String second, String third) {
        switch (type) {
            case "Bus":
                return "Number: " + first +
                        ", Model: " + second +
                        ", Mileage: " + third;
            case "Student":
                return "GroupNumber: " + first +
                        ", AverageGrade: " + second +
                        ", GradeBookNumber: " + third;
            case "User":
                return "Name: " + first +
                        ", Email: " + second +
                        ", Password: " + third;
            default:
                return "Ошибка введённых данных, попробуйте снова";
        }
    }

    // Метод для подготовки данных и записи в файл
    public static void prepareAndWrite( String type, String filePath, List<List<String>> data) throws IOException {
        for (List<String> entry : data) {
            String formattedLine = filePrefix(type, entry.get(0), entry.get(1), entry.get(2));
            writeToFile(filePath, List.of(formattedLine));
        }
    }

    public static String parseLineByType(String line, String type) {
        return switch (type) {
            case "1" -> new BusFactory().parse(line);
            case "2" -> new StudentFactory().parse(line);
            case "3" -> new UserFactory().parse(line);
            default -> throw new IllegalArgumentException("Неизвестный тип: " + type);
        };
    }
}
package com.internship.aston_project.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

//Класс для ввода/вывода данных
public class FileUtils {
    public static List<String> readFile(String type , String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    public static void writeToFile(String type, String data, String filePath) throws IOException {
        Files.write(Paths.get(filePath), data.getBytes(), StandardOpenOption.APPEND);
    }

    public String filePrefix(String type, String first, String second, String third) throws IOException {
        switch (type) {
            case "Bus":
                return "Number: " + first +
                        ", Model: " + second + '\'' +
                        ", Mileage: " + third;
            case "Student":
                return "GroupNumber: " + first +
                        ", AverageGrade: " + second + '\'' +
                        ", GradeBookNumber: " + third;
            case "User":
                return "Name: " + first +
                        ", Email: " + second + '\'' +
                        ", Password: " + third;
            default: return "Ошибка введённых данных, попробуйте снова";
        }
    }

}
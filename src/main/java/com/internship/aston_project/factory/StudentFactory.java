package com.internship.aston_project.factory;

import com.internship.aston_project.utils.FileValidation;
import com.internship.aston_project.model.Student;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class StudentFactory implements ObjectFactory<Student> {
    private final Set<String> usedRecordBookNumbers = new HashSet<>();

    @Override
    public Student create(Scanner scanner, boolean fullInput) {
        System.out.println("Введите номер группы, средний балл и номер зачетки (через пробел):");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 3 && FileValidation.validateInteger(parts[0]) && FileValidation.validateDouble(parts[1])) {
            String recordBookNumber = parts[2];
            if (!usedRecordBookNumbers.add(recordBookNumber)) {
                System.out.println("Ошибка: номер зачётки уже используется. Попробуйте другой номер.");
                return null;
            }
            return new Student.Builder()
                    .setGroupNumber(Integer.parseInt(parts[0]))
                    .setAverageScore(Double.parseDouble(parts[1]))
                    .setRecordBookNumber(recordBookNumber)
                    .build();
        }
        return null;
    }

    @Override
    public Student parse(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3 && FileValidation.validateInteger(parts[0]) && FileValidation.validateDouble(parts[1])) {
            String recordBookNumber = parts[2];
            if (!usedRecordBookNumbers.add(recordBookNumber)) {
                System.out.println("Ошибка: номер зачётки уже используется. Пропускаем запись.");
                return null;
            }
            return new Student.Builder()
                    .setGroupNumber(Integer.parseInt(parts[0]))
                    .setAverageScore(Double.parseDouble(parts[1]))
                    .setRecordBookNumber(recordBookNumber)
                    .build();
        }
        return null;
    }

    @Override
    public Student generateRandom() {
        int groupNumber = 100 + (int) (Math.random() * 900);
        double averageScore = Math.round((2 + Math.random() * 3) * 100.0) / 100.0;
        String recordBookNumber;
        do {
            recordBookNumber = " №" + (int) (Math.random() * 100000);
        } while (!usedRecordBookNumbers.add(recordBookNumber));

        return new Student.Builder()
                .setGroupNumber(groupNumber)
                .setAverageScore(averageScore)
                .setRecordBookNumber(recordBookNumber)
                .build();
    }

    @Override
    public Student createForSearch(String searchKey) {
        try {
            int groupNumber = Integer.parseInt(searchKey);
            return new Student.Builder().setGroupNumber(groupNumber).build();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: недопустимый формат ключа поиска для студента.");
            return null;
        }
    }
}
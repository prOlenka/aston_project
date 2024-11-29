package com.internship.aston_project.factory;

import com.internship.aston_project.model.Student;
import com.internship.aston_project.utils.Validator;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class StudentFactory implements ObjectFactory<Student> {
    // Хранит уже использованные номера зачетных книжек для проверки уникальности.
    private final Set<String> usedRecordBookNumbers = new HashSet<>();
    private static final Random RANDOM = new Random();

    @Override
    public Student create(Scanner scanner, String choice) {
        if(choice.equals("1")) System.out.println("Введите номер группы, средний балл и номер зачетки (через пробел):");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 3){
            if(parts[1].contains(",")) parts[1] = parts[1].replace(",", ".");
            if(!Validator.isValidInteger(parts[0])){
                System.out.println("Некорректный формат ввода, номер группы может быть только числовым значением.");
                return null;
            }
            if(!Validator.isValidDouble(parts[1])){
                System.out.println("Некорректный формат ввода, средний бал может быть только числовым значением " +
                        "(целым или нецелым).");
                return null;
            }
            if(!Validator.isValidInteger(parts[2])){
                System.out.println("Некорректный формат ввода, номер зачетки может быть только числовым значением.");
                return null;
            }

            // Проверка уникальности номера зачетной книжки.
            String recordBookNumber = parts[2];
            if (!usedRecordBookNumbers.add(recordBookNumber)) {
                System.out.println("Ошибка: номер зачётки " + recordBookNumber + " уже используется. Попробуйте другой номер.");
                return null;
            }

            // Создание и возврат объекта Student.
            return new Student.Builder()
                    .setGroupNumber(Integer.parseInt(parts[0]))
                    .setAverageScore(Double.parseDouble(parts[1]))
                    .setRecordBookNumber(recordBookNumber)
                    .build();
        }
        return null;
    }

    @Override
    public String parse(String line) {
        // Парсит строку для извлечения данных о Student.
        return line.replace("GroupNumber: ", "")
                .replace(", AverageGrade: ", " ")
                .replace(", GradeBookNumber: ", " ");
    }

    @Override
    public Student generateRandom() {
        // Случайный номер группы (100–999)
        int groupNumber = 100 + RANDOM.nextInt(99);
        // Случайный средний балл (2.0–5.0)
        double averageScore = Math.round((2.0 + RANDOM.nextDouble() * 3.0) * 100.0) / 100.0;

        // Генерация уникального номера зачётной книжки
        String recordBookNumber;
        do {
            recordBookNumber = String.format("№%05d", RANDOM.nextInt(100000));
        } while (!usedRecordBookNumbers.add(recordBookNumber));

        return new Student.Builder()
                .setGroupNumber(groupNumber)
                .setAverageScore(averageScore)
                .setRecordBookNumber(recordBookNumber)
                .build();
    }

    @Override
    public Student createForSearch(String searchKey) {
        // Создает объект Student для поиска по номеру группы.
        try {
            int groupNumber = Integer.parseInt(searchKey);
            return new Student.Builder().setGroupNumber(groupNumber).build();
        } catch (NumberFormatException e1) {
            // Попытка обработать как средний балл (число с плавающей точкой)
            try {
                double averageScore = Double.parseDouble(searchKey);
                return new Student.Builder().setAverageScore(averageScore).build();  // Если это число с плавающей точкой, ищем по среднему баллу
            } catch (NumberFormatException e2) {
                // Если это не число, ищем по номеру зачетной книжки
                return new Student.Builder().setRecordBookNumber(searchKey).build();  // Если это строка, ищем по номеру зачетки
            }
        }
    }
}
package factory;

import model.Student;
import util.Validator;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class StudentFactory implements ObjectFactory<Student> {
    private final Set<String> usedRecordBookNumbers = new HashSet<>();
    private static final Random RANDOM = new Random();

    @Override
    public Student create(Scanner scanner, boolean fullInput) {
        System.out.println("Введите номер группы, средний балл и номер зачетки (через пробел):");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 3 && Validator.validateInteger(parts[0]) && Validator.validateDouble(parts[1])) {
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
        if (parts.length == 3 && Validator.validateInteger(parts[0]) && Validator.validateDouble(parts[1])) {
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
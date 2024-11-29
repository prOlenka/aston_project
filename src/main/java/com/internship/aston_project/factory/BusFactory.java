package com.internship.aston_project.factory;

import com.internship.aston_project.model.Bus;
import com.internship.aston_project.utils.Validator;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BusFactory implements ObjectFactory<Bus> {
    // Хранит уже использованные номера автобусов для проверки уникальности.
    private final Set<String> usedBusNumbers = new HashSet<>();

    @Override
    public Bus create(Scanner scanner, String choice) {
        // Считывает и создает объект Bus на основе пользовательского ввода.
        if (choice.equals("1")) System.out.println("Введите номер, модель и пробег (через пробел):");
        String input = scanner.nextLine();

        String[] parts = input.split(" ");
        if (parts.length == 3) {
            // Валидация полей ввода.
            if (!Validator.isValidInteger(parts[0])) {
                System.out.println("Некорректный формат ввода, номер может быть только числовым значением.");
                return null;
            }
            if (!Validator.isValidStringWithoutSymbols(parts[1])) {
                System.out.println("Некорректный формат ввода, в значении не могут присутствовать специальные символы.");
                return null;
            }
            if (!Validator.isValidInteger(parts[2])) {
                System.out.println("Некорректный формат ввода, пробег может быть только числовым значением.");
                return null;
            }
            // Проверка уникальности номера автобуса.
            String busNumber = parts[2];
            if (!usedBusNumbers.add(busNumber)) {
                System.out.println("Ошибка: " + busNumber + " уже используется. Попробуйте другой номер автобуса.");
                return null;
            }
            // Создание и возврат объекта Bus.
            return new Bus.Builder()
                    .setNumber(Integer.parseInt(parts[0]))
                    .setModel(parts[1])
                    .setMileage(Integer.parseInt(parts[2]))
                    .build();
        }
        return null;
    }

    @Override
    public String parse(String line) {
        // Парсит строку для извлечения данных о Bus.
        return line.replace("Number: ", "")
                .replace(", Model: ", " ")
                .replace(", Mileage: ", " ");
    }

    @Override
    public Bus generateRandom() {
        // Генерирует случайный объект Bus.
        return new Bus.Builder()
                .setNumber((int) (Math.random() * 1000))
                .setModel("Model" + (int) (Math.random() * 100))
                .setMileage((int) (Math.random() * 50000))
                .build();
    }

    @Override
    public Bus createForSearch(String searchKey) {
        // Создает объект Bus для поиска по заданному ключу.
        try {
            int number = Integer.parseInt(searchKey);
            return new Bus.Builder().setNumber(number).build();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: недопустимый формат ключа поиска.");
            return null;
        }
    }
}


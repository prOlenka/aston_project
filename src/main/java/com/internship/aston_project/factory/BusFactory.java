package com.internship.aston_project.factory;

import com.internship.aston_project.model.Bus;
import com.internship.aston_project.utils.Validator;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BusFactory implements ObjectFactory<Bus> {
    private final Set<String> usedBusNumbers = new HashSet<>();
    @Override
    public Bus create(Scanner scanner, boolean fullInput) {
        System.out.println("Введите номер, модель и пробег (через пробел):");
        String input = scanner.nextLine();

        String[] parts = input.split(" ");
        if (parts.length == 3) {
            if (Validator.isValidStringWithoutSymbols(parts[0])) {
                System.out.println("Некорректный формат, в значении не могут присутствовать специальные символы");
                return null;
            }
            if (Validator.isValidStringWithoutSymbols(parts[1])) {
                System.out.println("Некорректный формат, в значении не могут присутствовать специальные символы.");
                return null;
            }
            if (!Validator.isValidInteger(parts[2])) {
                System.out.println("Некорректный формат ввода, пробег может быть только числовым значением");
                return null;
            }
            String busNumber = parts[2];
            if (!usedBusNumbers.add(busNumber)) {
                System.out.println("Ошибка: " + busNumber + " уже используется. Попробуйте другой номер автобуса");
                return null;
            }
            return new Bus.Builder()
                    .setNumber(Integer.parseInt(parts[0]))
                    .setModel(parts[1])
                    .setMileage(Integer.parseInt(parts[2]))
                    .build();
        }
        scanner.close();
        return null;
    }

    @Override
    public String parse(String line) {
        return line.replace("Number: ", "")
                .replace(", Model: ", " ")
                .replace(", Mileage: ", " ");
    }

    @Override
    public Bus generateRandom() {
        return new Bus.Builder()
                .setNumber((int) (Math.random() * 1000))
                .setModel("Model" + (int) (Math.random() * 100))
                .setMileage((int) (Math.random() * 50000))
                .build();
    }

    @Override
    public Bus createForSearch(String searchKey) {
        try {
            int number = Integer.parseInt(searchKey);
            return new Bus.Builder().setNumber(number).build();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: недопустимый формат ключа поиска.");
            return null;
        }
    }
}


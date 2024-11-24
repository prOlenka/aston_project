package com.internship.aston_project.factory;

import com.internship.aston_project.model.Bus;

import java.util.Scanner;

public class BusFactory implements ObjectFactory<Bus> {
    @Override
    public Bus create(Scanner scanner, boolean fullInput) {
        System.out.println("Введите номер, модель и пробег (через пробел):");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 3 && Validator.validateInteger(parts[0]) && Validator.validateInteger(parts[2])) {
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
    public Bus parse(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3 && Validator.validateInteger(parts[0]) && Validator.validateInteger(parts[2])) {
            return new Bus.Builder()
                    .setNumber(Integer.parseInt(parts[0]))
                    .setModel(parts[1])
                    .setMileage(Integer.parseInt(parts[2]))
                    .build();
        }
        return null;
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


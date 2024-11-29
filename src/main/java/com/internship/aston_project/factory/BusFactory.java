package com.internship.aston_project.factory;

import com.internship.aston_project.model.Bus;
import com.internship.aston_project.utils.Validator;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class BusFactory implements ObjectFactory<Bus> {
    private static final String[] MODELS = {
            "Volvo", "Mercedes", "Scania", "MAN", "Iveco", "Ford", "DAF", "Renault"
    };
    private static final Random RANDOM = new Random();

    private final Set<String> usedBusNumbers = new HashSet<>();
    @Override
    public Bus create(Scanner scanner, String choice) {
        if(choice.equals("1")) System.out.println("Введите номер, модель и пробег (через пробел):");
        String input = scanner.nextLine();

        String[] parts = input.split(" ");
        if (parts.length == 3) {
            if (!Validator.isValidInteger(parts[0])) {
                System.out.println("Некорректный формат ввода, номер может быть только числовым значением.");
                return null;
            }
            if (!Validator.isValidStringWithoutSymbols(parts[1])) {
                System.out.println("Некорректный формат ввода, в значении не могут присутствовать специальные символы.");
                return null;
            }
            if (!Validator.isValidInteger(parts[2])) {
                System.out.println("Некорректный формат ввода, пробег может быть только числовым значением");
                return null;
            }
            String busNumber = parts[0];
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
        // Случайный номер (1–999)
        int number = RANDOM.nextInt(999) + 1;
        // Случайная модель из списка
        String model = MODELS[RANDOM.nextInt(MODELS.length)];
        // Случайный пробег (10к–200к км)
        int mileage = RANDOM.nextInt(190_001) + 10_000;

        return new Bus.Builder()
                .setNumber(number)
                .setModel(model)
                .setMileage(mileage)
                .build();
    }

    @Override
    public Bus createForSearch(String searchKey) {
        // Попытка обработать как номер автобуса
        try {
            int number = Integer.parseInt(searchKey);
            return new Bus.Builder().setNumber(number).build();  // Если это число, ищем по номеру
        } catch (NumberFormatException e) {
            return new Bus.Builder().setModel(searchKey).build();  // Если это строка, ищем по модели
        }
    }
}


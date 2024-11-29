package factory;

import model.Bus;
import util.Validator;

import java.util.Random;
import java.util.Scanner;

public class BusFactory implements ObjectFactory<Bus> {
    private static final String[] MODELS = {
            "Volvo", "Mercedes", "Scania", "MAN", "Iveco", "Ford", "DAF", "Renault"
    };
    private static final Random RANDOM = new Random();

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

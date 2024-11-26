package com.internship.aston_project.strategy;

import com.internship.aston_project.model.Bus;
import com.internship.aston_project.utils.FileValidation;

import java.util.Scanner;

public class BusArrayFiller implements StrategyFiller<Bus> {
    @Override
    public Bus fillFromRandom() {
        return new Bus.Builder()
                .setNumber((int) (Math.random() * 1000))
                .setModel("Model" + (int) (Math.random() * 100))
                .setMileage((int) (Math.random() * 50000))
                .build();
    }

    @Override
    public Bus fillManually(Scanner scanner, boolean fullInput) {
        System.out.println("Введите номер, модель и пробег (через пробел):");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 3 && FileValidation.validateInteger(parts[0]) && FileValidation.validateInteger(parts[2])) {
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
    public Bus fillFromFile(String line) {
        String[] parts = line.split(", ");
        if (parts.length == 3 && FileValidation.validateInteger(parts[0]) && FileValidation.validateInteger(parts[2])) {
            return new Bus.Builder()
                    .setNumber(Integer.parseInt(parts[0]))
                    .setModel(parts[1])
                    .setMileage(Integer.parseInt(parts[2]))
                    .build();
        }
        return null;
    }
}
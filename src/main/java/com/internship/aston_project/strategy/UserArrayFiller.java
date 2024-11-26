package com.internship.aston_project.strategy;

import com.internship.aston_project.model.User;

import java.util.Scanner;

public class UserArrayFiller implements StrategyFiller<User> {
    @Override
    public User fillFromRandom() {
        return new User.Builder()
                .setName("User" + (int) (Math.random() * 100))
                .setPassword("Pass" + (int) (Math.random() * 1000))
                .setEmail("user" + (int) (Math.random() * 100) + "@mail.com")
                .build();
    }

    @Override
    public User fillManually(Scanner scanner, boolean fullInput) {
        System.out.println("Введите имя, пароль и email (через пробел):");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 3) {
            return new User.Builder()
                    .setName(parts[0])
                    .setPassword(parts[1])
                    .setEmail(parts[2])
                    .build();
        }
        return null;
    }

    @Override
    public User fillFromFile(String line) {
        String[] parts = line.split(", ");
        if (parts.length == 3) {
            return new User.Builder()
                    .setName(parts[0])
                    .setPassword(parts[1])
                    .setEmail(parts[2])
                    .build();
        }
        return null;
    }
}
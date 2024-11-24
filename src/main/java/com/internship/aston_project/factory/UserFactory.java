package com.internship.aston_project.factory;

import model.User;

import java.util.Scanner;

public class UserFactory implements ObjectFactory<User> {
    @Override
    public User create(Scanner scanner, boolean fullInput) {
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
    public User parse(String line) {
        String[] parts = line.split(",");
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
    public User generateRandom() {
        return new User.Builder()
                .setName("User" + (int) (Math.random() * 100))
                .setPassword("Pass" + (int) (Math.random() * 1000))
                .setEmail("user" + (int) (Math.random() * 100) + "@mail.com")
                .build();
    }

    @Override
    public User createForSearch(String searchKey) {
        if (searchKey == null || searchKey.isEmpty()) {
            System.out.println("Ошибка: пустой ключ поиска для пользователя.");
            return null;
        }
        return new User.Builder().setName(searchKey).build();
    }
}
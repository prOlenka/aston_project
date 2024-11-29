package com.internship.aston_project.factory;

import com.internship.aston_project.model.User;
import com.internship.aston_project.utils.Validator;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserFactory implements ObjectFactory<User> {
    // Хранит уже использованные e-mail для проверки уникальности.
    private final Set<String> usedRecordUserEmails = new HashSet<>();

    @Override
    public User create(Scanner scanner, String choice) {
        // Считывает и создает объект User на основе пользовательского ввода.
        if (choice.equals("1"))
            System.out.println("Введите имя, пароль и email (через пробел):");

        String input = scanner.nextLine();
        String[] parts = input.split(" ");

        if (parts.length == 3) {
            // Валидация имени.
            if (!Validator.isValidName(parts[0])) {
                System.out.println("Некорректный формат имени. Имя должно содержать только буквы.");
                return null;
            }

            // Валидация пароля.
            if (!Validator.isValidPassword(parts[1])) {
                System.out.println("Некорректный формат пароля. Пароль должен содержать цифры, заглавные и строчные латинские буквы и быть не меньше 6 символов.");
                return null;
            }

            // Валидация e-mail.
            if (!Validator.isValidEmail(parts[2])) {
                System.out.println("Некорректный формат e-mail.");
                return null;
            }

            // Проверка уникальности e-mail.
            String email = parts[2];
            if (!usedRecordUserEmails.add(email)) {
                System.out.println("Ошибка: " + email + " уже используется. Попробуйте другой e-mail.");
                return null;
            }

            // Создание и возврат объекта User.
            return new User.Builder()
                    .setName(parts[0])
                    .setPassword(parts[1])
                    .setEmail(parts[2])
                    .build();
        }
        return null;
    }

    @Override
    public String parse(String line) {
        // Парсит строку для извлечения данных о User.
        return line.replace("Name: ", "")
                .replace(", Email: ", " ")
                .replace(", Password: ", " ");
    }

    @Override
    public User generateRandom() {
        // Генерирует случайного пользователя.
        return new User.Builder()
                .setName("User" + (int) (Math.random() * 100))
                .setPassword("Pass" + (int) (Math.random() * 1000))
                .setEmail("user" + (int) (Math.random() * 100) + "@mail.com")
                .build();
    }

    @Override
    public User createForSearch(String searchKey) {
        // Создает пользователя для поиска по имени.
        if (searchKey == null || searchKey.isEmpty()) {
            System.out.println("Ошибка: пустой ключ поиска для пользователя.");
            return null;
        }
        return new User.Builder().setName(searchKey).build();
    }
}

package com.internship.aston_project.factory;

import com.internship.aston_project.model.User;
import com.internship.aston_project.utils.Validator;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class UserFactory implements ObjectFactory<User> {
    // Хранит уже использованные e-mail для проверки уникальности.
    private final Set<String> usedEmails = new HashSet<>();
    private static final Random RANDOM = new Random();

    private final Set<String> usedRecordUserEmails = new HashSet<>();

    @Override
    public User create(Scanner scanner, String choice) {
        if(choice.equals("1")) System.out.println("Введите имя, пароль и email (через пробел):");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");

        if (parts.length == 3) {
            if(!Validator.isValidName(parts[0])) {
                System.out.println("Некорректный формат имени. Имя должно содержать только буквы");
                return null;
            }
            if(!Validator.isValidPassword(parts[1])) {
                System.out.println("Некорректный формат пароля. Пароль должен содержать цифры,\n" +
                        "заглавные и строчные латинские буквы и быть не меньше 6 символов");
                return null;
            }
            if(!Validator.isValidEmail(parts[2])) {
                System.out.println("Некорректный формат e-mail.");
                return null;
            }

            // Проверка уникальности e-mail.
            String email = parts[2];
            if (!usedRecordUserEmails.add(email)) {
                System.out.println("Ошибка: " + email + " уже используется. Попробуйте другой e-mail");
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
        // Списки для случайных данных
        String[] names = {"Евгений", "Ольга", "Валерия", "Дарья", "Владимир", "Кекус", "Шнырь"};
        String[] domains = {"gmail.com", "mail.com", "outlook.org", "yandex.ru"};

        // Генерация случайного имени
        String name = names[RANDOM.nextInt(names.length)];

        // Генерация уникального email
        String email;
        do {
            email = name.toLowerCase() + RANDOM.nextInt(1000) + "@" +
                    domains[RANDOM.nextInt(domains.length)];
        } while (!usedEmails.add(email));

        // Генерация случайного пароля
        String password = generateRandomPassword(8); // Длина пароля — 8 символов

        return new User.Builder()
                .setName(name)
                .setPassword(password)
                .setEmail(email)
                .build();
    }

    private String generateRandomPassword(int length) {
        // Разрешённые символы для пароля
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(randomIndex));
        }

        return password.toString();
    }

    @Override
    public User createForSearch(String searchKey) {
        // Создает пользователя для поиска по имени.
        if (searchKey == null || searchKey.isEmpty()) {
            System.out.println("Ошибка: пустой ключ поиска для пользователя.");
            return null;
        }

        // Проверка, является ли ключ email
        if (searchKey.contains("@")) {
            return new User.Builder().setEmail(searchKey).build();
        }
        // Если ключ не является email, используем его как имя
        return new User.Builder().setName(searchKey).build();
    }


}
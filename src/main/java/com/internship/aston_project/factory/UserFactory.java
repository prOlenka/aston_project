package factory;

import model.User;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class UserFactory implements ObjectFactory<User> {
    private final Set<String> usedEmails = new HashSet<>();
    private static final Random RANDOM = new Random();

    @Override
    public User create(Scanner scanner, boolean fullInput) {
        System.out.println("Введите имя, пароль и email (через пробел):");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 3) {
            String email = parts[2];
            if (!usedEmails.add(email)) {
                System.out.println("Ошибка: email уже используется. Попробуйте другой.");
                return null;
            }
            return new User.Builder()
                    .setName(parts[0])
                    .setPassword(parts[1])
                    .setEmail(email)
                    .build();
        }
        return null;
    }

    @Override
    public User parse(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
            String email = parts[2];
            if (!usedEmails.add(email)) {
                System.out.println("Ошибка: email уже используется. Пропускаем запись.");
                return null;
            }
            return new User.Builder()
                    .setName(parts[0])
                    .setPassword(parts[1])
                    .setEmail(email)
                    .build();
        }
        return null;
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
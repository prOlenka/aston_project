package com.internship.aston_project.utils;

import com.internship.aston_project.model.Bus;
import com.internship.aston_project.model.Student;
import com.internship.aston_project.model.User;

// Класс для валидации входных данных
public class Validator {

    // Проверка, является ли строка целым числом
    public static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Проверка, является ли строка числом с плавающей запятой (Double)
    public static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Проверка, является ли строка валидным email-адресом
    public static boolean isValidEmail(String input) {
        return (input.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"));
    }

    // Проверка, является ли строка валидным именем (только буквы)
    public static boolean isValidName(String input) {
        return input.matches("[a-zA-Zа-яА-Я]+");
    }

    // Проверка, является ли строка валидным паролем (6-20 символов, только латинские буквы и цифры)
    public static boolean isValidPassword(String input) {
        return input.matches("[a-zA-Z0-9]{6,20}");
    }

    // Проверка, является ли строка валидной строкой без спецсимволов (только буквы и цифры)
    public static boolean isValidStringWithoutSymbols(String input) {
        return input.matches("^[a-zA-Zа-яА-ЯёЁ0-9\\s]+$");
    }

    // Валидация типа объекта в зависимости от строки (например, проверка, является ли строка значением поля объекта)
    public static <T> boolean fileValidation(String input, Class<T> type) {
        Class<?> receivedType = switch (input) {
            case "Number:" -> Bus.class;
            case "GroupNumber:" -> Student.class;
            case "Name:" -> User.class;
            default -> null;
        };
        return type == receivedType;
    }
}

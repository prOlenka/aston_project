package com.internship.aston_project.utils;

//Класс для валидации
public class FileValidation {

    //TODO: удалить все, что ниже
    public FileValidation() {
    }

    public static boolean validateInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static boolean validateDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }
}

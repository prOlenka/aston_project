package com.internship.aston_project.utils;

import com.internship.aston_project.model.Bus;
import com.internship.aston_project.model.Student;
import com.internship.aston_project.model.User;

//Класс для валидации
public class Validator {


    public static boolean isValidInteger(String input) {
        try{
            Integer.parseInt(String.valueOf(input));
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean isValidDouble(String input) {
        try{
            Double.parseDouble(input);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean isValidEmail(String input) {
       return (input.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"));
    }

    public static boolean isValidName(String input) {
        return input.matches("[a-zA-Zа-яА-Я]+");
    }

    public static boolean isValidPassword(String input) {
        return input.matches("[a-zA-Z0-9]{6,20}");
    }

    public static boolean isValidStringWithoutSymbols(String input) {
        return input.matches("/^[a-zA-Zа-яА-ЯёЁ0-9\\s]+$/");
    }

    //Валидация класса из файла
    public static <T> boolean fileValidation(String input, Class<T> type ) {
        Class<?> receivedType = switch (input) {
            case "Number:" -> Bus.class;
            case "GroupNumber:" -> Student.class;
            case "Name:" -> User.class;
            default -> null;
        };
        return type == receivedType;
    }

}

package com.internship.aston_project.utils;

import com.internship.aston_project.factory.BusFactory;
import com.internship.aston_project.factory.ObjectFactory;
import com.internship.aston_project.factory.StudentFactory;
import com.internship.aston_project.factory.UserFactory;
import com.internship.aston_project.model.Bus;
import com.internship.aston_project.model.Student;
import com.internship.aston_project.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class SearchField {
    public static <T> Function<T, ? extends Comparable> chooseField(Scanner scanner, ObjectFactory<T> factory) {

        Map<String, Function<T, ? extends Comparable>> fieldMap = new HashMap<>();

        if (factory instanceof BusFactory) {
            System.out.println("1. Номер автобуса");
            System.out.println("2. Модель");
            System.out.println("3. Пробег");
            fieldMap.put("1", bus -> ((Bus) bus).getNumber());
            fieldMap.put("2", bus -> ((Bus) bus).getModel());
            fieldMap.put("3", bus -> ((Bus) bus).getMileage());
        } else if (factory instanceof StudentFactory) {
            System.out.println("1. Номер группы");
            System.out.println("2. Средний балл");
            System.out.println("3. Номер зачетной книжки");
            fieldMap.put("1", student -> ((Student) student).getGroupNumber());
            fieldMap.put("2", student -> ((Student) student).getAverageScore());
            fieldMap.put("3", student -> ((Student) student).getRecordBookNumber());
        } else if (factory instanceof UserFactory) {
            System.out.println("1. Имя");
            System.out.println("2. Пароль");
            System.out.println("3. Email");
            fieldMap.put("1", user -> ((User) user).getName());
            fieldMap.put("2", user -> ((User) user).getPassword());
            fieldMap.put("3", user -> ((User) user).getEmail());
        }

        String fieldChoice = scanner.nextLine();
        return fieldMap.getOrDefault(fieldChoice, null);
    }

}

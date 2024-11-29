package com.internship.aston_project.factory;

import java.util.Scanner;

public interface ObjectFactory<T> {
    // Метод для создания объекта с использованием данных из Scanner
    T create(Scanner scanner, String choice);

    // Метод для парсинга строки в объект
    String parse(String line);

    // Метод для генерации случайного объекта
    T generateRandom();

    // Метод для создания объекта для поиска по ключу
    T createForSearch(String searchKey);
}
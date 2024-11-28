package com.internship.aston_project.factory;

import java.util.Scanner;

public interface ObjectFactory<T> {
    T create(Scanner scanner, String choice);

    String parse(String line);

    T generateRandom();

    T createForSearch(String searchKey);

}
package com.internship.aston_project.factory;

import java.util.Scanner;

public interface ObjectFactory<T> {
    T create(Scanner scanner, boolean fullInput);

    String parse(String line);

    T generateRandom();

    T createForSearch(String searchKey);

}
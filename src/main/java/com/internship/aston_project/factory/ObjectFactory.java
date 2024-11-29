package factory;

import java.util.Scanner;

public interface ObjectFactory<T> {
    T create(Scanner scanner, boolean fullInput);

    T parse(String line);

    T generateRandom();

    T createForSearch(String searchKey);

}
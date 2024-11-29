package com.internship.aston_project.sort;

import com.internship.aston_project.model.Bus;
import com.internship.aston_project.model.Student;
import com.internship.aston_project.model.User;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class QuickSort<T extends Comparable<T>> implements SortStrategy<T> {
    private Comparator<T> comparator;

    @Override
    public int sort(List<T> items) {
        if (items == null || items.size() <= 1) return -1;
        Sortable<T> typeSorting = new NaturalSort();
        Scanner scanner = new Scanner(System.in);
        String fieldChoice = null;

        // Выбор поля для сортировки в зависимости от типа объекта в списке.
        switch(items.get(0).getClass().getSimpleName()) {
            case "Bus" -> {
                System.out.println("""
                        Выберете поле:
                        1.Номер
                        2.Модель
                        3.Прообег
                        """);

                fieldChoice = scanner.nextLine();
                switch (fieldChoice) {
                    case "1" -> {
                        comparator = new Bus.NumberCompare<>();
                        fieldChoice = "number";
                    }
                    case "2" -> {
                        comparator = new Bus.ModelCompare<>();
                        fieldChoice = "model";
                    }
                    case "3" -> {
                        comparator = new Bus.MileageCompare<>();
                        fieldChoice = "mileage";
                    }
                }
            }
            case "Student" -> {
                System.out.println("""
                        Выберете поле:
                        1.Номер группы
                        2.Средний балл
                        3.Номер зачётки
                        """);
                fieldChoice = scanner.nextLine();
                switch (fieldChoice) {
                    case "1" -> {
                        comparator = new Student.GroupNumberCompare<>();
                        fieldChoice = "groupNumber";
                    }
                    case "2" -> {
                        comparator = new Student.AverageScoreCompare<>();
                        fieldChoice = "averageScore";
                    }
                    case "3" -> {
                        comparator = new Student.RecordBookNumberCompare<>();
                        fieldChoice = "recordBookNumber";
                    }
                }
            }
            case "User" -> {
                System.out.println("""
                        Выберете поле:
                        1.Имя
                        2.Пароль
                        3.Email
                        """);
                fieldChoice = scanner.nextLine();
                switch (fieldChoice) {
                    case "1" -> {
                        comparator = new User.NameCompare<>();
                        fieldChoice = "name";
                    }
                    case "2" -> {
                        comparator = new User.PasswordCompare<>();
                        fieldChoice = "password";
                    }
                    case "3" -> {
                        comparator = new User.EmailCompare<>();
                        fieldChoice = "email";
                    }
                }
            }
        }

        // Выбор типа сортировки.
        System.out.println("""
        Выбрать способ сортировки:
        1.Обычный порядок
        2.Только чётные числа
        """);
        String typeSort = scanner.nextLine();
        switch(typeSort) {
            case "1" -> { }
            case "2" -> typeSorting = new EvenSort(fieldChoice);
        }
        return quickSort(items, 0, items.size() - 1, typeSorting);
    }

    // Метод быстрой сортировки
    private int quickSort(List<T> items, int low, int high, Sortable<T> typeSort) {
        if (low < high) {
            int pivotIndex = typeSort.partition(items, low, high);
            if(pivotIndex != -1) {
                quickSort(items, low, pivotIndex - 1, typeSort);
                quickSort(items, pivotIndex + 1, high, typeSort);
                return 0;
            } else {
                return -1;
            }
        }
        return -1;
    }

    // Класс для обычной сортировки
    class NaturalSort implements Sortable<T> {
        public int partition(List<T> items, int low, int high) {
            T pivot = items.get(high);
            int pLow = low;
            int pHigh = high - 1;
            while (true) {
                while (pLow < high && comparator.compare(items.get(pLow), pivot) < 0) pLow++;
                while (pHigh > low && comparator.compare(items.get(pHigh), pivot) >= 0) pHigh--;
                if (pLow < pHigh) swap(items, pLow++, pHigh--);
                else break;
            }
            swap(items, pLow, high);
            return pLow;
        }
    }

    // Класс для сортировки только чётных чисел
    class EvenSort implements Sortable<T> {
        String field;
        public EvenSort(String field) {
            this.field = field;
        }

        public int partition(List<T> items, int low, int high) {
            try {
                Field currentField = items.get(high).getClass().getDeclaredField(field);
                if(currentField.getType() == int.class) {
                    currentField.setAccessible(true);
                    // Пропускаем нечетные числа в процессе сортировки
                    while (currentField.getInt(items.get(high)) % 2 == 1 && low < high) high--;
                    int pivot = currentField.getInt(items.get(high));
                    int pLow = low;
                    int pHigh = high - 1;
                    while (pLow <= pHigh) {
                        while (pLow < high) {
                            if (currentField.getInt(items.get(pLow)) % 2 == 1 || currentField.getInt(items.get(pLow)) < pivot)
                                pLow++;
                            else break;
                        }
                        while (pHigh > low) {
                            if (currentField.getInt(items.get(pHigh)) % 2 == 1 || currentField.getInt(items.get(pHigh)) >= pivot)
                                pHigh--;
                            else break;
                        }
                        if (pLow < pHigh) swap(items, pLow++, pHigh--);
                        else break;
                    }
                    swap(items, pLow, high);
                    return pLow;
                } else {
                    return -1;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Метод для обмена элементов в списке
    private void swap(List<T> items, int i, int j) {
        T temp = items.get(i); // Сохраняем временно значение i
        items.set(i, items.get(j));  // Меняем местами значения i и j
        items.set(j, temp); // Устанавливаем сохраненное значение на позицию j
    }
}

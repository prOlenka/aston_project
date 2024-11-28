package com.internship.aston_project.sort;

import java.util.List;
import java.util.function.Function;

public class QuickSort<T> implements SortStrategy<T> {
    @Override
    public void sort(List<T> items, Function<T, ? extends Comparable> keyExtractor) {
        // Ничего нет, не сортируем
        if (items == null || items.size() <= 1) return;
        // Вызвать быстр сортир
        quickSort(items, 0, items.size() - 1, keyExtractor);
    }

    private void quickSort(List<T> items, int low, int high, Function<T, ? extends Comparable> keyExtractor) {
        if (low < high) {
            // Разделяем массив на части относительно опорного элемента
            int pivotIndex = partition(items, low, high, keyExtractor);
            // Проверяем левую часть
            quickSort(items, low, pivotIndex - 1, keyExtractor);
            // Проверяем правую часть
            quickSort(items, pivotIndex + 1, high, keyExtractor);
        }
    }

    private int partition(List<T> items, int low, int high, Function<T, ? extends Comparable> keyExtractor) {
        // Выбираем последний элемент как опорный
        T pivot = items.get(high);
        // Указатель для разделения элементов
        int i = low - 1;
        // Проходим по массиву от low до high-1
        for (int j = low; j < high; j++) {
            // Если текущий элемент меньше или равен опорному, перемещаем его в левую часть
            if (keyExtractor.apply(items.get(j)).compareTo(keyExtractor.apply(pivot)) <= 0) {
                i++;
                swap(items, i, j); // Обмениваем элементы
            }
        }
        // Помещаем опорный элемент в свою позицию
        swap(items, i + 1, high);
        return i + 1; // Возвращаем индекс опорного элемента
    }

    private void swap(List<T> items, int i, int j) {
        T temp = items.get(i); // Сохраняем временно значение i
        items.set(i, items.get(j));  // Меняем местами значения i и j
        items.set(j, temp); // Устанавливаем сохраненное значение на позицию j
    }
}

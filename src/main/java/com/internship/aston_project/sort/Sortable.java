package com.internship.aston_project.sort;

import java.util.List;

public interface Sortable<T extends Comparable<T>> {
    int partition(List<T> items, int low, int high);   // Метод для разделения списка в процессе сортировки
}

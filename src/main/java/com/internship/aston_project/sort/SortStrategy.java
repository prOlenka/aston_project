package com.internship.aston_project.sort;

import java.util.List;

public interface SortStrategy<T> {
    void sort(List<T> items);
}
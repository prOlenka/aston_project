package com.internship.aston_project.sort;

import java.util.List;

public interface SortStrategy<T> {
    int sort(List<T> items);
}
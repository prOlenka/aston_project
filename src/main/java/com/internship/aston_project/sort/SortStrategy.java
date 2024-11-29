package com.internship.aston_project.sort;

import java.util.List;
import java.util.function.Function;

public interface SortStrategy<T> {

    int sort(List<T> items);
}
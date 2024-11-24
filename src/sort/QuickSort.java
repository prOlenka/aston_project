package sort;

import java.util.List;

public class QuickSort<T extends Comparable<T>> implements SortStrategy<T> {
    @Override
    public void sort(List<T> items) {
        if (items == null || items.size() <= 1) return;
        quickSort(items, 0, items.size() - 1);
    }

    private void quickSort(List<T> items, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(items, low, high);
            quickSort(items, low, pivotIndex - 1);
            quickSort(items, pivotIndex + 1, high);
        }
    }

    private int partition(List<T> items, int low, int high) {
        T pivot = items.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (items.get(j).compareTo(pivot) <= 0) {
                i++;
                swap(items, i, j);
            }
        }
        swap(items, i + 1, high);
        return i + 1;
    }

    private void swap(List<T> items, int i, int j) {
        T temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
    }
}
package sort;

import java.util.List;
import java.util.function.Function;

public class OddEvenSort<T> implements SortStrategy<T> {
    @Override
    public void sort(List<T> items, Function<T, ? extends Comparable> keyExtractor) {
        if (items == null || items.size() <= 1) return;
        oddEvenSort(items, 0, items.size() - 1, keyExtractor);
    }

    private void oddEvenSort(List<T> items, int low, int high, Function<T, ? extends Comparable> keyExtractor) {
        if (low < high) {
            int pivotIndex = partition(items, low, high, keyExtractor);
            oddEvenSort(items, low, pivotIndex - 1, keyExtractor);
            oddEvenSort(items, pivotIndex + 1, high, keyExtractor);
        }
    }

    private int partition(List<T> items, int low, int high, Function<T, ? extends Comparable> keyExtractor) {
        // Найти опорный элемент (четное значение)
        while (isOdd(items.get(high), keyExtractor) && low < high) high--;
        T pivot = items.get(high);
        int pLow = low;
        int pHigh = high - 1;
        while (pLow <= pHigh) {
            // Пропустить нечетные значения, которые меньше опорного
            while (pLow < high && (isOdd(items.get(pLow), keyExtractor) ||
                    keyExtractor.apply(items.get(pLow)).compareTo(keyExtractor.apply(pivot)) < 0)) {
                pLow++;
            }
            // Пропустить нечетные значения, которые больше или равны опорному
            while (pHigh > low && (isOdd(items.get(pHigh), keyExtractor) ||
                    keyExtractor.apply(items.get(pHigh)).compareTo(keyExtractor.apply(pivot)) >= 0)) {
                pHigh--;
            }
            // Поменять местами элементы, если это необходимо
            if (pLow < pHigh) {
                swap(items, pLow++, pHigh--);
            } else {
                break;
            }
        }
        // Поместить опорный элемент на свое место
        swap(items, pLow, high);
        return pLow;
    }

    private void swap(List<T> items, int i, int j) {
        T temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
    }

    private boolean isOdd(T item, Function<T, ? extends Comparable> keyExtractor) {
        // Проверяем, является ли числовое значение нечетным
        Comparable key = keyExtractor.apply(item);
        if (key instanceof Integer) {
            return (Integer) key % 2 != 0;
        }
        throw new IllegalArgumentException("Тут робят только числа");
    }
}
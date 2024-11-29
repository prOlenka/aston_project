package util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BinarySearch<T> {
    // Метод для поиска всех элементов, равных заданному target в отсортированном списке
    public List<T> searchAll(List<T> sortedList, T target, Function<T, ? extends Comparable> keyExtractor) {
        List<T> results = new ArrayList<>(); // Список для хранения найденных элементов

        if (sortedList == null || sortedList.isEmpty()) {
            return results; // Если список пуст, возвращаем пустой список
        }

        // Извлечение ключа поиска из target с использованием keyExtractor
        Comparable targetKey = keyExtractor.apply(target);

        // Начальные значения для границ поиска
        int low = 0;
        int high = sortedList.size() - 1;
        int foundIndex = -1; // Индекс найденного элемента, изначально -1 (не найден)

        while (low <= high) {
            int mid = low + (high - low) / 2; // Находим средний индекс
            Comparable midValue = keyExtractor.apply(sortedList.get(mid)); // Получаем значение для сравнения

            int comparisonResult = midValue.compareTo(targetKey); // Сравнение среднего значения с искомым

            if (comparisonResult == 0) { // Если нашли совпадение
                foundIndex = mid; // Сохраняем индекс найденного элемента
                break;
            } else if (comparisonResult < 0) { // Если среднее значение меньше искомого, ищем справа
                low = mid + 1;
            } else { // Если среднее значение больше искомого, ищем слева
                high = mid - 1;
            }
        }
        // Если элемент не найден, возвращаем пустой список
        if (foundIndex == -1) {
            return results;
        }

        // Расширение поиска для нахождения всех элементов, равных target, по обеим сторонам
        int left = foundIndex;
        // Ищем одинаковые элементы слева от найденного
        while (left >= 0 && keyExtractor.apply(sortedList.get(left)).compareTo(targetKey) == 0) {
            results.add(sortedList.get(left)); // Добавляем найденный элемент в результат
            left--; // Переходим к предыдущему элементу
        }

        int right = foundIndex + 1;
        // Ищем одинаковые элементы справа от найденного
        while (right < sortedList.size() && keyExtractor.apply(sortedList.get(right)).compareTo(targetKey) == 0) {
            results.add(sortedList.get(right));
            right++; // Переходим к следующему элементу
        }

        return results; // Возвращаем список с найденными элементами
    }
}
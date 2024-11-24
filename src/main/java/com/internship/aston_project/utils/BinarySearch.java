package com.internship.aston_project.utils;

import java.util.ArrayList;
import java.util.List;

public class BinarySearch<T extends Comparable<T>> {
    public List<T> searchAll(List<T> sortedList, T target) {
        List<T> results = new ArrayList<>();

        if (sortedList == null || sortedList.isEmpty()) {
            return results;
        }

        int low = 0;
        int high = sortedList.size() - 1;
        int foundIndex = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            T midValue = sortedList.get(mid);

            int comparisonResult = midValue.compareTo(target);

            if (comparisonResult == 0) {
                foundIndex = mid;
                break;
            } else if (comparisonResult < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (foundIndex == -1) {
            return results;
        }

        int left = foundIndex;
        while (left >= 0 && sortedList.get(left).compareTo(target) == 0) {
            results.add(sortedList.get(left));
            left--;
        }

        int right = foundIndex + 1;
        while (right < sortedList.size() && sortedList.get(right).compareTo(target) == 0) {
            results.add(sortedList.get(right));
            right++;
        }

        return results;
    }
}
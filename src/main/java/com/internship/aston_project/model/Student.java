package com.internship.aston_project.model;

import java.util.Comparator;
import lombok.Getter;

@Getter
public class Student implements Comparable<Student> {
    // Поля, представляющие номер группы, средний балл и номер зачетки студента.
    private final int groupNumber;
    private final Double averageScore;
    private final String recordBookNumber;

    // Приватный конструктор, инициализирующий поля с помощью билдера.
    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageScore = builder.averageScore;
        this.recordBookNumber = builder.recordBookNumber;
    }

    // Статический вложенный класс Builder для пошагового создания объекта Student.
    public static class Builder {
        private int groupNumber;
        private double averageScore;
        private String recordBookNumber;

        // Устанавливает номер группы и возвращает текущий экземпляр Builder.
        public Builder setGroupNumber(int groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        // Устанавливает средний балл и возвращает текущий экземпляр Builder.
        public Builder setAverageScore(double averageScore) {
            this.averageScore = averageScore;
            return this;
        }

        // Устанавливает номер зачетки и возвращает текущий экземпляр Builder.
        public Builder setRecordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }

        // Создает объект Student с текущими параметрами Builder.
        public Student build() {
            return new Student(this);
        }
    }

    @Override
    public int compareTo(Student other) {
        // Сравнивает студентов по номеру группы.
        return Integer.compare(this.groupNumber, other.groupNumber);
    }

    @Override
    public String toString() {
        // Возвращает строковое представление объекта Student в формате "groupNumber,averageScore,recordBookNumber".
        return groupNumber + "," + averageScore + "," + recordBookNumber;
    }

    // Вложенный класс для сравнения студентов по номеру группы.
    public static class GroupNumberCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return Integer.compare(((Student) a).groupNumber, ((Student) b).groupNumber);
        }
    }

    // Вложенный класс для сравнения студентов по среднему баллу.
    public static class AverageScoreCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return ((Student) a).averageScore.compareTo(((Student) b).averageScore);
        }
    }

    // Вложенный класс для сравнения студентов по номеру зачетки.
    public static class RecordBookNumberCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return ((Student) a).recordBookNumber.compareTo(((Student) b).recordBookNumber);
        }
    }
}

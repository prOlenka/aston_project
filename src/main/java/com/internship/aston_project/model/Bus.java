package com.internship.aston_project.model;

import java.util.Comparator;
import lombok.Getter;

@Getter
public class Bus implements Comparable<Bus> {
    // Поля, представляющие номер, модель и пробег автобуса.
    private final int number;
    private final String model;
    private final int mileage;

    // Приватный конструктор, инициализирующий поля с помощью билдера.
    private Bus(Builder builder) {
        this.number = builder.number;
        this.model = builder.model;
        this.mileage = builder.mileage;
    }

    // Статический вложенный класс Builder для пошагового создания объекта Bus.
    public static class Builder {
        private int number;
        private String model;
        private int mileage;

        // Устанавливает номер автобуса и возвращает текущий экземпляр Builder.
        public Builder setNumber(int number) {
            this.number = number;
            return this;
        }

        // Устанавливает модель автобуса и возвращает текущий экземпляр Builder.
        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        // Устанавливает пробег автобуса и возвращает текущий экземпляр Builder.
        public Builder setMileage(int mileage) {
            this.mileage = mileage;
            return this;
        }

        // Создает объект Bus с текущими параметрами Builder.
        public Bus build() {
            return new Bus(this);
        }
    }

    @Override
    public int compareTo(Bus other) {
        // Сравнивает автобусы по номеру.
        return Integer.compare(this.number, other.number);
    }

    @Override
    public String toString() {
        // Возвращает строковое представление объекта Bus в формате "number,model,mileage".
        return number + "," + model + "," + mileage;
    }

    // Вложенный класс для сравнения объектов Bus по номеру.
    public static class NumberCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return Integer.compare(((Bus) a).number, ((Bus) b).number);
        }
    }

    // Вложенный класс для сравнения объектов Bus по модели.
    public static class ModelCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return ((Bus) a).model.compareTo(((Bus) b).model);
        }
    }

    // Вложенный класс для сравнения объектов Bus по пробегу.
    public static class MileageCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return Integer.compare(((Bus) a).mileage, ((Bus) b).mileage);
        }
    }
}

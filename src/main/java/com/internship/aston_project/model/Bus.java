package com.internship.aston_project.model;

import java.util.Comparator;
import lombok.Getter;

@Getter
public class Bus implements Comparable<Bus> {
    private final int number;
    private final String model;
    private final int mileage;

    private Bus(Builder builder) {
        this.number = builder.number;
        this.model = builder.model;
        this.mileage = builder.mileage;
    }

    public static class Builder {
        private int number;
        private String model;
        private int mileage;

        public Builder setNumber(int number) {
            this.number = number;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setMileage(int mileage) {
            this.mileage = mileage;
            return this;
        }

        public Bus build() {
            return new Bus(this);
        }
    }

    @Override
    public int compareTo(Bus other) {
        return Integer.compare(this.number, other.number);
    }

    @Override
    public String toString() {
        return number + "," + model + "," + mileage;
    }

    public static class NumberCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return Integer.compare(((Bus)a).number, ((Bus)b).number);
        }
    }

    public static class ModelCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return ((Bus)a).model.compareTo(((Bus)b).model);
        }
    }

    public static class MileageCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return Integer.compare(((Bus)a).mileage, ((Bus)b).mileage);
        }
    }
}


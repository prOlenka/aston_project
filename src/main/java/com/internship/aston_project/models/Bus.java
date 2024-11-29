package model;

public class Bus implements Comparable<Bus> {
    private final int number;
    private final String model;
    private final int mileage;

    private Bus(Builder builder) {
        this.number = builder.number;
        this.model = builder.model;
        this.mileage = builder.mileage;
    }

    public int getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public int getMileage() {
        return mileage;
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
    public int compareTo(Bus other) { // Метод для сравнения объектов класса Bus
        int result = Integer.compare(this.number, other.number);
        if (result == 0) {
            result = this.model.compareTo(other.model);
        }
        if (result == 0) {
            result = Integer.compare(this.mileage, other.mileage);
        }
        return result;
    }

    @Override
    public String toString() {
        return number + "," + model + "," + mileage;
    }
}
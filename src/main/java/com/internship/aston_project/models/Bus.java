package com.internship.aston_project.models;

import lombok.Getter;

@Getter
public class Bus {
    private final int number;
    private final String model;
    private final double mileage;

    private Bus(int number, String model, double mileage) {
        this.number = number;
        this.model = model;
        this.mileage = mileage;
    }
}

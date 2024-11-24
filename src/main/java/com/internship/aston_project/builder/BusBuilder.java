package com.internship.aston_project.builder;

import com.internship.aston_project.models.Bus;

public interface BusBuilder {
    BusBuilder number(int number);
    BusBuilder model(String model);
    BusBuilder mileage(double mileage);
    Bus buildBus();
}


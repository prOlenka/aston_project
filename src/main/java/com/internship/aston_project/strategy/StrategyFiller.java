package com.internship.aston_project.strategy;

import java.util.Scanner;

//Реализация паттерна "Стратегия"
public interface StrategyFiller <T> {
    T fillFromRandom();
    T fillManually();
    T fillFromFile();
}

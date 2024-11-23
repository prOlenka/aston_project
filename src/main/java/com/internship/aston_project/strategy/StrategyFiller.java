package com.internship.aston_project.strategy;

//Реализация паттерна "Стратегия"
public interface StrategyFiller {
    public void fillFromRandom();
    public void fillManually();
    public void fillFromFile();
}

package com.internship.aston_project.models;

import lombok.Getter;

@Getter
public class Student {
    private final int groupName;
    private final int averageScore;
    private final int gradeBookNumber;

    private Student(int groupName, int averageScore, int gradeBookNumber) {
        this.groupName = groupName;
        this.averageScore = averageScore;
        this.gradeBookNumber = gradeBookNumber;
    }
}

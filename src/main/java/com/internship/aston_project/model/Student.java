package com.internship.aston_project.model;

import lombok.Getter;


@Getter
public class Student implements Comparable<Student> {
    private final int groupNumber;
    private final double averageScore;
    private final String recordBookNumber;


    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageScore = builder.averageScore;
        this.recordBookNumber = builder.recordBookNumber;
    }

    public static class Builder {
        private int groupNumber;
        private double averageScore;
        private String recordBookNumber;

        public Builder setGroupNumber(int groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public Builder setAverageScore(double averageScore) {
            this.averageScore = averageScore;
            return this;
        }

        public Builder setRecordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }

        public Student build() {
            return new Student(this);
        }

    }

    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.groupNumber, other.groupNumber);
    }

    @Override
    public String toString() {
        return groupNumber + "," + averageScore + "," + recordBookNumber;
    }
}
package com.internship.aston_project.builder;

import com.internship.aston_project.models.Student;

public interface StudentBuilder {
    StudentBuilder groupName(String groupName);
    StudentBuilder averageScore(Integer averageScore);
    StudentBuilder gradeBookNumber(Integer gradeBookNumber);
    Student buildStudent();
}

package com.internship.aston_project.models;

public class User {
    private final String name;
    private final int password;
    private final String email;

    public User(String name, int password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}

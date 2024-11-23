package com.internship.aston_project.models;

import lombok.Getter;

@Getter
public class User {
    private final String name;
    private final String email;
    private final int password;

    private User(String name, int password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}

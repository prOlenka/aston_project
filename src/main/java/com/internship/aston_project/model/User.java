package com.internship.aston_project.model;

import com.internship.aston_project.utils.Validator;

public class User implements Comparable<User> {
    private final String name;
    private final String password;
    private final String email;


    private User(Builder builder) {
        this.name = builder.name;
        this.password = builder.password;
        this.email = builder.email;
    }

    public static class Builder {
        private String name;
        private String password;
        private String email;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public int compareTo(User other) {
        return this.name.compareTo(other.name); // Сравнение по имени
    }

    @Override
    public String toString() {
        return name + "," + password + "," + email;
    }
}
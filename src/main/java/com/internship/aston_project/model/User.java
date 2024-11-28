package com.internship.aston_project.model;

import java.util.Comparator;

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

    public static class NameCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return ((User)a).name.compareTo(((User)b).name);
        }
    }

    public static class PasswordCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return ((User)a).password.compareTo(((User)b).password);
        }
    }

    public static class EmailCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return ((User)a).email.compareTo(((User)b).email);
        }
    }
}
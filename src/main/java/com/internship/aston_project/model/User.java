package com.internship.aston_project.model;

import java.util.Comparator;
import lombok.Getter;

@Getter
public class User implements Comparable<User> {
    // Поля, представляющие имя, пароль и email пользователя.
    private final String name;
    private final String password;
    private final String email;

    // Приватный конструктор, инициализирующий поля с помощью билдера.
    private User(Builder builder) {
        this.name = builder.name;
        this.password = builder.password;
        this.email = builder.email;
    }

    // Статический вложенный класс Builder для пошагового создания объекта User.
    public static class Builder {
        private String name;
        private String password;
        private String email;

        // Устанавливает имя и возвращает текущий экземпляр Builder.
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        // Устанавливает пароль и возвращает текущий экземпляр Builder.
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        // Устанавливает email и возвращает текущий экземпляр Builder.
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        // Создает объект User с текущими параметрами Builder.
        public User build() {
            return new User(this);
        }
    }

    @Override
    public int compareTo(User other) {
        // Сравнивает пользователей по имени.
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        // Возвращает строковое представление объекта User в формате "name,password,email".
        return name + "," + password + "," + email;
    }

    // Вложенный класс для сравнения пользователей по имени.
    public static class NameCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return ((User) a).name.compareTo(((User) b).name);
        }
    }

    // Вложенный класс для сравнения пользователей по паролю.
    public static class PasswordCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return ((User) a).password.compareTo(((User) b).password);
        }
    }

    // Вложенный класс для сравнения пользователей по email.
    public static class EmailCompare<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return ((User) a).email.compareTo(((User) b).email);
        }
    }
}

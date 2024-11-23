package com.internship.aston_project.builder;

import com.internship.aston_project.models.User;

public interface UserBuilder {
    UserBuilder name(String name);
    UserBuilder email(String email);
    UserBuilder password(int password);
    User buildUser();
}
